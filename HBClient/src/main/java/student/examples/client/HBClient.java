package student.examples.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import student.examples.comm.Action;

public class HBClient {
	public static void main(String a[])
			throws NumberFormatException, UnknownHostException, IOException, InterruptedException {

		int port = 8888;
		String ipaddress = "localhost";

		Socket socket = new Socket(ipaddress, port);

		BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());

		BufferedInputStream in = new BufferedInputStream(socket.getInputStream());

		while (true) {
			Thread.sleep(1000);
			out.write(Action.POKE.ordinal());
			out.flush();

			int inVal = in.read();
			if (inVal >= 0 && inVal < Action.values().length) {
				Action action = Action.values()[inVal];
				switch (action) {
				case OK:
					System.out.println("Client received OK");
					break;

				case POKE:
					// ignore
					break;
				}
			}
		}
	}
}
