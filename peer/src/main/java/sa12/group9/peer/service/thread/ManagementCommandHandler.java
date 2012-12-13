package sa12.group9.peer.service.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import sa12.group9.peer.service.Kernel;

public class ManagementCommandHandler extends Thread {
	
	private Socket socket;

	public ManagementCommandHandler(Socket socket){
		super();
		this.socket = socket;
	}
	
	public void run(){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(!socket.isClosed()){
				System.out.println("Reading command from: "+socket.getPort());
				String inputLine = in.readLine();
				System.out.println("Recieved command: "+inputLine);
			}
		}catch(IOException e){
			System.out.println("Error reading IO Command in ManagementHandler. \n"+e.getMessage());
		}
	}

}
