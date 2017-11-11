package ihm.options;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import filter.ImageUtilCalcul;
import ihm.util.NumberUtil;
import model.RGBSeuilFact;

public class PanelSeuilRGB extends JPanel{

	private static final Logger LOGGER = Logger.getLogger(PanelSeuilRGB.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1181929149759401752L;
	
	private PanelSeuil panelSeuilRed;
	
	private PanelSeuil panelSeuilGreen;

	private PanelSeuil panelSeuilBlue;
	
	private BufferedImage image;
	
	private int[] red;
	
	private int[] green;
	
	private int[] blue;
	
	private int max = 255;
	
	private RGBSeuilFact rgbSeuilFact;

	public PanelSeuilRGB(BufferedImage imageStart) {
		super();
		this.image = imageStart;
		rgbSeuilFact = new RGBSeuilFact();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		red = ImageUtilCalcul.greyTabRed(image);
		green = ImageUtilCalcul.greyTabGreen(image);
		blue = ImageUtilCalcul.greyTabBlue(image);
		int maxRed =NumberUtil.getMax(red);
		int maxGreen =NumberUtil.getMax(green);
		int maxBlue =NumberUtil.getMax(blue);
		max = NumberUtil.getMax(maxRed,maxGreen,maxBlue);
		this.add(getPanelSeuilRed());
		this.add(getPanelSeuilGreen());
		this.add(getPanelSeuilBlue());
	}

	/**
	 * @return the panelSeuilRed
	 */
	private PanelSeuil getPanelSeuilRed() {
		if(panelSeuilRed == null) {
			panelSeuilRed = new PanelSeuil(ImageUtilCalcul.greyTabRed(image),Color.RED,this.rgbSeuilFact.getFactRed());
			panelSeuilRed.setMax(max);
		}
		return panelSeuilRed;
	}

	/**
	 * @return the panelSeuilGreen
	 */
	private PanelSeuil getPanelSeuilGreen() {
		if(panelSeuilGreen == null) {
			panelSeuilGreen = new PanelSeuil(ImageUtilCalcul.greyTabGreen(image),Color.green,this.rgbSeuilFact.getFactGreen());
			panelSeuilGreen.setMax(max);
		}
		return panelSeuilGreen;
	}

	/**
	 * @return the panelSeuilBlue
	 */
	private PanelSeuil getPanelSeuilBlue() {
		if(panelSeuilBlue == null) {
			panelSeuilBlue = new PanelSeuil(ImageUtilCalcul.greyTabBlue(image),Color.BLUE,this.rgbSeuilFact.getFactBlue());
			panelSeuilBlue.setMax(max);
		}
		return panelSeuilBlue;
	}
	
	public RGBSeuilFact getRGBSeuilFact() {
		return this.rgbSeuilFact;
	}


	
}
