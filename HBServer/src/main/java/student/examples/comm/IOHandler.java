package student.examples.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class IOHandler extends Thread {
	
	private Map<InetAddress, Socket> clients;

	public IOHandler(Map<InetAddress, Socket> clients) {
		this.clients = clients;
	}

	public void run() {
		MyLogger logger = new MyLogger();
		logger.getLogger().log(Level.INFO, "[SERVER]" +  getName() + "In run() of IOHandler");
		
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized (clients) {

				LoopTask:clients.forEach((innetAddress, clientSocket) -> {
					try {
//						IOStream ioStream = new IOStream(new BufferedOutputStream(clientSocket.getOutputStream()), new BufferedInputStream(clientSocket.getInputStream()));
						SecuredIOStream ioStream = new SecuredIOStream(new BufferedOutputStream(clientSocket.getOutputStream()), new BufferedInputStream(clientSocket.getInputStream()));

							int inVal = ioStream.read();
							System.out.println("[SERVER]Server received " + inVal);
							if (inVal >= 0 && inVal < Action.values().length) {
								Action action = Action.values()[inVal];
								switch (action) {
								case OK:
									System.out.println("[SERVER]Server received OK");
									break;

								case POKE:
									System.out.println("[SERVER]Server received POKE");
									ioStream.write(Action.POKE.ordinal());
									System.out.println("[SERVER]Server send OK");
									break;
									
								case ERROR:
									logger.getLogger().log(Level.SEVERE, getName() + "[SERVER]Error");
									break;
									
								case SHUTDOWN:
									System.exit(0);
								}
							} else {
								System.out.println("[SERVER]Unknown message");
							}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		}
	}
}
