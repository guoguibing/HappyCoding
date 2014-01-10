package happy.coding.system;

import happy.coding.io.Logs;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Systems
{
	private static String		desktopPath			= null;

	public final static String	FILE_SEPARATOR		= System.getProperty("file.separator");
	public final static String	USER_NAME			= System.getProperty("user.name");
	public final static String	USER_DIRECTORY		= System.getProperty("user.home");
	public final static String	WORKING_DIRECTORY	= System.getProperty("user.dir");
	public final static String	OPERATING_SYSTEM	= System.getProperty("os.name");

	public enum OS {
		Windows, Linux, Unix
	}

	private static OS	os	= null;

	public static String getDesktop()
	{
		if (desktopPath == null) desktopPath = USER_DIRECTORY + FILE_SEPARATOR + "Desktop" + FILE_SEPARATOR;
		return desktopPath;
	}

	public static String getIP()
	{
		InetAddress ip = null;
		try
		{
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		return ip.getHostName() + "@" + ip.getHostAddress();
	}

	public static OS getOs()
	{
		if (os == null)
		{
			for (OS m : OS.values())
			{
				if (OPERATING_SYSTEM.toLowerCase().contains(m.name().toLowerCase()))
				{
					os = m;
					break;
				}
			}
		}
		return os;
	}

	public static void pause()
	{
		try
		{
			Logs.debug("System paused, waiting for inputs ...");
			System.in.read();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
