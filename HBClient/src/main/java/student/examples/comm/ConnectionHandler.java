package student.examples.comm;

import java.net.Socket;

public class ConnectionHandler {
	
	private Socket socket;
	
	public ConnectionHandler(Socket socket) {
		super();
		this.socket = socket;
	}
	
	public void run() {
		System.out.println("[CLIENT]Server connected");
	}
}
