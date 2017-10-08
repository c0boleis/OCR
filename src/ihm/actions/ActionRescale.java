package ihm.actions;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ihm.PanelOCR;

public class ActionRescale extends ActionOcr implements MouseMotionListener,MouseListener{

	private static final Logger LOGGER = Logger.getLogger(ActionRescale.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4389402422581243423L;
	
	public static void DrawLine(Line2D.Float line,Graphics g) {
		g.drawLine(
				(int)line.getX1(), 
				(int)line.getY1(), 
				(int)line.getX2(), 
				(int)line.getY2());
	}
	
	private Line2D.Float  lineInf = null;

	private boolean lineInfDone = false;

	private Line2D.Float lineSup = null;

	private boolean lineSupDone = false;
	
	private JPanel panelOption;
	
	private List<PointGomme> points = new ArrayList<PointGomme>();

	public ActionRescale() {
		super("Rescale");
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setDone(false);
		setActive(!isActive());
	}

	private void createLineInf(int Mx,int My) {
		boolean debug = true;
		if(lineSup==null) {
			return;
		}
		if(debug) {
			System.out.println("Y2: "+lineSup.getY2()+"\tY1: "+lineSup.getY1());
			System.out.println("X2: "+lineSup.getX2()+"\tX1: "+lineSup.getX1());
		}
		double sizeYSup =  lineSup.getY2()-lineSup.getY1();
		double sizeXSup = lineSup.getX2()-lineSup.getX1();
		if(debug) {
			System.out.println("sizeYSup: "+sizeYSup+"\tsizeXSup: "+sizeXSup);
		}
		double d = lineSup.ptLineDist(Mx, My);
		double a = Math.atan2(sizeYSup,sizeXSup);
		double dx = 0.0;
		double dy = 0.0;
		double aDeg = a*180.0/Math.PI;
		if(debug) {
			System.out.println("angle a (deg): "+aDeg);
		}
		if(sizeXSup>0 && sizeYSup<0) {
			//cas 1
			dx = Math.cos((Math.PI/2)+a)*d;
			dy = Math.sin((Math.PI/2)+a)*d;
		}else if(sizeXSup>0 && sizeYSup>0) {
			//cas 2
			dx = Math.cos((Math.PI/2)+a)*d;
			dy = Math.sin((Math.PI/2)+a)*d;
		}else if(sizeXSup<0 && sizeYSup>0) {
			//cas 3
			dx = Math.cos((Math.PI/2)+a)*d;
			dy = Math.sin((Math.PI/2)+a)*d;
		}else if(sizeXSup<0 && sizeYSup<0) {
			//cas 4
			dx = Math.cos((Math.PI/2)+a)*d;
			dy = Math.sin((Math.PI/2)+a)*d;
		}else {
			dx = Math.cos((Math.PI/2)+a)*d;
			dy = Math.sin((Math.PI/2)+a)*d;
		}
		if(debug) {
			System.out.println("dx: "+dx+"\tdy: "+dy);
		}

		double x1 = lineSup.getX1()+dx;
		double y1 = lineSup.getY1()+dy;
		double x2 = lineSup.getX2()+dx;
		double y2 = lineSup.getY2()+dy;

		double d1 = lineSup.ptLineDist(x1, y1);
		double d2 = lineSup.ptLineDist(x2, y2);
		if(debug) {
			System.out.println("d: "+d+"\td1: "+d1+"\td2: "+d2);
		}
		lineInf = new Line2D.Float((float)x1, (float)y1, (float)x2, (float)y2);
		if(debug) {
			System.out.println("\n");
		}
	}

	/**
	 * @return the lineInf
	 */
	public Line2D.Float getLineInf() {
		return lineInf;
	}
	
	public Line2D.Float getLineLeft() {
		if(lineInf==null || lineInf==null) {
			return null;
		}
		return new Line2D.Float(lineSup.getP1(), lineInf.getP1());
	}

	public Line2D.Float getLineRight() {
		if(lineInf==null || lineInf==null) {
			return null;
		}
		return new Line2D.Float(lineSup.getP2(), lineInf.getP2());
	}

	/**
	 * @return the lineSup
	 */
	public Line2D.Float getLineSup() {
		return lineSup;
	}

	@Override
	public JPanel getPanelOption() {
		if(panelOption == null) {
			panelOption = new JPanel();
			//TODO
		}

		return panelOption;
	}
	
	/**
	 * 
	 * @return la liste des points gommés.
	 */
	public PointGomme[] getPoints() {
		return points.toArray(new PointGomme[0]);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x1 = e.getX();
		int y1 = e.getY();
		if(lineSup==null) {
			lineSup = new Line2D.Float(x1, y1, x1, y1);
			PanelOCR.get().getPanelImage().repaint();
			return;
		}else if(!lineSupDone){
			lineSupDone = true;
			lineInf = new Line2D.Float(lineSup.getP1(), lineSup.getP2());
			PanelOCR.get().getPanelImage().repaint();
			return;
		}else {
			createLineInf(e.getX(), e.getY());
			lineInfDone = true;
			//TODO fin de la définition des lignes. Début scale de l'image
			BufferedImage image = this.scaleImage(PanelOCR.getImage());
			PanelOCR.setImage(image);
			this.setActive(false);
			PanelOCR.get().getPanelImage().repaint();
			this.setDone(true);
//			BufferedImage imageR = this.rotateImage(image);
////			Windows.get().getPanelImage3().setImage(imageR);

		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public void mouseMoved(MouseEvent e) {
		if(lineSup==null) {
			return;
		}else if(lineSup!=null && !lineSupDone){
			int x1 = (int) lineSup.getX1();
			int y1 = (int) lineSup.getY1();
			int x2 = e.getX();
			int y2 = e.getY();
			lineSup = new Line2D.Float(x1, y1, x2, y2);
			PanelOCR.get().getPanelImage().repaint();
		}else if(lineInf!=null && !lineInfDone) {
			createLineInf(e.getX(), e.getY());
			PanelOCR.get().getPanelImage().repaint();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	private BufferedImage scaleImage(BufferedImage bufIn) {
		/*
		 * first scale
		 */
		double xMinRect = Math.min(lineSup.getX1(), lineSup.getX2());
		xMinRect = Math.min(xMinRect, lineInf.getX1());
		xMinRect = Math.min(xMinRect, lineInf.getX2());

		double xMaxRect = Math.max(lineSup.getX1(), lineSup.getX2());
		xMaxRect = Math.max(xMaxRect, lineInf.getX1());
		xMaxRect = Math.max(xMaxRect, lineInf.getX2());

		double yMinRect = Math.min(lineSup.getY1(), lineSup.getY2());
		yMinRect = Math.min(yMinRect, lineInf.getY1());
		yMinRect = Math.min(yMinRect, lineInf.getY2());

		double yMaxRect = Math.max(lineSup.getY1(), lineSup.getY2());
		yMaxRect = Math.max(yMaxRect, lineInf.getY1());
		yMaxRect = Math.max(yMaxRect, lineInf.getY2());

		double factZoom = PanelOCR.get().getPanelImage().getZoomFact();
//		int xMin = (int)(xMinRect/factZoom);
//		int xMax = (int)(xMaxRect/factZoom);
//		int yMin = (int)(yMinRect/factZoom);
//		int yMax = (int)(yMaxRect/factZoom);
		double dxLineSup = (lineSup.getX2()-lineSup.getX1())/factZoom;
		double dyLineSup = (lineSup.getY2()-lineSup.getY1())/factZoom;
		
		dxLineSup*=dxLineSup;
		dyLineSup*=dyLineSup;
		
		int widthScale = (int) Math.sqrt(dxLineSup+dyLineSup);
		int heightScale = (int)( lineSup.ptLineDist(lineInf.getP1())/factZoom);

		/*
		 * send buf for display
		 */

		/*
		 * rotation
		 */
		AffineTransform tx = new AffineTransform();
		double dx = this.lineSup.getX1()-this.lineSup.getX2();
		double dy = this.lineSup.getY1()-this.lineSup.getY2();
		double theta = Math.PI-Math.atan2(dy, dx);
		double thetaDeg =  theta*180/Math.PI;

		LOGGER.debug("thetha :"+thetaDeg);
		
		int xT = (int)(lineSup.getX1()/factZoom);
		int yT = (int)(lineSup.getY1()/factZoom);
		LOGGER.debug("xT: "+xT+"\tyT: "+yT);
		tx.rotate(theta, xT, yT);
		
		
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);


		BufferedImage bufRotate = op.filter(bufIn, null);
		
		LOGGER.debug("bufOutTest.width: "+bufRotate.getWidth()+"\tbufOutTest.height: "+bufRotate.getHeight());
		/*
		 * rescale image
		 */
		
		int xOriginRescale = xT;
		int yOriginRescale = yT;
		int widhtRescale = widthScale;
		int heightRescale = heightScale;
		LOGGER.debug("xT: "+xT+"\tyT: "+yT+"\twidth: "+widhtRescale+"\theight: "+heightRescale);
		
		BufferedImage bufResacled = bufRotate.getSubimage(xOriginRescale, yOriginRescale, widhtRescale, heightRescale);
		
		
//		File outputfile = new File("saved_rescale.png");
//		try {
//			ImageIO.write(bufResacled, "png", outputfile);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		
//		File outputfile2 = new File("saved_Rotate.png");
//		try {
//			ImageIO.write(bufRotate, "png", outputfile2);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		return bufResacled;
	} 
	
	/* (non-Javadoc)
	 * @see ihm.actions.ActionOcr#setActive(boolean)
	 */
	@Override
	public void setActive(boolean act) {
		super.setActive(act);
		if(act) {
			PanelOCR.get().getPanelImage().addMouseMotionListener(this);
			LOGGER.debug("ajout MouseMotionListener dans le PanelImage");
			PanelOCR.get().getPanelImage().addMouseListener(this);
			LOGGER.debug("ajout MouseListener dans le PanelImage");
		}else {
			PanelOCR.get().getPanelImage().removeMouseMotionListener(this);
			LOGGER.debug("supression MouseListener dans le PanelImage");
			PanelOCR.get().getPanelImage().removeMouseListener(this);
			LOGGER.debug("supression MouseMotionListener dans le PanelImage");
		}
	}

}
