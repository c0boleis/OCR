package ihm.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import ihm.PanelOCR;
import ihm.actions.ActionAllOCRListener;
import ihm.actions.ActionOcr;

public class PanelOptions extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5459710990075751869L;
	
	private ActionAllOCRListener actionListener;
	
	private JPanel panelNorth;
	
	private JButton buttonHide;
	
	private JPanel panelZoom;
	
	
	private JButton buttonZoomMore;
	
	private JButton buttonResetZoom;
	
	/**
	 * @return the panelZoom
	 */
	private JPanel getPanelZoom() {
		if(panelZoom == null) {
			panelZoom = new JPanel();
//			panelZoom.setPreferredSize(new Dimension(160, 25));
			panelZoom.setLayout(new BorderLayout());
			panelZoom.add(getButtonZoomLess(),BorderLayout.WEST);
			panelZoom.add(getButtonResetZoom(),BorderLayout.CENTER);
			panelZoom.add(getButtonZoomMore(),BorderLayout.EAST);
		}
		return panelZoom;
	}

	/**
	 * @return the buttonZoomMore
	 */
	private JButton getButtonZoomMore() {
		if(buttonZoomMore == null) {
			buttonZoomMore = new JButton("+");
			buttonZoomMore.addActionListener(new ActionListener() {
				
				/*
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					PanelOCR.get().getPanelImage().zoomMore();
				}
			});
		}
		return buttonZoomMore;
	}

	/**
	 * @return the buttonZoomLess
	 */
	private JButton getButtonZoomLess() {
		if(buttonZoomLess == null) {
			buttonZoomLess = new JButton("-");
			buttonZoomLess.addActionListener(new ActionListener() {
				
				/*
				 * (non-Javadoc)
				 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					PanelOCR.get().getPanelImage().zoomLess();
				}
			});
		}
		return buttonZoomLess;
	}

	private JButton buttonZoomLess;

	/**
	 * @return the panelNorth
	 */
	private JPanel getPanelNorth() {
		if(panelNorth == null) {
			panelNorth = new JPanel();
			panelNorth.setLayout(new BorderLayout());
			panelNorth.add(getLabelTitle(), BorderLayout.CENTER);
			panelNorth.add(getButtonHide(), BorderLayout.EAST);
			panelNorth.add(new JSeparator(), BorderLayout.SOUTH);
		}
		return panelNorth;
	}

	/**
	 * @return the labelTitle
	 */
	private JLabel getLabelTitle() {
		if(labelTitle == null) {
			labelTitle = new JLabel("Options");
		}
		return labelTitle;
	}

	private JLabel labelTitle;
	
	public PanelOptions() {
		this.setLayout(new BorderLayout());
		init();
		this.setPreferredSize(new Dimension(200, 500));
		ActionOcr.addActionAllOCRListener(getActionAllOCRListener());
	}
	
	private void init() {
		this.removeAll();
		this.setLayout(new BorderLayout());
		this.add(getPanelNorth(), BorderLayout.NORTH);
		this.add(getPanelZoom(),BorderLayout.SOUTH);
	}

	/**
	 * @return the actionListener
	 */
	public ActionAllOCRListener getActionAllOCRListener() {
		if(actionListener== null) {
			actionListener = new ActionAllOCRListener() {
				
				@Override
				public void actionChange(ActionOcr actionOcr) {
					JPanel panel = actionOcr.getPanelOption();
					init();
					if(panel != null) {
						add(panel, BorderLayout.CENTER);
//						panel.doLayout();
//						panel.repaint();
					}
					PanelOCR.get().refreshOptions();
				}
			};
		}
		return actionListener;
	}

	/**
	 * @return the buttonHide
	 */
	private JButton getButtonHide() {
		if(buttonHide == null) {
			buttonHide = new JButton("<");
		}
		return buttonHide;
	}

	/**
	 * @return the buttonResetZoom
	 */
	private JButton getButtonResetZoom() {
		if(buttonResetZoom == null) {
			buttonResetZoom = new JButton("R");
			buttonResetZoom.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					PanelOCR.get().getPanelImage().resetZoom();
				}
			});
		}
		return buttonResetZoom;
	}
}
