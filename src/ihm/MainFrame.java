package ihm;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.RollingFileAppender;


public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4135053340890300871L;
	
	private static final Logger LOGGER = Logger.getLogger(MainFrame.class);

	private MainMenuBar mainMenuBar;

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
		
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	public MainFrame() {
		super("OCR");
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(getMainMenuBar());
		this.setLayout(new BorderLayout());
		
		this.add(PanelOCR.get(), BorderLayout.CENTER);
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

	


}
