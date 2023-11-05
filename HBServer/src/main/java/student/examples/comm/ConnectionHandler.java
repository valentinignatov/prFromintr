package student.examples.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionHandler extends Thread {

	private ServerSocket serverSocket;
	private Map<InetAddress, Socket> clients;

	public ConnectionHandler() {}

	public ConnectionHandler(ServerSocket server, ConcurrentHashMap<InetAddress, Socket> clients) {
		super();
		this.serverSocket = server;
		this.clients = clients;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			synchronized (clients) {
				Socket clientSocket = null;
				System.out.println("[SERVER]Connection Handler");
				try {
					clientSocket = serverSocket.accept(); // avoid sync on accept
					serverSocket.setSoTimeout(1000);
				} catch (SocketTimeoutException e) {

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("[SERVER]Client connected to the port" + clientSocket.getLocalPort());
				clients.put(clientSocket.getInetAddress(), clientSocket);
			}
			break;
		}
	}
}
