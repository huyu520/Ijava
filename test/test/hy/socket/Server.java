package test.hy.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 作用:
 * @author wb-hy292092
 * @date 2017年10月23日 下午5:25:37
 */
public class Server implements BaseData{	
	
	public static void main(String[] args) {
		
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(local_port);
			while (true) {
				Socket socket = serverSocket.accept();
				OutputStream outputStream = socket.getOutputStream();
				DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
				dataOutputStream.writeUTF("hello kitty");
				dataOutputStream.close();
				socket.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
