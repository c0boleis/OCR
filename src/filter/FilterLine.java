package filter;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import ihm.ImageUtil;

public class FilterLine extends RGBImageFilter{
	
	private int[] table;
	
	private boolean line = true;
	
	private static final int LIMIT_LINE = 30;
	
	private static final int LIMIt_CHAR = 0;
	
	private int limit = LIMIT_LINE;
	
	public static void main(String[] args) {
		BufferedImage imageS;
		try {
			imageS = ImageIO.read(new File("tmp_gomme.png"));
			FilterLine filter = new FilterLine(imageS.getHeight());  
			ImageProducer producer = new FilteredImageSource(imageS.getSource(), filter); 
			Image image = Toolkit.getDefaultToolkit().createImage(producer); 
			ImageUtil.toBufferedImage(image);
			Point[] tab = filter.getResult();
			int index = 0;
			int indexChar = 0; 
			for(Point p : tab) {
				BufferedImage img = imageS.getSubimage(0, p.x, imageS.getWidth(), p.y-p.x);
				ImageIO.write(img, "png", new File("tmp_find_line_"+index+".png"));
				
				
				FilterLine filter1 = new FilterLine(img.getWidth(),false);  
				ImageProducer producer1 = new FilteredImageSource(img.getSource(), filter1); 
				Image image1 = Toolkit.getDefaultToolkit().createImage(producer1); 
				ImageUtil.toBufferedImage(image1);
				Point[] tab1 = filter1.getResult();
				indexChar = 0;
				for(Point p1 : tab1) {
					BufferedImage img1 = img.getSubimage(p1.x, 0, p1.y-p1.x, img.getHeight());
					ImageIO.write(img1, "png", new File("tmp_find_line_"+index+"_char"+indexChar+".png"));
					indexChar++;
				}
				index++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public FilterLine(int heightImage) {
		super();
		table = new int[heightImage];
		for(int index = 0;index<table.length;index++) {
			table[index] = 0;
		}
	}
	
	public FilterLine(int heightImage,boolean l) {
		this(heightImage);
		this.line = l;
		if(!l) {
			this.limit = LIMIt_CHAR;
		}
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

//		System.out.print("rgb: "+rgb+"\tgrey: "+grey);
		if(grey<155) {
//			System.out.print("\tOK");
			if(line) {
				table[y]++;
			}else {
				table[x]++;
			}
		}
		return rgb;
	}
	
	public Point[] getResult() {
		boolean line = false;
		int indexLine = 0;
		List<Point> listOut = new ArrayList<Point>();
		for(int index = 0;index<table.length;index++) {
			if(!line && table[index]>this.limit) {
				indexLine = index;
				line=true;
			}else if(line && table[index]<=this.limit) {
				listOut.add(new Point(indexLine, index));
				line = false;
			}
		}
		return listOut.toArray(new Point[0]);
	}

}
