package ihm.actions;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import ihm.PanelOCR;

public class ActionOpen extends ActionOcr{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5819487962019044672L;

	public ActionOpen() {
		super("Open");
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setDone(false);
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("images"));
		fileChooser.showOpenDialog(null);
		File file = fileChooser.getSelectedFile();
		if(file==null) {
			return;
		}
		System.out.println(file.getAbsolutePath());
		try {
			BufferedImage image = ImageIO.read(file);
			PanelOCR.setImage(image);
			PanelOCR.get().repaint();
			this.setDone(true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public JPanel getPanelOption() {
		// TODO Auto-generated method stub
		return null;
	}

}
