package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import ihm.util.ImageViewer;

public class LineOCR {
	
	private int postion = 0;
	
	private List<CharacterOCR> characters = new ArrayList<>();
	
	private List<LineOCRListener> listeners = new ArrayList<LineOCRListener>();
	
	private BufferedImage imageGreyScale;
	
	private BufferedImage imageFirst;

	private boolean useImageGrey = true;
	
	public LineOCR(int pos,BufferedImage imgFirst,BufferedImage imgGreyScale) {
		this.postion = pos;
		this.imageGreyScale = imgGreyScale;
		this.imageFirst = imgFirst;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + postion;
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
		if (!(obj instanceof LineOCR)) {
			return false;
		}
		LineOCR other = (LineOCR) obj;
		if (postion != other.postion) {
			return false;
		}
		return true;
	}

	public void addCharacter(CharacterOCR charOCR) {
		if(!characters.contains(charOCR)) {
			characters.add(charOCR);
			fireCharacterOCRAdd(charOCR);
		}
	}
	
	public void removeCharacter(CharacterOCR charOCR) {
		if(characters.remove(charOCR)) {
			fireCharacterOCRRemove(charOCR);
		}
	}
	
	public CharacterOCR[] getCharacters() {
		return characters.toArray(new CharacterOCR[0]);
	}
	
	public int getPosition() {
		return this.postion;
	}

	public BufferedImage getImage() {
		if(useImageGrey) {
			return imageGreyScale;
		}else {
			return imageFirst;
		}
		
	}
	
	public void setImage(BufferedImage img) {
		this.imageGreyScale = img;
	}
	
	public void useImageFirst() {
		this.useImageGrey  = false;
	}
	
	public void useImageGreyScale() {
		this.useImageGrey = true;
	}
	
	
	public String joinCharacter(CharacterOCR[] tabCharOCR) {
		if(tabCharOCR.length<2) {
			return "La liste est trop petite. taille <2";
		}
		int[] listX = new int[tabCharOCR.length];
		int index=0;
		for(CharacterOCR nbr : tabCharOCR) {
			listX[index] = nbr.getAbsolutPostion().x;
			index++;
		}
		/*
		 * on récupère les coordonées
		 */
		int xMin = tabCharOCR[0].getAbsolutPostion().x;
		int xMax = tabCharOCR[tabCharOCR.length-1].getAbsolutPostion().x + tabCharOCR[tabCharOCR.length-1].getImage().getWidth();
		
		int w = xMax-xMin;
		/*
		 * on récupère les actions concernées.
		 */

		BufferedImage imgFirst = this.imageFirst.getSubimage(xMin, 0, w, this.imageFirst.getHeight());
		BufferedImage imgGrey = this.imageGreyScale.getSubimage(xMin, 0, w, this.imageGreyScale.getHeight());

		
		tabCharOCR[0].setImageFirst(imgFirst);
		tabCharOCR[0].setImageGrey(imgGrey);
		
		for(index = 1;index<tabCharOCR.length;index++) {
			this.removeCharacter(tabCharOCR[index]);
		}
		
		return "Not yet implement";
	}
	
	public static int max(int[] tab) {
		int max = tab[0];
		for(int k : tab) {
			if(k>max) {
				max = k;
			}
		}
		return max;
	}
	
	public static int min(int[] tab) {
		int min = tab[0];
		for(int k : tab) {
			if(k<min) {
				min = k;
			}
		}
		return min;
	}
	
	public boolean addLineOCRListener(LineOCRListener listener) {
		if(!this.listeners.contains(listener)) {
			this.listeners.add(listener);
			return true;
		}
		return false;
	}
	
	public boolean removeLineOCRListener(LineOCRListener listener) {
		return this.listeners.remove(listener);
	}

	public LineOCRListener[] getListeners() {
		return this.listeners.toArray(new LineOCRListener[0]);
	}
	
	protected void fireCharacterOCRAdd(CharacterOCR characterOCR) {
		 LineOCRListener[] tmp = getListeners();
		 for(LineOCRListener listener : tmp) {
			 listener.characterOCRAdd(characterOCR);
		 }
	}

	protected void fireCharacterOCRRemove(CharacterOCR characterOCR) {
		 LineOCRListener[] tmp = getListeners();
		 for(LineOCRListener listener : tmp) {
			 listener.characterOCRemove(characterOCR);
		 }
	}
}
