package student.examples.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

public class HBClient {

	private final String ipaddress;// = "localhost";
	private ConnectionHandler connectionHandler = null; // Usualy this is injected
	private int BACKLOG = 1;
	private IOHandler IOHandler;

	public HBClient(Socket socket, int BACKLOG) throws IOException {
		this.BACKLOG = BACKLOG;
		this.connectionHandler = new ConnectionHandler(socket);
		this.IOHandler = new IOHandler(socket);
		this.ipaddress = "";
	}

	public void run() {
		connectionHandler.run();
		IOHandler.run();
	}

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		int port = 7778;
		String ipaddress = "localhost";
		
		HBClient hbClient = new HBClient(new Socket(ipaddress, port), 100);
		hbClient.run();

//		Socket socket = new Socket(ipaddress, port);
//		System.out.println("[CLIENT]Server connected");
//
////		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
////		BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
//
//		IOStream ioStream = new IOStream(new BufferedOutputStream(socket.getOutputStream()),
//				new BufferedInputStream(socket.getInputStream()));
//
//		// Connection loop
//		while (true) {
//			Thread.sleep(1000);
//			ioStream.write(Action.POKE.ordinal());
//			System.out.println("Client send poke");
//
//			System.out.println("Server response" + Action.POKE.ordinal() + ioStream.read());

//			break;

//			int inVal = in.read();
//			if (inVal >= 0 && inVal < Action.values().length) {
//				Action action = Action.values()[inVal];
//				switch (action) {
//				case OK:
//					System.out.println("Client received OK");
//					break;
//
//				case POKE:
//					// ignore
//					break;
//				}
//			}
		}
	}
