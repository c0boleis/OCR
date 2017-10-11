package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import ihm.actions.ActionGomme;
import ihm.actions.ActionOcr;
import ihm.actions.ActionRescale;
import ihm.actions.PointGomme;

public class PanelImage extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7835616410612450476L;

	private BufferedImage image;

	private MouseWheelListener mouseWheelListener;

	private double zoomFact = 1.0;
	
	public PanelImage() {
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
		this.zoomFact = 1.0;
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
//		int widthPanel = getWidth();
//		int heightPanel = getHeight();
		int xImage = 0;
		int yImage = 0;
//		if(w<widthPanel) {
//			xImage = (int) ((widthPanel-w)/2);
//		}
//		if(h<heightPanel) {
//			yImage = (int) ((heightPanel-h)/2);
//		}
		this.setPreferredSize(new Dimension((int)w, (int)h));
//		g.drawRect(xImage, yImage,(int)w, (int)h);
		g.drawImage(image, xImage, yImage,(int)w,(int)h, null);
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
		
		PointGomme[] points = actionGomme.getPoints();
		if(points.length<=0) {
			return;
		}
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

		if(actionGomme.isActive()) {
			g.setColor(Color.WHITE);
			Point p = actionGomme.getPosition();
			
			if(p!=null) {
				g.fillOval(p.x-ActionGomme.TAILLE_GOMME/2,
						p.y-ActionGomme.TAILLE_GOMME/2,
						ActionGomme.TAILLE_GOMME,
						ActionGomme.TAILLE_GOMME);
				g.setColor(Color.black);
				g.drawOval(p.x-ActionGomme.TAILLE_GOMME/2,
						p.y-ActionGomme.TAILLE_GOMME/2,
						ActionGomme.TAILLE_GOMME,
						ActionGomme.TAILLE_GOMME);
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
					zoomFact = zoomFact*fact;
					System.out.println("wheelWalue: "+wheelWalue+"\tzoomFact: "+zoomFact);
					repaint();
				}
			};
		}
		return mouseWheelListener;
	}

	public void zoomMore() {
		zoomFact = zoomFact*1.25;
		repaint();
	}
	
	public void zoomLess() {
		zoomFact = zoomFact*0.75;
		repaint();
	}

	public void resetZoom() {
		zoomFact =1.0;
		repaint();
	}


}
