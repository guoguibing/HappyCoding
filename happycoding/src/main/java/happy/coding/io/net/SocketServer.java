package happy.coding.io.net;

import happy.coding.io.Logs;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer
{
	private ServerSocket ss;
	
	public SocketServer(int port) throws Exception
	{
		ss = new ServerSocket(port);
		Logs.info("Server Listen to Port: {}", port);
	}
	
	public void listen() throws Exception
	{
		while (true)
		{
			if (ss == null) return;
			Socket conn = ss.accept();
			Logs.debug("Get Connection from " + conn.getRemoteSocketAddress());

			InputStream is = conn.getInputStream();
			StringBuilder sb = new StringBuilder();
			int ch;

			while ((ch = is.read()) != -1)
				sb.append((char) ch);
			
			Logs.info("Message from Client: {}", sb.toString());

			is.close();
			conn.close();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new SocketServer(9001).listen();
	}
	
}
