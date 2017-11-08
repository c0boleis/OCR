package filter;

import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

import javafx.scene.control.Tab;

public class ImageUtilCalcul {
	
	public static final Logger LOGGER = Logger.getLogger(ImageUtilCalcul.class);

	
	public static int greyMoy(BufferedImage image) {
		int width = image.getWidth();
		int height= image.getHeight();
		
		int[] dataBuffInt = image.getRGB(0, 0, width, height, null, 0, width);
		int sum = 0;
		for(int rgb : dataBuffInt) {
			int r = (int) ((rgb &  0x00ff0000) >> 16);
			int g = (int) ((rgb &  0x0000ff00) >> 8);
			int b = (int) (rgb &  0x000000ff);

			sum += r+g+b;
		}
		return sum/(dataBuffInt.length*3);
	}
	
	public static int[] greyTabMoy(BufferedImage image) {
		int width = image.getWidth();
		int height= image.getHeight();
		int[] out = new int[255];
		for(int index = 0;index<out.length;index++) {
			out[index] = 0;
		}
		int[] dataBuffInt = image.getRGB(0, 0, width, height, null, 0, width);
		int sum = 0;
		for(int rgb : dataBuffInt) {
			int r = (int) ((rgb &  0x00ff0000) >> 16);
			int g = (int) ((rgb &  0x0000ff00) >> 8);
			int b = (int) (rgb &  0x000000ff);

			sum = (r+g+b)/3;
			out[sum]++;
		}
		for(int index = 0;index<out.length;index++) {
			LOGGER.debug(index+": "+out[index]);
		}
		return out;
	}

}
