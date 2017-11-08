package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;

import ihm.util.ImageViewer;

public class TestChar {

	public static void main(String[] args) {
		JLabel label = new JLabel("T") {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2393240162955059543L;

			/* (non-Javadoc)
			 * @see javax.swing.JComponent#paint(java.awt.Graphics)
			 */
			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub
				super.paint(g);
				g.setColor(Color.RED);
//				g.drawRect(0, 0, getWidth()-1, getHeight()-1);
			}
			
			
		};
		label.setSize(300, 300);
		Font labelFont = label.getFont();
		String labelText = label.getText();

		int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = label.getWidth();
//		label

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = label.getHeight();

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
		
		labelFont = label.getFont();
		labelText = label.getText();
		stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
		
		JFrame frame = new JFrame();
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.add(label);
		label.setLocation(0, 0);
		label.setSize(stringWidth, fontSizeToUse);
		frame.setSize(400, 400);
		frame.setVisible(true);
//		panel.repaint();
		
		BufferedImage imageProcess = new BufferedImage(
				label.getWidth(), label.getHeight(), BufferedImage.TYPE_INT_ARGB);
		label.paint(imageProcess.getGraphics());
		ImageViewer v = new ImageViewer(imageProcess, "test");
		v.setVisible(true);
	}

}
