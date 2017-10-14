package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LineOCR {
	
	private int postion = 0;
	
	private List<CharacterOCR> characters = new ArrayList<>();
	
	private BufferedImage image;
	
	public LineOCR(int pos,BufferedImage image) {
		this.postion = pos;
		this.image = image;
	}
	
	public void addCharacter(CharacterOCR charOCR) {
		characters.add(charOCR);
	}
	
	public CharacterOCR[] getCharacters() {
		return characters.toArray(new CharacterOCR[0]);
	}
	
	public int getPosition() {
		return this.postion;
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

}
