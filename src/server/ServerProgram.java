package server;

import java.util.ResourceBundle;
import java.util.Scanner;
import common.Settings;

public class ServerProgram {
	
	public static void main(String[] args) {
		
		try (Server server=new Server()){
			int port = Integer.parseInt(ResourceBundle.getBundle("settings").getString("port"));
			server.start(Settings.PORT);
			System.out.printf("Server running on port %d, type 'exit' to close%n", port);
			try(Scanner scanner =new Scanner(System.in)){
				while(true) {
					String command=scanner.nextLine();
					if(command==null || "exit".equalsIgnoreCase(command)) {
						break;
					}
				}
			}
		}catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}finally {
			System.exit(0);
		}
	}
}
