package model;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

import org.apache.log4j.Logger;

import filter.SeuilBlackFilter;
import ihm.ImageUtil;
import ihm.PanelOCR;

public class CharacterOCR {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(CharacterOCR.class);

	private int position;
	
	private BufferedImage imageGreyScale;
	
	private BufferedImage imageFirst;
	
	private int greyFactMin = -1;
	
	private int greyFactMax = -1;
	
	private boolean useImageGrey = false;
	
	private LineOCR lineOCR;

	public CharacterOCR(int p,int greyFMin,int greyFMax, LineOCR line, BufferedImage imgFirst, BufferedImage imgGrey) {
		this.position = p;
		this.imageGreyScale = imgGrey;
		this.imageFirst = imgFirst;
		this.lineOCR = line;
		this.greyFactMin = greyFMin;
		this.greyFactMax = greyFMax;
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

	/**
	 * @return the greyFact
	 */
	public int getGreyFactMin() {
		return greyFactMin;
	}
	
	public int getGreyFactMax() {
		return greyFactMax;
	}

	/**
	 * @param greyFact the greyFact to set
	 */
	public void setGreyFact(int greyFact) {
		if(greyFact<0 || greyFact >255) {
			throw new IllegalArgumentException();
		}
		this.greyFactMin = greyFact;
		ImageFilter filter = new SeuilBlackFilter(greyFactMin,greyFactMax);  
		ImageProducer producer = new FilteredImageSource(this.imageFirst.getSource(), filter);  
		Image image = Toolkit.getDefaultToolkit().createImage(producer);  
		this.imageGreyScale = ImageUtil.toBufferedImage(image);
		
	}
	
	public void setGreyFactMax(int greyFact) {
		if(greyFact<0 || greyFact >255) {
			throw new IllegalArgumentException();
		}
		this.greyFactMax = greyFact;
		ImageFilter filter = new SeuilBlackFilter(greyFactMin,greyFactMax);  
		ImageProducer producer = new FilteredImageSource(this.imageFirst.getSource(), filter);  
		Image image = Toolkit.getDefaultToolkit().createImage(producer);  
		this.imageGreyScale = ImageUtil.toBufferedImage(image);
		
	}
	
	public void setGreyFactM(int greyFactMin,int greyFactMax) {
		if(greyFactMin<0 || greyFactMin >255) {
			throw new IllegalArgumentException();
		}
		if(greyFactMax<0 || greyFactMax >255) {
			throw new IllegalArgumentException();
		}
		this.greyFactMax = greyFactMax;
		this.greyFactMin = greyFactMin;
		ImageFilter filter = new SeuilBlackFilter(this.greyFactMin,this.greyFactMax);  
		ImageProducer producer = new FilteredImageSource(this.imageFirst.getSource(), filter);  
		Image image = Toolkit.getDefaultToolkit().createImage(producer);  
		this.imageGreyScale = ImageUtil.toBufferedImage(image);
		
	}
	
	public boolean isUseImageGrey() {
		return this.useImageGrey;
	}
	

}
