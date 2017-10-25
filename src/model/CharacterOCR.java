package model;

import java.awt.Point;
import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

public class CharacterOCR {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(CharacterOCR.class);

	private int position;
	
	private BufferedImage imageGreyScale;
	
	private BufferedImage imageFirst;
	
	private boolean useImageGrey = false;
	
	private LineOCR lineOCR;

	public CharacterOCR(int p,LineOCR line, BufferedImage imgFirst, BufferedImage imgGrey) {
		this.position = p;
		this.imageGreyScale = imgGrey;
		this.imageFirst = imgFirst;
		this.lineOCR = line;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lineOCR == null) ? 0 : lineOCR.hashCode());
		result = prime * result + position;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CharacterOCR)) {
			return false;
		}
		CharacterOCR other = (CharacterOCR) obj;
		if (lineOCR == null) {
			if (other.lineOCR != null) {
				return false;
			}
		} else if (!lineOCR.equals(other.lineOCR)) {
			return false;
		}
		if (position != other.position) {
			return false;
		}
		return true;
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		if(useImageGrey) {
			return imageGreyScale;
		}else {
			return imageFirst;
		}
		
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public void setImageFirst(BufferedImage img) {
		this.imageFirst = img;
	}
	
	public void setImageGrey(BufferedImage img) {
		this.imageGreyScale = img;
	}
	
	public void useImageFirst() {
		this.useImageGrey = false;
	}
	
	public void useImageGreyScale() {
		this.useImageGrey = true;
	}


	
	public Point getAbsolutPostion() {
		int x = getPosition();
		int y = this.lineOCR.getPosition();
		return new Point(x, y);
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

	/**
	 * @return the lineOCR
	 */
	public LineOCR getLineOCR() {
		return lineOCR;
	}
	
	public void delete() {
		this.lineOCR.removeCharacter(this);
	}
	
	

}
