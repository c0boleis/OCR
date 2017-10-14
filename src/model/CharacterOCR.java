package model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ihm.PanelOCR;

public class CharacterOCR {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3358091586792571400L;
	
	private static final Logger LOGGER = Logger.getLogger(CharacterOCR.class);

	private int position;
	
	private BufferedImage image;
	


	public CharacterOCR(int p, BufferedImage image) {
		this.position = p;
		this.image = image;
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	public int getPosition() {
		return this.position;
	}

	/**
	 * @return the postition
	 */
//	public Point getPostition() {
//		double zoomFact = PanelOCR.get().getPanelImage().getZoomFact();
//		int xImage = (int) (postition.x*zoomFact);
//		int yImage = (int) (postition.x*zoomFact);
//		return new Point(xImage, yImage);
//	}
	
	

}
