package happy.coding.io.net;

import happy.coding.io.FileIO;
import happy.coding.io.Logs;
import happy.coding.math.Randoms;
import happy.coding.system.Systems;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WebCrawler implements Runnable
{
	protected static Map<String, Integer>	proxy		= null;
	protected static List<String>			hosts		= null;
	protected static long					sleep		= 8000;
	
	/**
	 * set true if we want to use proxy sever instead of direct connection
	 */
	protected static boolean				use_proxy	= false;

	protected String						dir			= Systems.getDesktop();
	protected String						url;
	protected String						thread_id;

	public WebCrawler(String feed, int id) throws Exception
	{
		this.url = feed;
		this.thread_id = "[Thread " + id + "]";

		load_proxy();
	}

	public abstract void run_thread() throws Exception;

	@Override
	public void run()
	{
		Logs.debug(this.thread_id + " is started with link: " + url);

		try
		{
			run_thread();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		Logs.debug(this.thread_id + " is finished with link: " + url);

	}

	/**
	 * read the contents of a specific {@code url} every {@code sleep} millionseconds
	 * @return html contents
	 */
	protected String read_url(String url) throws Exception
	{
		if(use_proxy) return read_url_w_proxy(url);
		
		Thread.sleep(sleep);

		int max_trails = 200;
		int count = 0;
		while (true)
		{
			count++;

			try
			{
				String html = URLReader.read(url);

				// judge if page is completely or just partially read
				if (html.contains("<html") && !html.contains("</html>")) continue;
				else return html;
			} catch (IOException ce)
			{
				if (count > max_trails) return null;
				Logs.debug("Try again [times: " + count + "]: " + ce.getMessage());
			}
		}
	}

	private String read_url_w_proxy(String url) throws Exception
	{
		Thread.sleep(sleep);

		int max_trails = proxy.size();
		int index = Randoms.uniform(hosts.size());
		String host = hosts.get(index);
		int port = proxy.get(host);

		Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));

		int count = 0;
		while (true)
		{
			count++;

			try
			{
				String html = URLReader.read(url, p);

				// judge if page is completely or just partially read
				if (!html.contains("</html>")) continue;
				else return html;
			} catch (IOException ce)
			{
				if (count > max_trails) return null;
				Logs.debug("Connect to another proxy [times: " + count + "]: " + ce.getMessage());

				index = Randoms.uniform(hosts.size());
				host = hosts.get(index);
				port = proxy.get(host);

				p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
			}
		}
	}

	protected void load_proxy() throws Exception
	{
		proxy = new HashMap<>();

		String path = FileIO.getResource("proxy.txt");

		List<String> lines = FileIO.readAsList(path);
		for (String line : lines)
		{
			String[] data = line.split(":");
			String host = data[0];
			int port = Integer.parseInt(data[1]);

			proxy.put(host, port);
		}

		hosts = new ArrayList<>(proxy.keySet());
	}

}
