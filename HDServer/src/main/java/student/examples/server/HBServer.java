package student.examples.server;

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

import student.examples.comm.Action;

public class HBServer {

	public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException {
		// TODO Auto-generated method stub

		int port = 8888;
		String ipaddress = "localhost";

		ServerSocket listener = new ServerSocket(port, 1, InetAddress.getByName(ipaddress));

		Socket client = null;

		Map<InetAddress, Socket> clients = new HashMap<>();
		// Connection loop
		while (true) {
			System.out.println("[SERVER] Waiting for client connection...");
			client = listener.accept(); // block
			clients.put(client.getInetAddress(), client);
			System.out.println("[SERVER] Connected to client!");
			break;
		}

//		BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
//		
//		BufferedInputStream in = new BufferedInputStream(client.getInputStream());

		// IO loop
		while (true) {
			clients.forEach((innetAddress, clientSocket) -> {
				try {
					
					BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream());
					BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream());
					// block
					int inVal = in.read();
					if (inVal >= 0 && inVal < Action.values().length) {
						Action action = Action.values()[inVal];
						switch (action) {
						case OK:
							System.out.println("Server received OK");
							break;

						case POKE:
							System.out.println("Server received POKE");
							out.write(Action.POKE.ordinal());
							out.flush();
							System.out.println("Server send OK");
							break;
						}
					} else {
						System.out.println("Unknown message");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	}

}
