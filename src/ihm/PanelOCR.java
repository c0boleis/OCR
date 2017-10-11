package ihm;


import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.apache.log4j.Logger;

import ihm.actions.ActionOCRCalcul;
import ihm.actions.ActionOCRListener;
import ihm.actions.ActionOcr;
import ihm.options.PanelOptions;
import ihm.progression.PanelProgression;
import model.OCRTransaction;
import model.TransactionAvaibleListener;

public class PanelOCR extends JPanel{

	private static final PanelOCR INSTANCE = new PanelOCR();
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(PanelOCR.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -729337384797862733L;
	
	private static final List<TransactionAvaibleListener> listeners = new ArrayList<TransactionAvaibleListener>();
	
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
		ActionOCRCalcul actionOCRCalcul = (ActionOCRCalcul) ActionOcr.getAction(ActionOCRCalcul.class.getName());
		actionOCRCalcul.addActionListener(new ActionOCRListener() {
			
			/*
			 * (non-Javadoc)
			 * @see ihm.actions.ActionOCRListener#enableChange(boolean, boolean)
			 */
			@Override
			public void enableChange(boolean oldEnable, boolean newEnable) {
				// TODO Auto-generated method stub
				
			}
			
			/*
			 * (non-Javadoc)
			 * @see ihm.actions.ActionOCRListener#doneChange(boolean, boolean)
			 */
			@Override
			public void doneChange(boolean oldDone, boolean newDone) {
				List<OCRTransaction> details = ((ActionOCRCalcul) ActionOcr.getAction(ActionOCRCalcul.class.getName())).getTransactionDetails();
				if(details==null) {
					return;
				}
				fireTranscationAvaible(details);
			}
		});
	}
	
	public static void addTransactionListener(TransactionAvaibleListener listener) {
		listeners.add(listener);
	}
	
	public static void removeTransactionListener(TransactionAvaibleListener listener) {
		listeners.remove(listener);
	}
	
	public static TransactionAvaibleListener[] getListeners() {
		return listeners.toArray(new TransactionAvaibleListener[0]);
	}
	
	private static void fireTranscationAvaible(List<OCRTransaction> details) {
		LOGGER.debug("transactions send");
		TransactionAvaibleListener[] tmp = getListeners();
		for(TransactionAvaibleListener listener : tmp) {
			listener.transactionAvaible(details);
		}
	}

	public void refreshOptions() {
		int loc = this.getMainSliderPane().getDividerLocation();
		loc++;
		this.getMainSliderPane().setDividerLocation(loc);
		loc--;
		this.getMainSliderPane().setDividerLocation(loc);
	}

}
