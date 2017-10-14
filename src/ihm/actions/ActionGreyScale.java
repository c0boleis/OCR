package ihm.actions;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import filter.SeuilBlackFilter;
import ihm.ImageUtil;
import ihm.PanelOCR;

public class ActionGreyScale extends ActionOcr{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4389402422581243423L;

	private JPanel panelOption;

	private JTextField textField;

	/**
	 * @return the textField
	 */
	private JTextField getTextField() {
		if(textField == null) {
			textField = new JTextField();
			textField.setText("155");
		}
		return textField;
	}

	/**
	 * @return the buttonStart
	 */
	private JButton getButtonStart() {
		if(buttonStart == null) {
			buttonStart = new JButton("Start");
			buttonStart.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					BufferedImage bufferedImage = getImageStart();
					int sInt = -1;
					try {
						sInt = Integer.parseInt(getTextField().getText().trim());
					}catch (NumberFormatException exp) {
						return;
					}
					if(sInt<1 || sInt>255) {
						return;
					}
					ImageFilter filter = new SeuilBlackFilter(sInt);  
					ImageProducer producer = new FilteredImageSource(bufferedImage.getSource(), filter);  
					Image image = Toolkit.getDefaultToolkit().createImage(producer);  
					setImageEndAction(ImageUtil.toBufferedImage(image));
					PanelOCR.setImage(getImageEndAction());
					setDone(true);
				}
			});
		}
		return buttonStart;
	}

	private JButton buttonStart;

	public ActionGreyScale() {
		super("grey scale");
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setDone(false);
		this.setActive(!isActive());
	}

	@Override
	public JPanel getPanelOption() {
		if(panelOption == null) {
			panelOption = new JPanel();
			panelOption.setLayout(new BorderLayout());
			panelOption.add(getTextField(), BorderLayout.CENTER);
			panelOption.add(getButtonStart(), BorderLayout.SOUTH);
		}
		return panelOption;
	}

}
