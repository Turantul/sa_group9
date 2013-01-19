package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.common.beans.ManagementCommand;
import sa12.group9.common.beans.ManagementCommandResponse;
import sa12.group9.peer.service.IPeerManager;
import sa12.group9.peer.service.Kernel;

public class ManagementCommandHandler extends Thread {
	
	private static Log log = LogFactory.getLog(ManagementCommandHandler.class);
	
	private Socket socket;
	private Kernel kernel;
	private IPeerManager peerManager;

	public ManagementCommandHandler(Socket socket, Kernel kernel, IPeerManager peerManager){
		super();
		this.socket = socket;
		this.kernel = kernel;
		this.peerManager = peerManager;
	}
	
	public void run(){
		try{
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				ManagementCommand input = (ManagementCommand) in.readObject();
				log.debug("Received Management Command: "+input.getCommand());
				ManagementCommandResponse response = handleRequest(input);
				ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
	            socketout.writeObject(response);
		}catch(IOException e){
			if(!socket.isClosed()){
				log.error("Error reading IO Command in ManagementHandler. \n"+e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			log.error("Problem reading fingerprint. \n"+e.getMessage());
		}
	}
	
	public ManagementCommandResponse handleRequest(ManagementCommand command){
		String response = "";
		String[] split = command.getCommand().split(" ");
		if(split[0].equals("!stats")){
			response += kernel.getFingerprintSnapshot().size()+" fingerprints stored.\n";
			response += peerManager.getPeerCount()+" neighboring peers known.";
		}
		if(split[0].equals("!files")){
			List<Fingerprint> fingerprintList = kernel.getFingerprintSnapshot();
			int num = 1;
			for(Fingerprint fp : fingerprintList){
				response+=num+" "+fp.hashCode()+"\n";
				num++;
			}
		}
		if(split[0].equals("!addfile")){
			kernel.addFingerprint(command.getFingerprint());
			response += "Added fingerprint to database.";
		}
		if(split[0].equals("!removefile")){
			response += "Removed peer from database.";
		}
		return new ManagementCommandResponse(response);
	}
	
	public void shutdown(){
		log.debug("Shutdown ManagementCommandHandler");
		try {
			socket.close();
		} catch (IOException e) {
			log.error("Error closing management handler socket\n"+e.getMessage());
		}
	}
}
