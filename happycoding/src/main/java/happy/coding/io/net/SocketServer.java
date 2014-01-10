package happy.coding.io.net;

import happy.coding.io.Logs;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;

public class SocketServer
{
	protected final static Logger	logger	= Logs.getLogger();
	private ServerSocket ss;
	
	public SocketServer(int port) throws Exception
	{
		ss = new ServerSocket(port);
		logger.info("Server Listen to Port: {}", port);
	}
	
	public void listen() throws Exception
	{
		while (true)
		{
			if (ss == null) return;
			Socket conn = ss.accept();
			logger.debug("Get Connection from " + conn.getRemoteSocketAddress());

			InputStream is = conn.getInputStream();
			StringBuilder sb = new StringBuilder();
			int ch;

			while ((ch = is.read()) != -1)
				sb.append((char) ch);
			
			logger.info("Message from Client: {}", sb.toString());

			is.close();
			conn.close();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new SocketServer(9001).listen();
	}
	
}
