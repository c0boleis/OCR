package ihm.ocr;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ihm.PanelImageListener;
import ihm.PanelOCR;
import model.CharacterOCR;
import model.LineOCR;

public class PanelFindLine extends JPanel implements PanelImageListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4103707265863639641L;

	private static final Logger LOGGER = Logger.getLogger(PanelFindLine.class);

	private List<PanelLineOCR> panelLines = new ArrayList<PanelLineOCR>();

	private List<PanelOCRListener> panelOCRListeners = new ArrayList<PanelOCRListener>();

	public PanelFindLine(LineOCR[] lines) {
		super();
		init(lines);
	}

	public PanelFindLine() {
		super();
	}

	public void init(LineOCR[] lines) {
		this.setOpaque(false);
		this.setLayout(null);
		double zoomFact = PanelOCR.get().getPanelImage().getZoomFact();
		for(LineOCR line : lines) {
			PanelLineOCR panel = new PanelLineOCR(line,this);
			panel.setLocation(0, (int) (line.getPosition()*zoomFact));
			panelLines.add(panel);
			this.add(panel);
		}
		LineOCR line = lines[lines.length-1]; 
		int w = (int) (panelLines.get(panelLines.size()-1).getWidth()*zoomFact);
		int h = (int) ((line.getPosition()+line.getImage().getHeight())*zoomFact);
		this.setSize(w, h);
		LOGGER.debug("init w: "+w+"\th: "+h);
		PanelOCR.get().getPanelImage().addPanelImageListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//		double zoomFact = PanelOCR.get().getPanelImage().getZoomFact();
		//		BufferedImage image = this.characterOCR.getImage();
		int w = getWidth();
		int h = getHeight();
		//		w = (int) (w*zoomFact);
		//		h = (int) (h*zoomFact);


		int xImage = 0;
		int yImage = 0;

		g.setColor(Color.green);
		g.drawRect(xImage, yImage, w-1, h-1);

	}

	/*
	 * (non-Javadoc)
	 * @see ihm.PanelImageListener#zoomFactChange(double, double)
	 */
	@Override
	public void zoomFactChange(double oldZoom, double newZoom) {
		PanelLineOCR[] tmp = getPanelLines();
		LineOCR line = null;
		for(PanelLineOCR panel : tmp) {
			panel.setLocation(0, (int) (panel.getLineOCR().getPosition()*newZoom));
			panel.updateZoom(newZoom);
			line = panel.getLineOCR();
		}
		int w = (int) (panelLines.get(panelLines.size()-1).getWidth());
		int h = (int) ((line.getPosition()+line.getImage().getHeight())*newZoom);
		this.setSize(w, h);
		this.repaint();
		//		this.setPreferredSize(new Dimension(w, h));
	}

	public PanelLineOCR[] getPanelLines() {
		return this.panelLines.toArray(new PanelLineOCR[0]);
	}

	public CharacterOCR[] getSelectedCharacter() {
		List<CharacterOCR> selectedCharOCRs = new ArrayList<CharacterOCR>();
		for(PanelLineOCR panel : this.panelLines) {
			PanelCharacterOCR[] charOcrs = panel.getPanelsCharacterOCR();
			for(PanelCharacterOCR charOcr : charOcrs) {
				if(charOcr.isSelected()) {
					selectedCharOCRs.add(charOcr.getCharacterOCR());
				}
			}
		}
		return selectedCharOCRs.toArray(new CharacterOCR[0]);
	}

	public CharacterOCR[] getAllCharacter() {
		List<CharacterOCR> selectedCharOCRs = new ArrayList<CharacterOCR>();
		for(PanelLineOCR panel : this.panelLines) {
			PanelCharacterOCR[] charOcrs = panel.getPanelsCharacterOCR();
			for(PanelCharacterOCR charOcr : charOcrs) {
				selectedCharOCRs.add(charOcr.getCharacterOCR());
			}
		}
		return selectedCharOCRs.toArray(new CharacterOCR[0]);
	}

	public void unSelectAll() {
		for(PanelLineOCR panel : this.panelLines) {
			PanelCharacterOCR[] charOcrs = panel.getPanelsCharacterOCR();
			for(PanelCharacterOCR charOcr : charOcrs) {
				charOcr.setSelected(false);
			}
		}
		this.repaint();
	}

	public PanelOCRListener[] getListeners() {
		return this.panelOCRListeners.toArray(new PanelOCRListener[0]);
	}

	public void addPanelOCRListener(PanelOCRListener listener) {
		this.panelOCRListeners.add(listener);
	}

	public boolean removePanelOCRListener(PanelOCRListener listener) {
		return this.panelOCRListeners.remove(listener);
	}

	protected void fireCharacterOCRSelected(PanelCharacterOCR panelCharacterOCR) {
		PanelOCRListener[] listeners = getListeners();
		for(PanelOCRListener listener : listeners) {
			listener.characterOCRSelected(panelCharacterOCR);
		}
	}

	protected void fireCharacterOCRUnSelected(PanelCharacterOCR panelCharacterOCR) {
		PanelOCRListener[] listeners = getListeners();
		for(PanelOCRListener listener : listeners) {
			listener.characterOCRUnSelected(panelCharacterOCR);
		}
	}


}
