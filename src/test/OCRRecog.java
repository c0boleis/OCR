package test;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import filter.FilterResize;
import model.OCRResult;

public class OCRRecog {

	public static void main(String[] args) {
		File folder = new File("image_ocr\\lettres");
		File[] files = folder.listFiles();
		for(File file : files) {
			try {
				BufferedImage imageTest = ImageIO.read(file);
				char c = getChar(imageTest);
				String name = file.getName();
				String test = name.charAt(0)==c?"OK":"KO############";
				System.out.println(test+"\t file: "+file.getName()+" --> "+c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static char getChar(BufferedImage imageTest) {
		FilterResize filter = new FilterResize(imageTest);  
		filter.process();
		imageTest = filter.getImageResize();
		File dossierRef = new File("lettres");
		File[] files = dossierRef.listFiles();
		BufferedImage imageRef= null;
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		List<OCRResult> resultList = new ArrayList<OCRResult>();
		for(File file : files) {
			String Letter = String.valueOf(file.getName().charAt(0));
//			System.out.println(Letter);
			try {
				imageRef = ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			int hMax = Math.max(imageTest.getHeight(), imageRef.getHeight());
			int wMax = Math.max(imageTest.getWidth(), imageRef.getWidth());
			// on met les images à la même taille
			imageRef = OCRRecog.setSize(wMax, hMax, imageRef);
			imageTest = OCRRecog.setSize(wMax, hMax, imageTest);
			//on récuperrer la data
			int[] dataRef = imageRef.getRGB(0, 0, wMax, hMax, null, 0, wMax); 
			int[] dataTest = imageTest.getRGB(0, 0, wMax, hMax, null, 0, wMax); 
			int diff = 0;
			for(int index = 0;index<dataRef.length;index++) {
				if(dataRef[index]== dataTest[index]) {
					diff++;
				}else {
					diff--;
				}
			}
			resultList.add(new OCRResult(Letter.charAt(0), diff));
			result.put(Letter, new Integer(diff));
		}
		Collections.sort(resultList, new Comparator<OCRResult>() {

			@Override
			public int compare(OCRResult o1, OCRResult o2) {
				return Long.compare(o2.getResult(), o1.getResult());
			}
		});
//		for(String st :result.keySet()) {
//			System.out.println(st+" : "+result.get(st));
//		}
//		for(OCRResult st :resultList) {
//			System.out.println(st.getChar()+" : "+st.getResult());
//		}
		return resultList.get(0).getChar();
	}

	public static BufferedImage setSize(int width,int height,BufferedImage imageIn) {
		if(width == imageIn.getWidth() && height == imageIn.getHeight()) {
			return imageIn;
		}

		Image imgTmp = imageIn.getScaledInstance(width, height, Image.SCALE_FAST);
	    BufferedImage bimage = new BufferedImage(imgTmp.getWidth(null), imgTmp.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(imgTmp, 0, 0, null);
	    bGr.dispose();
	    return bimage;
	}

}
