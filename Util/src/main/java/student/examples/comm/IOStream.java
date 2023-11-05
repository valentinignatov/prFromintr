package student.examples.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.crypto.KeyGenerator;

public class IOStream {
	private BufferedOutputStream out;
	private BufferedInputStream in;
	
	KeyGenerator keyGen;

	public IOStream(BufferedOutputStream out, BufferedInputStream in) {
		super();
		this.out = out;
		this.in = in;
	}

	public int write(int value) throws IOException {
		out.write(value);
		out.flush();
		return value;
	}

	public int read() throws IOException {
		if (in.available() > 0) {
			int value = in.read();
			return value;
		} else return -1;
	}
}
