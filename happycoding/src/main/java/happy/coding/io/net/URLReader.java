package happy.coding.io.net;

import happy.coding.io.FileIO;
import happy.coding.system.Systems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class URLReader
{
	public static String read(String url) throws Exception
	{
		URL link = new URL(url);
		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new InputStreamReader(link.openStream()));
		String line = null;
		while ((line = br.readLine()) != null)
		{
			sb.append(line);
			sb.append("\r\n");
		}

		br.close();

		return sb.toString();
	}

	public static String read(String url, String proxyHost, int proxyPort) throws Exception
	{
		SocketAddress addr = new InetSocketAddress(proxyHost, proxyPort);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);

		URL link = new URL(url);
		URLConnection conn = link.openConnection(proxy);
		conn.setConnectTimeout(10 * 1000);

		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = null;
		while ((line = br.readLine()) != null)
		{
			sb.append(line);
			sb.append("\r\n");
		}

		br.close();

		return sb.toString();
	}
	
	public static String read(String url, Proxy proxy) throws Exception
	{
		URL link = new URL(url);
		URLConnection conn = link.openConnection(proxy);
		conn.setConnectTimeout(10 * 1000);
		
		StringBuilder sb = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = null;
		while ((line = br.readLine()) != null)
		{
			sb.append(line);
			sb.append("\r\n");
		}
		
		br.close();
		
		return sb.toString();
	}

	@Test
	public void usage() throws Exception
	{
		String url = "http://www.80stees.com/products/DC_Comics_Superman_Classic_t-shirt.asp";
		String html = URLReader.read(url);
		String filePath = Systems.getDesktop() + "html.html";

		FileIO.writeString(filePath, html);

		FileIO.writeString(Systems.getDesktop() + "html2.html", URLReader.read(url, "46.231.14.177", 8080));
	}
}
