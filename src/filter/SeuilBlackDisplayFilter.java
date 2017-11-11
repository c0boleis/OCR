package filter;

import java.awt.image.RGBImageFilter;

import model.RGBSeuilFact;

public class SeuilBlackDisplayFilter extends RGBImageFilter{

	private RGBSeuilFact rgbSeuilFact;

	public SeuilBlackDisplayFilter() {
	}

	public SeuilBlackDisplayFilter(RGBSeuilFact fact) {
		this.rgbSeuilFact = fact;
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
		
		if(r<=this.rgbSeuilFact.getFactRed().getValMax() && r>=this.rgbSeuilFact.getFactRed().getValMin()) {
			if(g<=this.rgbSeuilFact.getFactGreen().getValMax() && g>=this.rgbSeuilFact.getFactGreen().getValMin()) {
				if(b<=this.rgbSeuilFact.getFactBlue().getValMax() && b>=this.rgbSeuilFact.getFactBlue().getValMin()) {
					return  (rgb & 0xff000000) | 0x000000ff;
				}
			}
		}
		
		return rgb ;
		
	}

}
