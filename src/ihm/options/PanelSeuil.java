package ihm.options;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ihm.PanelOCR;
import ihm.actions.ActionGreyScale;
import ihm.actions.ActionOcr;
import ihm.util.NumberUtil;
import model.ColorSeuilFact;

public class PanelSeuil extends JPanel{

	private static final Logger LOGGER = Logger.getLogger(PanelSeuil.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -1181929149759401752L;

	private MouseListener mouseListener;

	private int[] tab;
	
	private ColorSeuilFact colorSeuilFact;

	private boolean vertical = false;
	
	private Color color;
	
	private int max = -1;

	public PanelSeuil(int[] s,Color c,ColorSeuilFact seuilFact) {
		this.tab = s;
		this.color = c;
		this.addMouseListener(getMouseListener());
		colorSeuilFact = seuilFact;
	}

	/**
	 * @return the mouseListener
	 */
	public MouseListener getMouseListener() {
		if(mouseListener == null) {
			mouseListener = new MouseListener() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					double val = 0;
					double h = getHeight();
					double w = getWidth();
					if(vertical) {
						val = h-e.getY();
						val = (val/h)*256.0;
					}else {
						val = e.getX();
						val = (val/w)*256.0;
					}
					double k = colorSeuilFact.getValMin()+ ((colorSeuilFact.getValMax() -colorSeuilFact.getValMin())/2.0);
					LOGGER.debug("limit: "+k+"\tmin: "+colorSeuilFact.getValMin()+"\tmax: "+colorSeuilFact.getValMax());
					if(val<=k) {
						setValMin((int) val);
						LOGGER.debug("modif val min");
					}
					if(val>k) {
						setValMax((int) val);
						LOGGER.debug("modif val max");
					}
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			};
		}
		return mouseListener;
	}
	
	

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#print(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		int w = getWidth();
		int h = getHeight();
//		LOGGER.debug("paint w: "+w+"\th: "+h);
		double[] cal = new double[256];
		double max1 = getMax();
		if(max1<0) {
			max1 = (double)NumberUtil.getMax(tab);
		}
		
		for(int index = 0;index<tab.length;index++) {
			double nbr = (double)tab[index];
			cal[index] = nbr/max1;
//			LOGGER.debug(index+": "+cal[index]);
		}
		g.setColor(this.color);
		if(vertical) {
			for(int index = h;index>=0;index--) {
				int indexCalc = ((h-index)*254)/h;
				double val = cal[indexCalc];
				int heightLIne =((int)(val*w));
				g.drawLine(0, index, heightLIne,index) ;
			}
			int min = (int) ((colorSeuilFact.getValMin()/255.0)*h);
			min = h-min;
			
			int max = (int) ((colorSeuilFact.getValMax()/255.0)*h);
			max = h-max;
			g.setColor(Color.MAGENTA.darker());
			g.drawLine(0, min, w, min);
			g.drawLine(0, max, w, max);
		}else {
			for(int index = 0;index<w;index++) {
				int indexCalc = (index*255)/w;
				double val = cal[indexCalc];
				int heightLIne = h-((int)(val*h));
				g.drawLine(index, heightLIne, index, h);
			}
			int min = (int) ((colorSeuilFact.getValMin()/255.0)*w);
			
			int max = (int) ((colorSeuilFact.getValMax()/255.0)*w);
			g.setColor(Color.MAGENTA.darker());
			g.drawLine(min,0, min,h);
			g.drawLine(max,0, max,h);
		}

	}

	/**
	 * @param valMax the valMax to set
	 */
	private void setValMax(double valMax) {
		colorSeuilFact.setValMax(valMax);
		PanelOCR.get().repaint();
		((ActionGreyScale)ActionOcr.getAction(ActionGreyScale.class.getName())).displaySeuil();
	}

	/**
	 * @param valMin the valMin to set
	 */
	private void setValMin(double valMin) {
		colorSeuilFact.setValMin(valMin);
		PanelOCR.get().repaint();
		((ActionGreyScale)ActionOcr.getAction(ActionGreyScale.class.getName())).displaySeuil();
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}


}
