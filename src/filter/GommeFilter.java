package filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RGBImageFilter;

import ihm.MainFrame;
import ihm.actions.ActionGomme;
import ihm.actions.ActionOcr;
import ihm.actions.PointGomme;

public class GommeFilter extends RGBImageFilter{
	
	
	private boolean[][] tab;
	
	
	public GommeFilter() {
		BufferedImage image = MainFrame.get().getPanelImage().getImage();
		int width = image.getWidth();
		int height = image.getHeight();
		//init tab;
		tab = new boolean[width][height];
		for(int indexW=0;indexW<width;indexW++) {
			for(int indexH=0;indexH<height;indexH++) {
				tab[indexW][indexH] = false;
			}
		}
		PointGomme[] points = ((ActionGomme)ActionOcr.getAction(ActionGomme.class.getName())).getPoints();
		for(PointGomme point : points) {
			processPoint(point);
		}
	}
	
	private void processPoint(PointGomme point) {
		//TODO
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
//		if(grey<sueil) {
//			return rgb & 0xff000000;
//		}else {
//			return rgb | 0x00ffffff;
//		}
		return 0;
	}

}
