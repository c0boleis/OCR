package ihm.actions;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import filter.FilterLine;
import ihm.ImageUtil;
import ihm.PanelOCR;
import ihm.ocr.PanelFindLine;
import javafx.scene.shape.Line;
import model.CharacterOCR;
import model.LineOCR;

public class ActionFindLine extends ActionOcr {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4389402422581243423L;
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ActionFindLine.class);
	
	private JPanel panelOption;
	
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
				BufferedImage imageS = getImageStart();
				FilterLine filter = new FilterLine(imageS.getHeight());  
				ImageProducer producer = new FilteredImageSource(imageS.getSource(), filter); 
				Image image = Toolkit.getDefaultToolkit().createImage(producer); 
				ImageUtil.toBufferedImage(image);
				Point[] tab = filter.getResult();
				int indexLine = 0;
				int indexChar = 0; 
				int indexStartLine = 0;
				int indexStartChar = 0;
				LOGGER.debug("imageS.width: "+imageS.getWidth()+"\timageS.height: "+imageS.getHeight());
				for(Point p : tab) {
					
					rectangleLine.add(new Rectangle(0, indexStartLine, imageS.getWidth(), p.x-indexStartLine));
					indexStartLine = p.y;
					
					
					
					BufferedImage img = imageS.getSubimage(0, p.x, imageS.getWidth(), p.y-p.x);
					LineOCR lineOCR = new LineOCR(p.x, img);
					linesOCR.add(lineOCR);
//					ImageIO.write(img, "png", new File("tmp_find_line_"+indexLine+".png"));
					
					FilterLine filter1 = new FilterLine(img.getWidth(),false);  
					ImageProducer producer1 = new FilteredImageSource(img.getSource(), filter1); 
					Image image1 = Toolkit.getDefaultToolkit().createImage(producer1); 
					ImageUtil.toBufferedImage(image1);
					Point[] tab1 = filter1.getResult();
					indexChar = 0;
					for(Point p1 : tab1) {
						rectangleChar.add(new Rectangle(indexStartChar, p.x, p1.x-indexStartChar,img.getHeight()));
						indexStartChar = p1.y;
						BufferedImage img1 = img.getSubimage(p1.x, 0, p1.y-p1.x, img.getHeight());
						lineOCR.addCharacter(new CharacterOCR(p1.x, img1));
						ImageIO.write(img1, "png", new File("tmp\\tmp_find_line_"+indexLine+"_char"+indexChar+".png"));
						indexChar++;
					}
					Point p1 = tab[tab.length-1];
					rectangleChar.add(new Rectangle(indexStartChar, p.x, imageS.getWidth()-p1.y,img.getHeight()));
					indexStartChar = 0;
					indexLine++;
				}
				Point p = tab[tab.length-1];
				rectangleLine.add(new Rectangle(0, p.y, imageS.getWidth(), imageS.getHeight()-p.y));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			PanelFindLine panelFindLine = new PanelFindLine(linesOCR.toArray(new LineOCR[0]));
//			JFrame frame = new JFrame("OCR Find Line");
//			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//			frame.setLayout(new BorderLayout());
//			frame.setSize(400, 400);
//			frame.add(panelFindLine, BorderLayout.CENTER);
//			frame.setVisible(true);
			
			PanelOCR.get().getPanelImage().add(panelFindLine);
			panelFindLine.setLocation(0, 0);
			
			this.setDone(true);
			PanelOCR.get().getPanelImage().doLayout();
			PanelOCR.get().getPanelImage().repaint();
		}
		
	}

	@Override
	public JPanel getPanelOption() {
		if(panelOption == null) {
			panelOption = new JPanel();
			
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
	

}
