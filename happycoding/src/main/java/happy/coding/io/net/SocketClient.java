package happy.coding.io.net;

import happy.coding.io.Logs;

import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;

public class SocketClient
{
	protected final static Logger	logger	= Logs.getLogger();
	private Socket socket;
	private String remoteHost;
	private int remotePort;
	
	public SocketClient(String host, int port)
	{
		this.remoteHost = host;
		this.remotePort = port;
	}
	
	public void write(String content) throws Exception
	{
		socket = new Socket(remoteHost, remotePort);
		
		OutputStream os = socket.getOutputStream();
		
		content += "\r\n";
		os.write(content.getBytes());
		os.flush();
		logger.info("Output to Server: {}", content);
		
		os.close();
		socket.close();
	}

	public static void main(String[] args) throws Exception
	{
		new SocketClient("localhost", 9001).write("Client Message");
	}
	
}
