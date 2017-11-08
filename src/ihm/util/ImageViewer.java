package ihm.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageViewer extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2551440911840209189L;
	
	public ImageViewer(BufferedImage image,String titre) {
		super(titre);
		this.setTitle(titre);
		this.setSize(400,400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				if(image == null) {
					return;
				}

				double w = image.getWidth();
				double h = image.getHeight();

				
				int xImage = 0;
				int yImage = 0;
				//this.setPreferredSize(new Dimension((int)w, (int)h));
				g.drawImage(image, xImage, yImage,(int)w,(int)h, null);
			}
		};
		
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
	}

}
