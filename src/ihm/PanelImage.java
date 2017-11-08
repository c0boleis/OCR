package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ihm.actions.ActionFindLine;
import ihm.actions.ActionGomme;
import ihm.actions.ActionOcr;
import ihm.actions.ActionRescale;
import ihm.actions.PointGomme;

public class PanelImage extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7835616410612450476L;
	
	private static final Logger LOGGER = Logger.getLogger(PanelImage.class);

	private BufferedImage image;

	private MouseWheelListener mouseWheelListener;
	
	private List<PanelImageListener> listeners = new ArrayList<PanelImageListener>();

	private double zoomFact = 1.0;
	
	public PanelImage() {
		super();
		this.setLayout(null);
		this.addMouseWheelListener(getMouseWheelListener());
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	public double getZoomFact() {
		return zoomFact;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
//		this.zoomFact = 1.0;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(image == null) {
			return;
		}

		double w = image.getWidth();
		double h = image.getHeight();
		w = w*zoomFact;
		h = h*zoomFact;

		
		int xImage = 0;
		int yImage = 0;
		this.setPreferredSize(new Dimension((int)w, (int)h));
		g.drawImage(image, xImage, yImage,(int)w,(int)h, null);
		
		ActionFindLine actionFindLine =(ActionFindLine) ActionOcr.getAction(ActionFindLine.class.getName());
		if(actionFindLine.isActive()) {
//			LOGGER.debug("ActionFindLine find line is active, paint Character");
			return;
		}
		
		

		
		ActionGomme actionGomme  =(ActionGomme) ActionOcr.getAction(ActionGomme.class.getName());

		ActionRescale act = (ActionRescale) ActionOcr.getAction(ActionRescale.class.getName());
		if(act.isActive()) {
			g.setColor(Color.BLUE.darker());
			if(act.getLineSup()!=null) {
				ActionRescale.DrawLine(act.getLineSup(), g);
			}
			if(act.getLineInf()!=null) {
				ActionRescale.DrawLine(act.getLineInf(), g);
				
				g.setColor(Color.RED);
				ActionRescale.DrawLine(act.getLineRight(), g);
				g.setColor(Color.green.darker());
				ActionRescale.DrawLine(act.getLineLeft(), g);
			}
		}
		


		/*
		 * affichage de la gomme
		 */
		if(actionGomme.isActive()) {
			PointGomme[] points = actionGomme.getPoints();
			int wG = (int) (50*zoomFact);
			int hG = (int) (50*zoomFact);
			int xG = 0;
			int yG = 0;
			g.setColor(Color.WHITE);
			for(PointGomme point : points) {
				wG = (int) (point.getTaille()*zoomFact);
				hG = (int) (point.getTaille()*zoomFact);
				xG = (int) ((point.x*zoomFact)-wG/2);
				yG = (int) ((point.y*zoomFact)-hG/2);
				g.fillOval(xG, yG, wG, hG);
			}
			Point p = actionGomme.getPosition();
			int tailleGomme = (int) (ActionGomme.TAILLE_GOMME*zoomFact);
			if(p!=null) {
				g.fillOval(p.x-tailleGomme/2,
						p.y-tailleGomme/2,
						tailleGomme,
						tailleGomme);
				g.setColor(Color.black);
				g.drawOval(p.x-tailleGomme/2,
						p.y-tailleGomme/2,
						tailleGomme,
						tailleGomme);
			}
			
		}
		/*
		 * affichage des ligne OCR
		 */
//		ActionFindLine actionFindLine = (ActionFindLine) ActionOcr.getAction(ActionFindLine.class.getName());
		if(actionFindLine.isDone()) {
			g.setColor(Color.blue.darker());
			Rectangle[] listRect = actionFindLine.getRectangleLine();
			for(Rectangle rect : listRect) {
				LOGGER.debug("rect : p(x:"+rect.x+",\ty:"+rect.y+",\tw:"+rect.width+"\th:"+rect.height+")");
				int xRect = (int) (rect.x*zoomFact);
				int yRect = (int) (rect.y*zoomFact);
				int wRect = (int) (rect.width*zoomFact);
				int hRect = (int) (rect.height*zoomFact);
				g.fillRect(xRect, yRect, wRect, hRect);
			}
			listRect = actionFindLine.getRectangleChar();
			g.setColor(Color.green.darker());
			for(Rectangle rect : listRect) {
				LOGGER.debug("rect : p(x:"+rect.x+",\ty:"+rect.y+",\tw:"+rect.width+"\th:"+rect.height+")");
				int xRect = (int) (rect.x*zoomFact);
				int yRect = (int) (rect.y*zoomFact);
				int wRect = (int) (rect.width*zoomFact);
				int hRect = (int) (rect.height*zoomFact);
				g.fillRect(xRect, yRect, wRect, hRect);
			}
		}

	}

	/**
	 * @return the mouseWheelListener
	 */
	private MouseWheelListener getMouseWheelListener() {
		if(mouseWheelListener == null) {
			mouseWheelListener = new MouseWheelListener() {

				/*
				 * (non-Javadoc)
				 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.MouseWheelEvent)
				 */
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
					int wheelWalue = e.getWheelRotation();
					double fact = 0.75;
					if(wheelWalue>0) {
						fact = 1.25;
					}
					double oldZoom =  zoomFact;
					zoomFact = zoomFact*fact;
					fireZoomChange(oldZoom, zoomFact);
					//System.out.println("wheelWalue: "+wheelWalue+"\tzoomFact: "+zoomFact);
					repaint();
				}
			};
		}
		return mouseWheelListener;
	}

	public void zoomMore() {
		double oldZoom = zoomFact;
		zoomFact = zoomFact*1.25;
		fireZoomChange(oldZoom, zoomFact);
		repaint();
	}
	
	public void zoomLess() {
		double oldZoom = zoomFact;
		zoomFact = zoomFact*0.75;
		fireZoomChange(oldZoom, zoomFact);
		repaint();
	}

	public void resetZoom() {
		double oldZoom = zoomFact;
		zoomFact =1.0;
		fireZoomChange(oldZoom, zoomFact);
		repaint();
	}
	
	public boolean addPanelImageListener(PanelImageListener listener) {
		if(!this.listeners.contains(listener)) {
			this.listeners.add(listener);
			return true;
		}
		return false;
	}
	
	public boolean removePanelImageListener(PanelImageListener listener) {
		return this.listeners.add(listener);
	}
	
	public PanelImageListener[] getPanelImageListener() {
		return this.listeners.toArray(new PanelImageListener[0]);
	}
	
	protected void fireZoomChange(double oldZoom,double newZoom) {
		PanelImageListener[] tmp = getPanelImageListener();
		for(PanelImageListener panelImageListener : tmp) {
			panelImageListener.zoomFactChange(oldZoom, newZoom);
		}
	}


}
