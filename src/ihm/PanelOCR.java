package ihm;


import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.apache.log4j.Logger;

import ihm.options.PanelOptions;
import ihm.progression.PanelProgression;

public class PanelOCR extends JPanel{

	private static final PanelOCR INSTANCE = new PanelOCR();
	
	private static final Logger LOGGER = Logger.getLogger(PanelOCR.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -729337384797862733L;
	
	public static PanelOCR get() {
		return INSTANCE;
	}
	
	public static BufferedImage getImage() {
		return get().getPanelImage().getImage();
	}
	
	public static void setImage(BufferedImage image) {
		get().getPanelImage().setImage(image);
	}
	
	private JSplitPane MainSliderPane;
	
	private PanelImage panelImage;
	
	private PanelOptions panelOptions;

	private PanelProgression panelProgression;

	private JScrollPane scrollPaneImage;

	private PanelOCR() {
		super();
		init();
	}

	private JSplitPane getMainSliderPane() {
		if(MainSliderPane == null) {
			MainSliderPane= new JSplitPane();
			MainSliderPane.setRightComponent(getScrollPaneImage());
			MainSliderPane.setLeftComponent(getPanelOptions());
		}
		return MainSliderPane;
	}

	public PanelImage getPanelImage() {
		if(panelImage == null) {
			panelImage = new PanelImage();
		}
		return panelImage;
	}
	
	private PanelOptions getPanelOptions() {
		if(panelOptions == null) {
			panelOptions = new PanelOptions();
		}
		return panelOptions;
	}
	
	private PanelProgression getPanelProgression() {
		if(panelProgression == null) {
			panelProgression = new PanelProgression();
		}
		return panelProgression;
	}
	
	private JScrollPane getScrollPaneImage() {
		if(scrollPaneImage == null) {
			scrollPaneImage= new JScrollPane(getPanelImage());
		}
		return scrollPaneImage;
	}
	
	private void init() {
		this.setLayout(new BorderLayout());
		this.add(getMainSliderPane(), BorderLayout.CENTER);
		this.add(getPanelProgression(), BorderLayout.SOUTH);
	}

}
