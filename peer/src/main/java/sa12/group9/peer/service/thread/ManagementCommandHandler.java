package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.peer.service.Kernel;

public class ManagementCommandHandler extends Thread {
	
	private Socket socket;
	private Kernel kernel;

	public ManagementCommandHandler(Socket socket, Kernel kernel){
		super();
		this.socket = socket;
		this.kernel = kernel;
	}
	
	public void run(){
		try{
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			while(!socket.isClosed()){
				Fingerprint input = (Fingerprint) in.readObject();
				kernel.addFingerprint(input);
			}
		}catch(IOException e){
			System.out.println("Error reading IO Command in ManagementHandler. \n"+e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Problem reading fingerprint. \n"+e.getMessage());
		}
	}
	
	public void shutdown(){
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Error closing management handler socket\n"+e.getMessage());
		}
	}
}
