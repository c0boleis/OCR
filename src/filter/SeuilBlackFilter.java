package filter;

import java.awt.image.RGBImageFilter;

public class SeuilBlackFilter extends RGBImageFilter{
	
	private int sueil = 155;
	
	public SeuilBlackFilter() {
	}
	
	public SeuilBlackFilter(int s) {
		this.sueil = s;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.image.RGBImageFilter#filterRGB(int, int, int)
	 */
	@Override
	public int filterRGB(int x, int y, int rgb) {
		int r = (int) ((rgb &  0x00ff0000) >> 16);
		int g = (int) ((rgb &  0x0000ff00) >> 8);
		int b = (int) (rgb &  0x000000ff);
//        System.out.println("r: "+r+"\tg: "+g+"\tb: "+b);
		int grey = ((r+g+b)/3);
		if(grey<sueil) {
			return rgb & 0xff000000;
		}else {
			return rgb | 0x00ffffff;
		}
	}

}
