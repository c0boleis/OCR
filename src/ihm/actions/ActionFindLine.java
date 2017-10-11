package ihm.actions;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

public class ActionFindLine extends ActionOcr {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4389402422581243423L;
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ActionFindLine.class);
	
	private JPanel panelOption;

	public ActionFindLine() {
		super("Find line");
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

	@Override
	public JPanel getPanelOption() {
		if(panelOption == null) {
			panelOption = new JPanel();
			
		}

		return panelOption;
	}
	

}
