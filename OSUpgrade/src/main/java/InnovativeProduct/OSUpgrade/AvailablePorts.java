package InnovativeProduct.OSUpgrade;

import java.net.ServerSocket;

public class AvailablePorts {

	
	public String getPort() throws Exception
	{
		ServerSocket socket = new ServerSocket(0);
		socket.setReuseAddress(true);
		String port = Integer.toString(socket.getLocalPort()); 
		socket.close();
		return port;
	}
	
	
	
	public static void main(String[] args) throws Exception 
	{
		System.out.println("Inside main method");
		AvailablePorts ap = new AvailablePorts();
		for(int i=0;i<10;i++)
			System.out.println(ap.getPort());
	}
	
	
	
	
	
	
	
	
}
