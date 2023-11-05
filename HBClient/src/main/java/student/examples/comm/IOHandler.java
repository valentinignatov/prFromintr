package student.examples.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

public class IOHandler {

	private Socket socket;

	public IOHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		MyLogger logger = new MyLogger();
		logger.getLogger().log(Level.INFO, "[CLIENT]In run() of IOHandler");

		while (true) {
//			Thread.sleep(1000);
			try {
				Thread.sleep(1000);
//				IOStream ioStream = new IOStream(new BufferedOutputStream(socket.getOutputStream()),
//						new BufferedInputStream(socket.getInputStream()));
				
				SecuredIOStream ioStream = new SecuredIOStream(new BufferedOutputStream(socket.getOutputStream()),
				new BufferedInputStream(socket.getInputStream()));
				
//				ioStream.write(Action.POKE.ordinal());
				ioStream.write(1);

				System.out.println("[CLIENT]Client send poke");

//				System.out.println("[CLIENT]Server response" + Action.POKE.ordinal() + ioStream.read());
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
