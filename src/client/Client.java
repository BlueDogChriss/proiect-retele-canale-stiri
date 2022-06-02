package client;

import java.io.IOException;
import java.net.Socket;

import common.Transport;

public class Client implements AutoCloseable{

	public interface ClientCallback{
		
		void onTalk(String mesaj);
	}
	
	private final Socket socket;
	
	public Client(String host,int port, ClientCallback callback) throws IOException {

		socket=new Socket(host,port);
		new Thread(()-> {
			while(!socket.isClosed()) {
				try {
 					callback.onTalk(Transport.receive(socket));
				}catch(Exception ignored) {
				}
			}
		}).start();
	}
	
	public void send(String mesaj) throws IOException {
		Transport.send(mesaj, socket);
	}

	@Override
	public void close() throws Exception {
		socket.close();
		
	}
}
