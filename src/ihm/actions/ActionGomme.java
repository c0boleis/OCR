package ihm.actions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import ihm.PanelOCR;

public class ActionGomme extends ActionOcr implements MouseMotionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4389402422581243423L;
	
	private static final Logger LOGGER = Logger.getLogger(ActionGomme.class);
	
	private List<PointGomme> points = new ArrayList<PointGomme>();
	
	public static int TAILLE_GOMME = 60;
	
	private JPanel panelOption;
	
	private JButton buttonSaveGomme;
	
	private JSlider slider;
	
	private Point position;

	/**
	 * @return the buttonSaveGomme
	 */
	private JButton getButtonSaveGomme() {
		if(buttonSaveGomme == null) {
			buttonSaveGomme = new JButton("Save");
			buttonSaveGomme.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					saveImage();
				}
			});
		}
		return buttonSaveGomme;
	}

	public ActionGomme() {
		super("Gomme");
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setDone(true);
		setActive(!isActive());
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		this.setPosition(e.getPoint());
		LOGGER.debug("Gomme.mouseDragged x: "+e.getX()+"\ty: "+e.getY());
		double z = PanelOCR.get().getPanelImage().getZoomFact();
		int x = (int) (e.getX()/z);
		int y = (int) (e.getY()/z);
		points.add(new PointGomme(x, y,ActionGomme.TAILLE_GOMME));
		PanelOCR.get().getPanelImage().repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.setPosition(e.getPoint());
		PanelOCR.get().getPanelImage().repaint();
	}

	/* (non-Javadoc)
	 * @see ihm.actions.ActionOcr#setActive(boolean)
	 */
	@Override
	public void setActive(boolean act) {
		super.setActive(act);
		if(act) {
			points.clear();
			PanelOCR.get().getPanelImage().addMouseMotionListener(this);
			LOGGER.debug("ajout MouseMotionListener dans le PanelImage");
		}else {
			PanelOCR.get().getPanelImage().removeMouseMotionListener(this);
			LOGGER.debug("supression MouseMotionListener dans le PanelImage");
			saveImage();
		}
	}
	
	/**
	 * 
	 * @return la liste des points gomm�s.
	 */
	public PointGomme[] getPoints() {
		return points.toArray(new PointGomme[0]);
	}

	@Override
	public JPanel getPanelOption() {
		if(panelOption == null) {
			panelOption = new JPanel();
			panelOption.setLayout(new BorderLayout());
			panelOption.add(getSlider(), BorderLayout.CENTER);
			panelOption.add(getButtonSaveGomme(), BorderLayout.SOUTH);
		}

		return panelOption;
	}

	/**
	 * @return the slider
	 */
	private JSlider getSlider() {
		if(slider == null) {
			slider = new JSlider(JSlider.VERTICAL);
			slider.setMinimum(10);
			slider.setMaximum(500);
			slider.setValue(ActionGomme.TAILLE_GOMME);
			slider.addChangeListener(new ChangeListener() {
				
				/*
				 * (non-Javadoc)
				 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
				 */
				@Override
				public void stateChanged(ChangeEvent e) {
					ActionGomme.TAILLE_GOMME = slider.getValue();
				}
			});
		}
		return slider;
	}

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	private void setPosition(Point position) {
		this.position = position;
	}
	
	private void saveImage() {
		if(points.isEmpty()) {
			return;
		}
		BufferedImage imageS = getImageStart();
		BufferedImage imageProcess = new BufferedImage(
				imageS.getWidth(), imageS.getHeight(), BufferedImage.TYPE_INT_ARGB);
		imageProcess.getGraphics().drawImage(imageS, 0, 0,imageS.getWidth(), imageS.getHeight(), null);
		imageProcess.getGraphics().setColor(Color.WHITE);
		int wG = 0;
		int hG = 0;
		int xG = 0;
		int yG = 0;
		for(PointGomme point : points) {
			wG = point.getTaille();
			hG = point.getTaille();
			xG = point.x-wG/2;
			yG = (point.y)-hG/2;
			imageProcess.getGraphics().fillOval(xG, yG, wG, hG);
		}
		File fileOutput = new File("tmp_gomme.png");
		try {
			ImageIO.write(imageProcess, "png", fileOutput );
		}catch (IOException e) {
			// TODO: handle exception
		}
			
		this.setImageEndAction(imageProcess);
	}
	

}
