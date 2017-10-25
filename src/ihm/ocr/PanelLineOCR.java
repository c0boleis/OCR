package ihm.ocr;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ihm.PanelOCR;
import model.CharacterOCR;
import model.LineOCR;
import model.LineOCRListener;

public class PanelLineOCR extends JPanel implements LineOCRListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(PanelLineOCR.class);
	
	private LineOCR lineOCR;
	
	private List<PanelCharacterOCR> panels = new ArrayList<PanelCharacterOCR>();
	
	public PanelLineOCR(LineOCR line) {
		this.lineOCR = line;
		this.lineOCR.addLineOCRListener(this);
		init();
	}
	
	private void init() {
		CharacterOCR[] characterOCRs = this.lineOCR.getCharacters();
		this.setOpaque(false);
		this.setLayout(null);
		double zoomFact = PanelOCR.get().getPanelImage().getZoomFact();
		for(CharacterOCR character : characterOCRs) {
			PanelCharacterOCR panel = new PanelCharacterOCR(character);
			panel.setLocation((int) (character.getPosition()*zoomFact), 0);
			panels.add(panel);
			this.add(panel);
		}
		CharacterOCR characterOCR = characterOCRs[characterOCRs.length-1];
		int w = (int) ((characterOCR.getPosition()+characterOCR.getImage().getWidth())*zoomFact);
		int h = (int) (characterOCR.getImage().getHeight()*zoomFact);
		this.setSize(w, h);
		LOGGER.debug("init w: "+w+"\th: "+h);
	}

	/**
	 * @return the lineOCR
	 */
	public LineOCR getLineOCR() {
		return lineOCR;
	}
	
	public void updateZoom(double newZoom) {
		PanelCharacterOCR[] tmp = getPanelsCharacterOCR();
		CharacterOCR characterOCR = null;
		for(PanelCharacterOCR panelCharacterOCR : tmp) {
			panelCharacterOCR.setLocation((int)(panelCharacterOCR.getCharacterOCR().getPosition()*newZoom),0);
			panelCharacterOCR.updateZoom(newZoom);
			characterOCR = panelCharacterOCR.getCharacterOCR();
		}
		int w = (int) ((characterOCR.getPosition()+characterOCR.getImage().getWidth())*newZoom);
		int h = (int) (characterOCR.getImage().getHeight()*newZoom);
		this.setSize(w, h);
	}

	/**
	 * @return the panels
	 */
	public PanelCharacterOCR[] getPanelsCharacterOCR() {
		return panels.toArray(new PanelCharacterOCR[0]);
	}

	@Override
	public void characterOCRAdd(CharacterOCR characterOCR) {
		this.repaint();
	}

	@Override
	public void characterOCRemove(CharacterOCR characterOCR) {
		
		PanelCharacterOCR panel = getPanelCharacterOCR(characterOCR);
		this.remove(panel);
		this.panels.remove(panel);
		this.repaint();
	}
	
	private PanelCharacterOCR getPanelCharacterOCR(CharacterOCR characterOCR) {
		PanelCharacterOCR[] tmp = getPanelsCharacterOCR();
		for(PanelCharacterOCR panelCharacterOCR : tmp) {
			if(panelCharacterOCR.getCharacterOCR().equals(characterOCR)) {
				return panelCharacterOCR;
			}
		}
		return null;
	}

}
