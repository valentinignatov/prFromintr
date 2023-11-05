package student.examples.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HBServer {

	private final int port;// = 8888;
	private final String ipaddress;// = "localhost";
	private ConnectionHandler connectionHandler = null; //Usualy this is injected
	private int BACKLOG = 1;
	private IOHandler IOHandler;
	private ConcurrentHashMap<InetAddress, Socket> clients;

	public HBServer(int port, String host, int BACKLOG) {
		this.port = port;
		this.ipaddress = host;
		this.BACKLOG = BACKLOG;
		clients = new ConcurrentHashMap<InetAddress, Socket>();
		try {
			this.connectionHandler = new ConnectionHandler(new ServerSocket(this.port, this.BACKLOG, InetAddress.getByName(ipaddress)), clients);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.IOHandler = new IOHandler(clients);
	}
	
	public void run() {
		connectionHandler.start(); //problem in connection handler
		IOHandler.start();
//		connectionHandler.run();
//		IOHandler.run();
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		
		HBServer hbServer = new HBServer(7778, "localhost", 100);
		hbServer.run();
		System.out.println("[SERVER]Working on port " + hbServer.port);

//		ServerSocket listener = new ServerSocket(hbServer.port, 1, InetAddress.getByName(hbServer.ipaddress));

//		Socket client = null;
//
//		Map<InetAddress, Socket> clients = new HashMap<>();
		// Connection loop
//		while (true) {
//			System.out.println("[SERVER] Waiting for client connection...");
//			client = listener.accept(); // block
//			clients.put(client.getInetAddress(), client);
//			System.out.println("[SERVER] Connected to client!");
//			break;
//		}
//
//		BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
//		
//		BufferedInputStream in = new BufferedInputStream(client.getInputStream());
//
//		// IO loop
//		while (true) {
//			clients.forEach((innetAddress, clientSocket) -> {
//				try {
//
//					BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());
//					BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream());
//					// block
//					int inVal = in.read();
//					if (inVal >= 0 && inVal < Action.values().length) {
//						Action action = Action.values()[inVal];
//						switch (action) {
//						case OK:
//							System.out.println("Server received OK");
//							break;
//
//						case POKE:
//							System.out.println("Server received POKE");
//							out.write(Action.POKE.ordinal());
//							out.flush();
//							System.out.println("Server send OK");
//							break;
//						}
//					} else {
//						System.out.println("Unknown message");
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			});
//		}
	}

}
