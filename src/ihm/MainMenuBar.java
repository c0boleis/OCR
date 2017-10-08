package ihm;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ihm.actions.ActionClose;
import ihm.actions.ActionGomme;
import ihm.actions.ActionGreyScale;
import ihm.actions.ActionOCRCalcul;
import ihm.actions.ActionOcr;
import ihm.actions.ActionOpen;
import ihm.actions.ActionSave;

public class MainMenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2507917243104224954L;
	
	private ActionClose actionClose;
	
	private ActionGreyScale actionGreyScale;
	
	private ActionOpen actionOpen;
	
	private ActionSave actionSave;
	
	private ActionGomme actionGomme;
	
	private ActionOCRCalcul actionOCRCalcul;
	
	private JMenu menuFile;
	
	private JMenu menuImage;
	
	private JMenuItem menuItemClose;
	
	private JMenuItem menuItemGreyScale;
	
	private JMenuItem menuItemOCR;
	
	private JMenuItem menuItemOpen;
	
	private JMenuItem menuItemSave;
	
	private JCheckBoxMenuItem menuItemGomme;
	
	/**
	 * @return the actionGomme
	 */
	private ActionGomme getActionGomme() {
		if(actionGomme == null) {
			actionGomme = (ActionGomme) ActionOcr.getAction(ActionGomme.class.getName());
		}
		return actionGomme;
	}

	/**
	 * @return the menuItemGomme
	 */
	private JCheckBoxMenuItem getMenuItemGomme() {
		if(menuItemGomme == null) {
			menuItemGomme = new JCheckBoxMenuItem();
			menuItemGomme.setAction(getActionGomme());
		}
		return menuItemGomme;
	}

	public MainMenuBar() {
		this.add(getMenuFile());
		this.add(getMenuImage());
	}

	/**
	 * @return the actionClose
	 */
	private ActionClose getActionClose() {
		if(actionClose == null) {
			actionClose = (ActionClose) ActionOcr.getAction(ActionClose.class.getName());
		}
		return actionClose;
	}

	/**
	 * @return the actionGreyScale
	 */
	private ActionGreyScale getActionGreyScale() {
		if(actionGreyScale == null) {
			actionGreyScale = (ActionGreyScale) ActionOcr.getAction(ActionGreyScale.class.getName());
		}
		return actionGreyScale;
	}

	/**
	 * @return the actionOpen
	 */
	private ActionOpen getActionOpen() {
		if(actionOpen == null) {
			actionOpen = (ActionOpen) ActionOcr.getAction(ActionOpen.class.getName());
		}
		return actionOpen;
	}
	
	/**
	 * @return the actionOCR
	 */
	private ActionOCRCalcul getActionOCR() {
		if(actionOCRCalcul == null) {
			actionOCRCalcul = (ActionOCRCalcul) ActionOcr.getAction(ActionOCRCalcul.class.getName());
		}
		return actionOCRCalcul;
	}

	/**
	 * @return the actionSave
	 */
	private ActionSave getActionSave() {
		if(actionSave == null) {
			actionSave = (ActionSave) ActionOcr.getAction(ActionSave.class.getName());
		}
		return actionSave;
	}

	/**
	 * @return the menuFile
	 */
	private JMenu getMenuFile() {
		if(menuFile == null) {
			menuFile = new JMenu();
			menuFile.setText("Fichier");
			menuFile.add(getMenuItemOpen());
			menuFile.add(getMenuItemSave());
			menuFile.add(getMenuItemClose());
		}
		return menuFile;
	}

	/**
	 * @return the menuImage
	 */
	private JMenu getMenuImage() {
		if(menuImage == null) {
			menuImage = new JMenu();
			menuImage.setText("Image");
			menuImage.add(getMenuItemGreyScale());
			menuImage.add(getMenuItemGomme());
			menuImage.add(getMenuItemOCR());
		}
		return menuImage;
	}

	/**
	 * @return the menuItemClose
	 */
	private JMenuItem getMenuItemClose() {
		if(menuItemClose == null) {
			menuItemClose = new JMenuItem();
			menuItemClose.setAction(getActionClose());
		}
		return menuItemClose;
	}

	/**
	 * @return the menuItemImage
	 */
	private JMenuItem getMenuItemGreyScale() {
		if(menuItemGreyScale == null) {
			menuItemGreyScale = new JMenuItem();
			menuItemGreyScale.setAction(getActionGreyScale());
		}
		return menuItemGreyScale;
	}

	/**
	 * @return the menuItemOpen
	 */
	private JMenuItem getMenuItemOpen() {
		if(menuItemOpen == null) {
			menuItemOpen = new JMenuItem();
//			menuItemOpen.setText("Open");
			menuItemOpen.setAction(getActionOpen());
		}
		return menuItemOpen;
	}

	/**
	 * @return the menuItemSave
	 */
	private JMenuItem getMenuItemSave() {
		if(menuItemSave == null) {
			menuItemSave = new JMenuItem();
//			menuItemSave.setText("Save");
			menuItemSave.setAction(getActionSave());
		}
		return menuItemSave;
	}

	/**
	 * @return the menuItemOCR
	 */
	private JMenuItem getMenuItemOCR() {
		if(menuItemOCR == null) {
			menuItemOCR = new JMenuItem();
			menuItemOCR.setAction(getActionOCR());
		}
		return menuItemOCR;
	}

}
