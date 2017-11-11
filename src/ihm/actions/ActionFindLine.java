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
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import filter.FilterLine;
import filter.SeuilBlackFilter;
import ihm.ImageUtil;
import ihm.PanelOCR;
import ihm.ocr.PanelCharacterOCR;
import ihm.ocr.PanelFindLine;
import ihm.ocr.PanelOCRListener;
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
	
	private JButton buttonSaveImageLetter;

	private JComboBox<String> comboBoxImages;

	private PanelFindLine panelFindLine;
	
	private JPanel panelChar;
	
	private JToggleButton toggleButtonGreyOrFirst;
	
	private JTextField textFieldGreyFact;
	
	private JButton buttonAutoCalcul;
	
	private JButton buttonRefresh;
	
	private PanelOCRListener panelOCRListener;
	
	private PanelCharacterOCR selctedPanelCharacterOCR;

	/**
	 * @return the panelChar
	 */
	private JPanel getPanelChar() {
		if(panelChar == null) {
			panelChar= new JPanel();
			panelChar.setBorder(new TitledBorder("Character"));
			panelChar.setLayout(new BorderLayout());
			JPanel panelSouth = new JPanel();
			panelSouth.setLayout(new BorderLayout());
			panelSouth.add(getButtonAutoCalcul(), BorderLayout.WEST);
			panelSouth.add(getButtonRefresh(),BorderLayout.CENTER);
			panelChar.add(panelSouth,BorderLayout.SOUTH);
			panelChar.add(getToggleButtonGreyOrFirst(), BorderLayout.NORTH);
			JLabel labelFact = new JLabel("Fact: ");
			panelChar.add(labelFact, BorderLayout.WEST);
			panelChar.add(getTextFieldGreyFact(), BorderLayout.CENTER);
		}
		return panelChar;
	}

	/**
	 * @return the toggleButtonGreyOrFirst
	 */
	private JToggleButton getToggleButtonGreyOrFirst() {
		if(toggleButtonGreyOrFirst==null) {
			toggleButtonGreyOrFirst = new JToggleButton("Grey/First");
			toggleButtonGreyOrFirst.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					if(selctedPanelCharacterOCR==null) {
						return;
					}
					if(toggleButtonGreyOrFirst.isSelected()) {
						selctedPanelCharacterOCR.getCharacterOCR().useImageFirst();
					}else {
						selctedPanelCharacterOCR.getCharacterOCR().useImageGreyScale();
					}
				}
			});
		}
		return toggleButtonGreyOrFirst;
	}

	/**
	 * @return the textFieldGreyFact
	 */
	private JTextField getTextFieldGreyFact() {
		if(textFieldGreyFact == null) {
			textFieldGreyFact = new JTextField();
		}
		return textFieldGreyFact;
	}

	/**
	 * @return the buttonAutoCalcul
	 */
	private JButton getButtonAutoCalcul() {
		if(buttonAutoCalcul == null) {
			buttonAutoCalcul = new JButton("Auto fact");
			buttonAutoCalcul.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(selctedPanelCharacterOCR==null) {
						return;
					}
					CharacterOCR charOCR = selctedPanelCharacterOCR.getCharacterOCR();
					charOCR.useImageFirst();
					int k = filter.ImageUtilCalcul.greyMoy(charOCR.getImage());
					int fact = ((255-k)/4)+k;
					getTextFieldGreyFact().setText(String.valueOf(fact));
				}
			});
		}
		return buttonAutoCalcul;
	}

	/**
	 * @return the buttonRefresh
	 */
	private JButton getButtonRefresh() {
		if(buttonRefresh == null) {
			buttonRefresh = new JButton("Refresh");
			buttonRefresh.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(selctedPanelCharacterOCR==null) {
						return;
					}
					panelFindLine.repaint();
					CharacterOCR charOCR = selctedPanelCharacterOCR.getCharacterOCR();
					int sInt = -1;
					try {
						sInt = Integer.parseInt(getTextFieldGreyFact().getText().trim());
					}catch (NumberFormatException exp) {
						return;
					}
					if(sInt<1 || sInt>255) {
						return;
					}
					ImageFilter filter = new SeuilBlackFilter(sInt-5,sInt+5);  
					charOCR.useImageFirst();
					charOCR.setGreyFact(sInt);
					ImageProducer producer = new FilteredImageSource(charOCR.getImage().getSource(), filter);  
					Image image = Toolkit.getDefaultToolkit().createImage(producer);  
					charOCR.setImageGrey(ImageUtil.toBufferedImage(image));
					charOCR.useImageGreyScale();
				}
			});
		}
		return buttonRefresh;
	}


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
			int greyFactMin = 0;
			int greyFactMax = 155;
			//TODO
//			int greyFactMin = (int) ((ActionGreyScale)ActionOcr.getAction(ActionGreyScale.class.getName())).getPanelSeuil().getValMin();
//			int greyFactMax = (int) ((ActionGreyScale)ActionOcr.getAction(ActionGreyScale.class.getName())).getPanelSeuil().getValMax();
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
						lineOCR.addCharacter(new CharacterOCR(p1.x,greyFactMin,greyFactMax,lineOCR,imgCharFirst, imgCharGrey));
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
			panelOption.add(getButtonSaveImageLetter());
			panelOption.add(getPanelChar());
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
			panelFindLine.addPanelOCRListener(getPanelOCRListener());
		}
		return panelFindLine;
	}

	/**
	 * @return the panelOCRListener
	 */
	public PanelOCRListener getPanelOCRListener() {
		if(panelOCRListener == null) {
			panelOCRListener = new PanelOCRListener() {
				
				@Override
				public void characterOCRUnSelected(PanelCharacterOCR charOCR) {
//					changeSelection(charOCR);
					
				}
				
				@Override
				public void characterOCRSelected(PanelCharacterOCR charOCR) {
					changeSelection(charOCR);
					LOGGER.debug("charOCR selected");
				}
			};
		}
		return panelOCRListener;
	}
	
	private void changeSelection(PanelCharacterOCR charOCR) {
		selctedPanelCharacterOCR = charOCR;
		if(charOCR.getCharacterOCR().isUseImageGrey()) {
			this.getToggleButtonGreyOrFirst().setSelected(true);
		}else {
			this.getToggleButtonGreyOrFirst().setSelected(false);
		}
		this.getTextFieldGreyFact().setText(String.valueOf(charOCR.getCharacterOCR().getGreyFactMin()));
	}

	/**
	 * @return the buttonSaveImageLetter
	 */
	public JButton getButtonSaveImageLetter() {
		if(buttonSaveImageLetter == null) {
			buttonSaveImageLetter = new JButton();
			buttonSaveImageLetter.setText("Save Letter");
			buttonSaveImageLetter.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					CharacterOCR[] tmp = getPanelFindLine().getAllCharacter();
					int index = 0;
					for(CharacterOCR c : tmp) {
						c.useImageFirst();
						try {
							ImageIO.write(c.getImage(), "png", new File("tmp\\l_"+index+".png"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						index++;
					}
				}
			});
		}
		return buttonSaveImageLetter;
	}
}
