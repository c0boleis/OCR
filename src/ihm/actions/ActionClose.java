package ihm.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import ihm.MainFrame;

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
