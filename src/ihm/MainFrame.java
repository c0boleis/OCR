package ihm;

import java.awt.BorderLayout;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;

import ihm.options.PanelOptions;


public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4135053340890300871L;
	
	private static final Logger LOGGER = Logger.getLogger(MainFrame.class);

	private MainMenuBar mainMenuBar;

	private PanelImage panelImage;
	
	private PanelOptions panelOptions;

	private JScrollPane scrollPane;

	private static final MainFrame INSTANCE = new MainFrame();

	public static void main(String[] args) {
		try {
			Properties logProperties = new Properties();
			InputStream read = MainFrame.class.getClassLoader().getResourceAsStream("ihm/log4j.properties");
			logProperties.load(read);
			PropertyConfigurator.configure(logProperties);
			((RollingFileAppender )Logger.getRootLogger().getAppender("R")).rollOver();
		} catch (Exception e) {
			System.err.println("Could not initialize logger");
		}
		LOGGER.info("Application start");
		
		MainFrame frame = MainFrame.get();
		frame.setVisible(true);
	}

	private MainFrame() {
		super("OCR");
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(getMainMenuBar());
		this.setLayout(new BorderLayout());
		this.add(getScrollPane(),BorderLayout.CENTER);
		this.add(getPanelOptions(), BorderLayout.WEST);
	}

	public static MainFrame get() {
		return INSTANCE;
	}

	/**
	 * @return the mainMenuBar
	 */
	private MainMenuBar getMainMenuBar() {
		if(mainMenuBar == null) {
			mainMenuBar = new MainMenuBar();
		}
		return mainMenuBar;
	}

	/**
	 * @return the panelImage
	 */
	public PanelImage getPanelImage() {
		if(panelImage == null) {
			panelImage = new PanelImage();
		}
		return panelImage;
	}

	/**
	 * @return the scrollPane
	 */
	private JScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getPanelImage());
			MouseWheelListener[] listeners = scrollPane.getMouseWheelListeners();
			for(MouseWheelListener listener : listeners) {
				scrollPane.removeMouseWheelListener(listener);
			}
		}
		return scrollPane;
	}

	/**
	 * 
	 * @return {@link BufferedImage} of the {@link PanelImage}
	 */
	public BufferedImage getImage() {
		return getPanelImage().getImage();
	}

	/**
	 * @return the panelOptions
	 */
	public PanelOptions getPanelOptions() {
		if(panelOptions == null) {
			panelOptions = new PanelOptions();
		}
		return panelOptions;
	}

}
