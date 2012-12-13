package sa12.group9.peer.service.thread;

import java.net.Socket;

public class ManagementCommandHandler extends Thread {
	
	private Socket socket;

	public ManagementCommandHandler(Socket socket){
		super();
		this.socket = socket;
	}
	
	public void run(){
		//So Management stuff here
	}

}
