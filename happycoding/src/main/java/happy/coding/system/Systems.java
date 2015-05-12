package happy.coding.system;

import happy.coding.io.Logs;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;

public class Systems {
	private static String desktopPath = null;

	public final static String FILE_SEPARATOR = System.getProperty("file.separator");
	public final static String USER_NAME = System.getProperty("user.name");
	public final static String USER_DIRECTORY = System.getProperty("user.home");
	public final static String WORKING_DIRECTORY = System.getProperty("user.dir");
	public final static String OPERATING_SYSTEM = System.getProperty("os.name");

	public enum OS {
		Windows, Linux, Mac
	}

	private static OS os = null;

	/**
	 * @return path to the desktop with a file separator in the end
	 */
	public static String getDesktop() {
		if (desktopPath == null)
			desktopPath = USER_DIRECTORY + FILE_SEPARATOR + "Desktop" + FILE_SEPARATOR;
		return desktopPath;
	}

	public static String getIP() {
		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip.getHostName() + "@" + ip.getHostAddress();
	}

	public static OS getOs() {
		if (os == null) {
			for (OS m : OS.values()) {
				if (OPERATING_SYSTEM.toLowerCase().contains(m.name().toLowerCase())) {
					os = m;
					break;
				}
			}
		}
		return os;
	}

	public static void pause() {
		try {
			Logs.debug("System paused, press [enter] to continue ...");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 
	public static void captureScreen() throws Exception {
		captureScreen("screenshot.png");
	}

	public static void captureScreen(String fileName) throws Exception {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);

		File file = new File(fileName);
		ImageIO.write(image, "png", file);

		Logs.debug("A screenshot is captured to: {}", file.getPath());
	}

}
