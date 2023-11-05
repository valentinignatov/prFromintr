package student.examples.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SecuredIOStream extends IOStream {
	private static final String DIR = System.getProperty("user.dir");
	private BufferedOutputStream out;
	private BufferedInputStream in;

	SecuredIOStream(BufferedOutputStream out, BufferedInputStream in) {
		super(out, in);
		this.out = out;
		this.in = in;
	}

	@Override
	public int write(int value) throws IOException {

//		byte[] bb = intToBytes(value);
//		int value2 = convertByteArrayToInt(bb);
//		System.out.println("initial value " + value + " converted " + value2);
//		
//		byte[] ba2 = {1,2,3,4,5};
//		
//		byte[] encryptedCustomn = encrypt(5555);
//		int decryptedCustomn = decrypt(encryptedCustomn);
//		System.out.println("CUSTOMN VALUE" + decryptedCustomn);

		PublicKey publicKey = readPublicKeyFile("publicKey");
		byte[] encryptedBytes = new byte[8];

//		try {
//			Cipher cipher = Cipher.getInstance("RSA");
//			cipher.init(cipher.ENCRYPT_MODE, publicKey);
//			cipher.update(intToBytes(value));
//			encryptedBytes = cipher.doFinal();

		encryptedBytes = encrypt(value);

//			encryptedBytes = cipher.doFinal(new byte[] { (byte) value });

//		System.out.println(
//				"[SecuredIOStream] Test actual value " + value + " decrypted value " + decrypt(encryptedBytes));

//		super.write(convertByteArrayToInt(encryptedBytes));
		
		System.out.println("encryptedBytes len "+encryptedBytes.length);
		out.write(encryptedBytes);
		out.flush();
//		} catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
//				| NoSuchPaddingException | InvalidKeyException e) {
//			e.printStackTrace();
//		}
		return convertByteArrayToInt(encryptedBytes);
	}

	@Override
	public int read() throws IOException {
		PrivateKey privateKey = readPrivateKeyFile("privateKey");
//		byte value = (byte) super.read();
		
		byte[] encryptedBytes = in.readNBytes(64);
//		byte[] encryptedBytes = in.readNBytes(8);
		int value = 0;
//		if (in.available() > 0) {
//			value = in.read();
//			return value;
//		}
//		value = super.read();
//		
//		byte[] cryptedBytesValue = intToBytes(value);
		int decryptedValue = decrypt(encryptedBytes);
		System.out.println("[SecuredIOStream]DECRYPTED VALUE "+decryptedValue);
		
//		Cipher cipher;

//		try {
//			cipher = Cipher.getInstance("RSA");
//			cipher.init(cipher.DECRYPT_MODE, privateKey);
//			byte[] decriptedBytes = cipher.doFinal(new byte[] { (byte) value });
//			return (int) decriptedBytes[0];
//
//		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
//				| BadPaddingException e) {
//			e.printStackTrace();
			return -1;
//		}
	}

//	private byte[] decrypt(byte[] value) {
//		PrivateKey privateKey = readPrivateKeyFile("privateKey");
//		Cipher cipher;
//		int returnValue = -999999;
//		byte[] decriptedBytes = null;
//		try {
//			cipher = Cipher.getInstance("RSA/ECB/NoPadding");
//			cipher.init(cipher.DECRYPT_MODE, privateKey);
//			decriptedBytes = cipher.doFinal();
//			returnValue = convertByteArrayToInt(decriptedBytes);
//		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return decriptedBytes;
////		return returnValue;
//		
//	}
//	
//	private byte[] encrypt(int value, byte[] byteVal) {
//		PublicKey publicKey = readPublicKeyFile("publicKey");
//		Cipher cipher;
//		byte[] encryptedBytes = null;
//		try {
//			cipher = Cipher.getInstance("RSA/ECB/NoPadding");
//			cipher.init(cipher.ENCRYPT_MODE, publicKey);
////			cipher.update(new byte[] { (byte) value });
//			cipher.update(byteVal);
//			encryptedBytes = cipher.doFinal();
//			
//		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return encryptedBytes;
//		
//	}

	//WORKING
	private int decrypt(byte[] value) {
		PrivateKey privateKey = readPrivateKeyFile("privateKey");
		Cipher cipher;
		int returnValue = -9;
//		byte[] decriptedBytes = null;
		byte[] decriptedBytes = new byte[8];
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(cipher.DECRYPT_MODE, privateKey);
			cipher.update(value);
			decriptedBytes = cipher.doFinal();
			returnValue = convertByteArrayToInt(decriptedBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;

	}

	//WORKING
	private byte[] encrypt(int value) {
		PublicKey publicKey = readPublicKeyFile("publicKey");
		Cipher cipher;
//		byte[] encryptedBytes = null;
		byte[] encryptedBytes = new byte[8];
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(cipher.ENCRYPT_MODE, publicKey);
			cipher.update(intToBytes(value));
			encryptedBytes = cipher.doFinal();

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptedBytes;
	}

	//WORKING
	private int convertByteArrayToInt(byte[] intBytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
		return byteBuffer.getInt();
	}

	//WORKING
	private byte[] intToBytes(int i) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(i);
		return bb.array();
	}

	private PublicKey readPublicKeyFile(String fileName) {
		InputStream input;
		byte[] arr;

		try {
			input = new FileInputStream(constructPath(fileName));
			arr = input.readAllBytes();
			X509EncodedKeySpec spec = new X509EncodedKeySpec(arr);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			return kf.generatePublic(spec);
		} catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}

	private PrivateKey readPrivateKeyFile(String fileName) {
		InputStream input;
		byte[] arr;

		try {
			input = new FileInputStream(constructPath(fileName));
			arr = input.readAllBytes();
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(arr);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			return kf.generatePrivate(spec);
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}

	}

	private String constructPath(String filename) {
		StringBuilder builder = new StringBuilder();

		builder.append(DIR);
		builder.append("\\");
		builder.append(filename);
		builder.append(".txt");

		return builder.toString();
	}
}
