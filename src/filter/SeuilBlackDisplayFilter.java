package filter;

import java.awt.image.RGBImageFilter;

public class SeuilBlackDisplayFilter extends RGBImageFilter{

	private int sueilMin = 155;

	private int sueilMax = 155;

	public SeuilBlackDisplayFilter() {
	}

	public SeuilBlackDisplayFilter(int min,int max) {
		this.sueilMin = min;
		this.sueilMax = max;
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
		if(grey<=sueilMax && grey>=sueilMin) {
			return (rgb & 0xff000000) | 0x000000ff;
		}else {
			return rgb ;
		}
	}

}
