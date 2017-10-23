package test.hy.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 作用:
 * @author wb-hy292092
 * @date 2017年10月23日 下午5:25:26
 */
public class Client implements BaseData {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket socket = new Socket(local_address, local_port);
			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			System.out.println(dataInputStream.readUTF());
			dataInputStream.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
