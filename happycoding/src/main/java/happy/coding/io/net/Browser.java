package happy.coding.io.net;

import happy.coding.system.Systems;

import org.junit.Test;

public class Browser
{
	private final static Runtime	rt	= Runtime.getRuntime();

	public static void startPages(String... urls) throws Exception
	{
		for (String url : urls)
		{
			switch (Systems.getOs())
			{
				case Windows:
					rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
					break;

				case Linux:
					String[] browsers = { "epiphany", "firefox", "mozilla", "konqueror", "netscape", "opera", "links",
							"lynx", "chrome" };

					// Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
					StringBuffer cmd = new StringBuffer();
					for (int i = 0; i < browsers.length; i++)
						cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + url + "\" ");

					rt.exec(new String[] { "sh", "-c", cmd.toString() });
					break;
				case Unix:
					rt.exec("open " + url);
					break;
			}
		}
	}

	@Test
	public void example() throws Exception
	{
		Browser.startPages(WebPages.favorites);
	}
}
