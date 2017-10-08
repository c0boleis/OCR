package ihm.progression;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import ihm.actions.ActionOCRListener;
import ihm.actions.ActionOcr;

public class ButtonProgression extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9187615786446224473L;
	
	private JButton button;
	
	private ActionOcr actionOcr;
	
	private JPanel panelArrow;
	
	private ActionOCRListener actionOCRListener;
	
	private boolean end = false;
	
	public ButtonProgression(ActionOcr action) {
		this(action,false);
	}
	
	public ButtonProgression(ActionOcr action,boolean end) {
		this.actionOcr = action;
		this.end = end;
		this.setLayout(new BorderLayout());
		this.actionOcr.addActionListener(getActionOCRListener());
		this.add(getButton(), BorderLayout.CENTER);
		if(!this.end) {
			this.add(getPanelArrow(),BorderLayout.EAST);
		}
		
	}

	/**
	 * @return the button
	 */
	public JButton getButton() {
		if(button == null) {
			button = new JButton();
			button.setAction(this.actionOcr);
			button.setEnabled(this.actionOcr.isEnable());
		}
		return button;
	}

	/**
	 * @return the panelArrow
	 */
	public JPanel getPanelArrow() {
		if(panelArrow == null) {
			panelArrow = new JPanel() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 7945002550852235715L;

				@Override
				public void paint(Graphics g) {
					int w = this.getWidth();
					int s_2 = 2;
					g.setColor(Color.gray);
					if(actionOcr.isDone()) {
						g.setColor(Color.GREEN.brighter());
					}
					g.fillRect(0, (w/2)-s_2, w, s_2*2);
					
					g.fillRect(0, (w/2)-s_2, w, s_2*2);
				}
				
				
			};
			panelArrow.setPreferredSize(new Dimension(30, 20));
		}
		return panelArrow;
	}

	/**
	 * @return the actionOCRListener
	 */
	private ActionOCRListener getActionOCRListener() {
		if(actionOCRListener == null) {
			actionOCRListener = new ActionOCRListener() {
				
				@Override
				public void enableChange(boolean oldEnable, boolean newEnable) {
					getButton().setEnabled(newEnable);
					repaint();
				}
				
				@Override
				public void doneChange(boolean oldDone, boolean newDone) {
					if(newDone) {
						getButton().setBackground(Color.green);
					}
					repaint();
				}
				
			};
		}
		return actionOCRListener;
	}

	/**
	 * @return the end
	 */
	public boolean isEnd() {
		return end;
	}

}
