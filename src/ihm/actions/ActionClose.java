package ihm.actions;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

public class ActionClose extends ActionOcr{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5819487962019044672L;

	public ActionClose() {
		super("Close");
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	@Override
	public JPanel getPanelOption() {
		// TODO Auto-generated method stub
		return null;
	}

}
