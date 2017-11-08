package filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import ihm.ImageUtil;

public class FilterResize {

	private int xMax;

	private int xMin;

	private int yMax;

	private int yMin;

	private int r = 0;

	private int g = 0;

	private int b = 0;

	private BufferedImage image;
	
	private int[] dataBuffInt;
	
	private int width;
	private int height;

	public FilterResize(BufferedImage img) {
		this.image = img;
		xMax = -1;
		xMin = -1;
		yMax = -1;
		yMin = -1;

		width = image.getWidth();
		height= image.getHeight();

		this.dataBuffInt = image.getRGB(0, 0, width, height, null, 0, width); 
		
	}
	
	public void process() {
//		System.out.println("DEBUT process");
		for(int y = 0;y<height;y++) {
			for(int x = 0;x<width;x++) {
				 int pixel   = dataBuffInt[(y)*width + (x)];
				 filterRGB(x, y, pixel);
			}
		}
//		System.out.println("FIN process");
	}
	
	public int filterRGB(int x, int y, int rgb) {
		int r = (int) ((rgb &  0x00ff0000) >> 16);
		int g = (int) ((rgb &  0x0000ff00) >> 8);
		int b = (int) (rgb &  0x000000ff);

		if(this.r!=r || this.g != g || this.b !=b) {
//			System.out.println("r: "+r+"\tg: "+g+"\tb: "+b);
		}
		this.r = r;
		this.g = g;
		this.b = b;
		int sum = r+g+b;
		int limit = 10;
		//		System.out.println("rgb: "+rgb+"\tsum: "+sum);
		if(sum<=limit) {
			if(xMin<0) {
				xMin = x;
				xMax = x;
				yMin = y;
				yMax = y;
//				System.out.println("INIT");
			}else {
				if(x>xMax) {
					xMax = x;
//					System.out.println("X MAX: "+xMax);
				}
				if(x<xMin) {
					xMin = x;
//					System.out.println("X MIN: "+yMin);
				}
				if(y>yMax) {
					yMax = y;
//					System.out.println("Y MAX: "+yMax);
				}
				if(y<yMin) {
					yMin = y;
//					System.out.println("Y MIN: "+yMin);
				}
			}
		}
		return rgb;
	}



	/**
	 * @return the xMax
	 */
	public int getxMax() {
		return xMax;
	}

	/**
	 * @return the xMin
	 */
	public int getxMin() {
		return xMin;
	}

	/**
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	public BufferedImage getImageResize() {
		return this.image.getSubimage(xMin, yMin, xMax-xMin,yMax-yMin );
	}

}
