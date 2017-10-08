package ihm.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.asprise.ocr.Ocr;

import ihm.PanelOCR;

public class ActionOCRCalcul extends ActionOcr{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5819487962019044672L;

	private JButton buttonCreateImage;

	private JPanel panelOption;
	
	private static final String LANGUAGE = "fra";

	/**
	 * @return the buttonCreateImage
	 */
	private JButton getButtonCreateImage() {
		if(buttonCreateImage == null) {
			buttonCreateImage = new JButton("Initialyse Image");
			buttonCreateImage.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}
		return buttonCreateImage;
	}

	/**
	 * @return the buttonStartOCR
	 */
	private JButton getButtonStartOCR() {
		if(buttonStartOCR == null) {
			buttonStartOCR = new JButton("Start OCR");
			buttonStartOCR.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					File fileOutput = new File("tmp.png");
					BufferedImage image = PanelOCR.getImage();
					try {
						ImageIO.write(image, "png", fileOutput );
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Ocr ocr = new Ocr();
					ocr.startEngine(LANGUAGE, Ocr.SPEED_FASTEST, "");
					String files = fileOutput.getAbsolutePath();
					String recognizeType = Ocr.RECOGNIZE_TYPE_TEXT;
					String outputFormat = Ocr.OUTPUT_FORMAT_PLAINTEXT;
					Object allRecogProps = "PROP_OUTPUT_SEPARATE_WORDS=false|PROP_PAGE_TYPE=auto|PROP_PDF_OUTPUT_TEXT_VISIBLE=true|PROP_IMG_PREPROCESS_TYPE=default|PROP_TABLE_SKIP_DETECTION=false";
					String s = ocr.recognize(files, Ocr.PAGES_ALL, -1, -1, -1, -1, recognizeType, outputFormat, allRecogProps);
					System.err.println(s);
					//					File outputFile = new File("asprise-ocr-" + new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss_SSS").format(new Date()) + ".txt");
				}
			});
		}
		return buttonStartOCR;
	}

	private JButton buttonStartOCR;

	public ActionOCRCalcul() {
		super("OCR");
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.setActive(true);
		
	}

	@Override
	public JPanel getPanelOption() {
		if(panelOption== null) {
			panelOption = new JPanel();
			panelOption.setLayout(new BoxLayout(panelOption, BoxLayout.Y_AXIS));
			panelOption.add(getButtonCreateImage());
			panelOption.add(getButtonStartOCR());
		}
		return panelOption;
	}

}
