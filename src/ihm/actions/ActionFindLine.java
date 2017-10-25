package ihm.actions;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import filter.FilterLine;
import ihm.ImageUtil;
import ihm.PanelOCR;
import ihm.ocr.PanelCharacterOCR;
import ihm.ocr.PanelFindLine;
import ihm.util.ImageViewer;
import model.CharacterOCR;
import model.LineOCR;

public class ActionFindLine extends ActionOcr {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4389402422581243423L;

	private static final Logger LOGGER = Logger.getLogger(ActionFindLine.class);

	private static final String[] NOMS_IMAGES = {"Rescale Images","Grey Scale Image"};

	private JPanel panelOption;

	private JButton buttonDelette;

	private JButton buttonJoin;

	private JButton buttonChangeImage;

	private JComboBox<String> comboBoxImages;

	private PanelFindLine panelFindLine;

	private JButton getButtonDelette() {
		if(buttonDelette == null) {
			buttonDelette = new JButton();
			buttonDelette.setText("Delete");
			buttonDelette.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					CharacterOCR[] tab = getPanelFindLine().getSelectedCharacter();
					for(CharacterOCR characterOCR : tab) {
						characterOCR.delete();
					}
				}
			});
		}
		return buttonDelette;
	}

	private JButton getButtonJoin() {
		if(buttonJoin == null) {
			buttonJoin = new JButton();
			buttonJoin.setText("Join");
			buttonJoin.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					CharacterOCR[] tab = getPanelFindLine().getSelectedCharacter();
					if(tab.length<2) {
						LOGGER.info("Character not join: "+tab.length+"<2");
						return;
					}
					LineOCR lineOCR  = tab[0].getLineOCR();
					lineOCR.joinCharacter(tab);
					getPanelFindLine().unSelectAll();
					LOGGER.info(tab.length+" Character joins");
				}
			});
		}
		return buttonJoin;
	}

	private List<LineOCR> linesOCR = new ArrayList<LineOCR>();

	private List<Rectangle> rectangleLine = new ArrayList<Rectangle>();

	private List<Rectangle> rectangleChar = new ArrayList<Rectangle>();

	public ActionFindLine() {
		super("Find line");
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		setActive(!isActive());
		if(isActive()) {
			linesOCR.clear();
			rectangleChar.clear();
			rectangleLine.clear();
			try {
				ActionOcr actionRescale = ActionOcr.getAction(ActionRescale.class.getName());
				BufferedImage imageSGrey = getImageStart();
				BufferedImage imageSFirst = actionRescale.getImageEndAction();
				FilterLine filter = new FilterLine(imageSGrey.getHeight());  
				ImageProducer producer = new FilteredImageSource(imageSGrey.getSource(), filter); 
				Image image = Toolkit.getDefaultToolkit().createImage(producer); 
				ImageUtil.toBufferedImage(image);
				Point[] tab = filter.getResult();
				int indexLine = 0;
				int indexChar = 0; 
				int indexStartLine = 0;
				int indexStartChar = 0;
				LOGGER.debug("imageS.width: "+imageSGrey.getWidth()+"\timageS.height: "+imageSGrey.getHeight());
				for(Point p : tab) {

					rectangleLine.add(new Rectangle(0, indexStartLine, imageSGrey.getWidth(), p.x-indexStartLine));
					indexStartLine = p.y;



					BufferedImage imgGreyScale = imageSGrey.getSubimage(0, p.x, imageSGrey.getWidth(), p.y-p.x);
					BufferedImage imgFirst = imageSFirst.getSubimage(0, p.x, imageSGrey.getWidth(), p.y-p.x);
					LineOCR lineOCR = new LineOCR(p.x, imgFirst,imgGreyScale);
					linesOCR.add(lineOCR);
					//					ImageIO.write(img, "png", new File("tmp_find_line_"+indexLine+".png"));

					FilterLine filter1 = new FilterLine(imgGreyScale.getWidth(),false);  
					ImageProducer producer1 = new FilteredImageSource(imgGreyScale.getSource(), filter1); 
					Image image1 = Toolkit.getDefaultToolkit().createImage(producer1); 
					ImageUtil.toBufferedImage(image1);
					Point[] tab1 = filter1.getResult();
					indexChar = 0;
					for(Point p1 : tab1) {
						rectangleChar.add(new Rectangle(indexStartChar, p.x, p1.x-indexStartChar,imgGreyScale.getHeight()));
						indexStartChar = p1.y;
						BufferedImage imgCharGrey = imgGreyScale.getSubimage(p1.x, 0, p1.y-p1.x, imgGreyScale.getHeight());
						BufferedImage imgCharFirst = imgFirst.getSubimage(p1.x, 0, p1.y-p1.x, imgGreyScale.getHeight());
						lineOCR.addCharacter(new CharacterOCR(p1.x,lineOCR,imgCharFirst, imgCharGrey));
						ImageIO.write(imgCharGrey, "png", new File("tmp\\tmp_find_line_"+indexLine+"_char"+indexChar+".png"));
						indexChar++;
					}
					Point p1 = tab[tab.length-1];
					rectangleChar.add(new Rectangle(indexStartChar, p.x, imageSGrey.getWidth()-p1.y,imgGreyScale.getHeight()));
					indexStartChar = 0;
					indexLine++;
				}
				Point p = tab[tab.length-1];
				rectangleLine.add(new Rectangle(0, p.y, imageSGrey.getWidth(), imageSGrey.getHeight()-p.y));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			getPanelFindLine().init(linesOCR.toArray(new LineOCR[0]));

			PanelOCR.get().getPanelImage().add(getPanelFindLine());
			getPanelFindLine().setLocation(0, 0);

			this.setDone(true);
			PanelOCR.get().getPanelImage().doLayout();
			PanelOCR.get().getPanelImage().repaint();
		}

	}

	@Override
	public JPanel getPanelOption() {
		if(panelOption == null) {
			panelOption = new JPanel();
			panelOption.setLayout(new BoxLayout(panelOption, BoxLayout.Y_AXIS));
			panelOption.add(getButtonDelette());
			panelOption.add(getButtonJoin());
			JPanel panelChangeImage = new JPanel();
			panelChangeImage.setLayout(new BorderLayout());
			panelChangeImage.add(getComboBoxImages(), BorderLayout.CENTER);
			panelChangeImage.add(getButtonChangeImage(), BorderLayout.EAST);
			panelOption.add(panelChangeImage);
		}

		return panelOption;
	}

	/**
	 * @return the rectangleChar
	 */
	public Rectangle[] getRectangleChar() {
		return rectangleChar.toArray(new Rectangle[0]);
	}

	/**
	 * @return the rectangleLine
	 */
	public Rectangle[] getRectangleLine() {
		return rectangleLine.toArray(new Rectangle[0]);
	}

	/**
	 * @return the comboBoxImages
	 */
	public JComboBox<String> getComboBoxImages() {
		if(comboBoxImages == null) {
			comboBoxImages = new JComboBox<String>();
			for(String st : NOMS_IMAGES) {
				comboBoxImages.addItem(st);
			}
		}
		return comboBoxImages;
	}

	/**
	 * @return the buttonChangeImage
	 */
	public JButton getButtonChangeImage() {
		if(buttonChangeImage == null) {
			buttonChangeImage = new JButton("Change");
			buttonChangeImage.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					CharacterOCR[] tab = getPanelFindLine().getSelectedCharacter();
					switch (getComboBoxImages().getSelectedIndex()) {
					case 0:
						for(CharacterOCR charOCR : tab) {
							charOCR.useImageFirst();
						}
						break;
					case 1:
						for(CharacterOCR charOCR : tab) {
							charOCR.useImageGreyScale();
						}
						break;
					default:
						break;
					}
				}
			});
		}
		return buttonChangeImage;
	}

	/**
	 * @return the panelFindLine
	 */
	public PanelFindLine getPanelFindLine() {
		if(panelFindLine == null) {
			panelFindLine = new PanelFindLine();
		}
		return panelFindLine;
	}
}
