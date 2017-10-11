package ihm.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.asprise.ocr.Ocr;

import ihm.PanelImage;
import ihm.PanelOCR;
import model.OCRTransaction;

public class ActionOCRCalcul extends ActionOcr{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5819487962019044672L;
	
	private static final Logger LOGGER = Logger.getLogger(ActionOCRCalcul.class);

	private JButton buttonCreateImage;

	private JPanel panelOption;

	private static final String LANGUAGE = "fra";
	
	private BufferedImage imageProcess = null;
	
	private static List<OCRTransaction> ocrTransactions;

	/**
	 * @return the buttonCreateImage
	 */
	private JButton getButtonCreateImage() {
		if(buttonCreateImage == null) {
			buttonCreateImage = new JButton("Initialyse Image");
			buttonCreateImage.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					PanelImage panel = PanelOCR.get().getPanelImage();
					imageProcess = new BufferedImage(
							panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
					panel.paint(imageProcess.getGraphics());
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
					if(imageProcess == null) {
						return;
					}
					try {
						ImageIO.write(imageProcess, "png", fileOutput );
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
					LOGGER.debug("OCR out: "+s);
					System.err.println(s);
					String text  = ActionOCRCalcul.processStringResult(s);
					LOGGER.debug("OCR process string out: "+text);
					preparTransaction(text);
					setDone(true);
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

	public List<OCRTransaction> getTransactionDetails(){
		if(!isDone()) {
			return null;
		}
		return ocrTransactions;
	}
	
	private static String processStringResult(String text) {
		char[] tab = text.toCharArray();
		String out = "";
		for(int index = 0;index<tab.length;index++) {
			char c = tab[index];
			switch (c) {
			case '\n':
				out+=c;
				continue;
			case '\t':
				out+=c;
				continue;
			case ',':
				out+=c;
				continue;
			case '.':
				out+=c;
				continue;
			default:
				if(Character.isAlphabetic(c)) {
					out+=c;
					continue;
				}
				if(Character.isDigit(c)) {
					out+=c;
					continue;
				}
				break;
			}
		}
		return out;
	}
	
	private static void preparTransaction(String text) {
		String[] info = text.split("\n");
		if(info.length % 2 != 0) {
			return;
		}
		int size = info.length/2;
		ocrTransactions = new ArrayList<OCRTransaction>();
		for(int index = 0;index<size;index++) {
			try {
				String title = info[index];
				double price = Double.parseDouble(info[size+index]);
				ocrTransactions.add(new OCRTransaction(title, price));
			}catch (NumberFormatException e) {
				return;
			}
		}
	}

}
