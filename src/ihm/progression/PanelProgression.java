package ihm.progression;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import ihm.actions.ActionFindLine;
import ihm.actions.ActionGomme;
import ihm.actions.ActionGreyScale;
import ihm.actions.ActionOCRCalcul;
import ihm.actions.ActionOcr;
import ihm.actions.ActionOpen;
import ihm.actions.ActionRescale;

public class PanelProgression extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2690957718767165496L;
	
	private ButtonProgression buttonOpenImage;
	
	private ButtonProgression buttonRescaleImage;
	
	private ButtonProgression buttonGreyScale;
	
	private ButtonProgression buttonGomme;
	
	private ButtonProgression buttonFindLine;
	
	private ButtonProgression buttonOcr;
	
	public PanelProgression() {
		super();
		init();
	}
	
	private void init() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(getButtonOpenImage());
		this.add(getButtonRescaleImage());
		this.add(getButtonGreyScale());
		this.add(getButtonGomme());
		this.add(getButtonFindLine());
		this.add(getButtonOcr());
	}

	/**
	 * @return the buttonOpenImage
	 */
	private ButtonProgression getButtonOpenImage() {
		if(buttonOpenImage == null) {
			ActionOcr actionOcr = ActionOcr.getAction(ActionOpen.class.getName());
			actionOcr.setEnable(true);
			buttonOpenImage = new ButtonProgression(actionOcr);
			buttonOpenImage.setEnabled(actionOcr.isEnable());
		}
		return buttonOpenImage;
	}

	/**
	 * @return the buttonRescaleImage
	 */
	private ButtonProgression getButtonRescaleImage() {
		if(buttonRescaleImage == null) {
			ActionOcr actionOcr = ActionOcr.getAction(ActionRescale.class.getName());
			buttonRescaleImage = new ButtonProgression(actionOcr);
			buttonRescaleImage.setEnabled(actionOcr.isEnable());
		}
		return buttonRescaleImage;
	}

	/**
	 * @return the buttonGreyScale
	 */
	private ButtonProgression getButtonGreyScale() {
		if(buttonGreyScale == null) {
			ActionOcr actionOcr = ActionOcr.getAction(ActionGreyScale.class.getName());
			buttonGreyScale = new ButtonProgression(actionOcr);
			buttonGreyScale.setEnabled(actionOcr.isEnable());
		}
		return buttonGreyScale;
	}

	/**
	 * @return the buttonGomme
	 */
	private ButtonProgression getButtonGomme() {
		if(buttonGomme == null) {
			ActionOcr actionOcr = ActionOcr.getAction(ActionGomme.class.getName());
			buttonGomme = new ButtonProgression(actionOcr);
			buttonGomme.setEnabled(actionOcr.isEnable());
		}
		return buttonGomme;
	}

	/**
	 * @return the buttonOcr
	 */
	private ButtonProgression getButtonOcr() {
		if( buttonOcr == null) {
			ActionOcr actionOcr = ActionOcr.getAction(ActionOCRCalcul.class.getName());
			buttonOcr = new ButtonProgression(actionOcr,true);
			buttonOcr.setEnabled(actionOcr.isEnable());
		}
		return buttonOcr;
	}

	private ButtonProgression getButtonFindLine() {
		if( buttonFindLine == null) {
			ActionOcr actionOcr = ActionOcr.getAction(ActionFindLine.class.getName());
			buttonFindLine = new ButtonProgression(actionOcr);
			buttonFindLine.setEnabled(actionOcr.isEnable());
		}
		return buttonFindLine;
	}

}
