package ihm.ocr;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import ihm.PanelOCR;
import model.CharacterOCR;

public class PanelCharacterOCR extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5148309439438255466L;
	
	private static final Logger LOGGER = Logger.getLogger(PanelCharacterOCR.class);
	
	private CharacterOCR characterOCR;
	
	private boolean selected = false;
	
	public PanelCharacterOCR(CharacterOCR character) {
		this.characterOCR = character;
		int w = this.characterOCR.getImage().getWidth();
		int h = this.characterOCR.getImage().getHeight();
		updateZoom(PanelOCR.get().getPanelImage().getZoomFact());
		this.addMouseListener(this);
		LOGGER.debug("init w: "+w+"\th: "+h);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		double zoomFact = PanelOCR.get().getPanelImage().getZoomFact();
		BufferedImage image = this.characterOCR.getImage();
		int w = image.getWidth();
		int h = image.getHeight();
		w = (int) (w*zoomFact);
		h = (int) (h*zoomFact);

		
		int xImage = 0;
		int yImage = 0;

		g.drawImage(image, xImage, yImage,w,h, null);
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(xImage, yImage, w-1, h-1);
			
		}
	}

	/**
	 * @return the characterOCR
	 */
	public CharacterOCR getCharacterOCR() {
		return characterOCR;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.selected = !this.selected;
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBorder(new BevelBorder(BevelBorder.LOWERED));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setBorder(null);
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	
	protected void setSelected(boolean b) {
		this.selected = b;
	}

	public void updateZoom(double newZoom) {
		int w = (int) (this.characterOCR.getImage().getWidth()*newZoom);
		int h = (int) (this.characterOCR.getImage().getHeight()*newZoom);
		this.setSize(w,h);
	}

}
