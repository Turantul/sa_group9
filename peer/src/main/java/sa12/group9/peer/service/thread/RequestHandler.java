package sa12.group9.peer.service.thread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.P2PSearchRequest;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.peer.service.IPeerManager;
import sa12.group9.peer.service.Kernel;

public class RequestHandler extends Thread
{
	private static Log log = LogFactory.getLog(RequestHandler.class);
	
    private Socket socket;
    private Kernel kernel;
    private IPeerManager peerManager;
    
    public RequestHandler(Socket socket, Kernel kernel, IPeerManager peerManager)
    {
        this.socket = socket;
        this.kernel = kernel;
        this.peerManager = peerManager;
    }
    
    @Override
    public void run()
    {
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Object inputObj = in.readObject();
			if(inputObj.getClass()==P2PSearchRequest.class){
				P2PSearchRequest input = (P2PSearchRequest) inputObj;
				log.info("Got Request "+input.getId());
				
				forwardToPeers(input);
				calculateMatch(input);
			}
			if(inputObj.getClass()==FoundInformation.class){
				FoundInformation input = (FoundInformation) inputObj;
				log.info("Got Sucessful response from "+input.getPeerUsername());
			}
		} catch (Exception e) {
			log.error("Error reading request from "+socket.getInetAddress()+":"+socket.getPort());
		}
    }
    
    private void calculateMatch(P2PSearchRequest input) {
		List<Fingerprint> fingerprintList = kernel.getFingerprintSnapshot();
		log.info(new Date(System.currentTimeMillis())+" - trying to calculate match for request("+input.getId()+"(with "+fingerprintList.size()+" fingerprints.");
		for(Fingerprint fp : fingerprintList){
			Double match = fp.match(input.getFingerprint());
			if(match>=0){
				log.info("Fingerprint match found. Send Success to client.");
				sendResponseToRequester(input, match);
			}
		}
		System.out.println(new Date(System.currentTimeMillis())+" - matching done.");
	}

	private void sendResponseToRequester(P2PSearchRequest input, Double match) {
		//TODO need to Use song meta information from DB
		try {
			Socket socket = new Socket(input.getRequesterAddress(), input.getRequesterPort());
			ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
			FoundInformation response = new FoundInformation();
			response.setAlbum("Affen");
			response.setGenre("Affensongs");
			response.setInterpret("Gorillaz");
			response.setLength(230);
			response.setTitle("Michi der kleine Affe");
			response.setMatch(match);
		    socketout.writeObject(response);
		    socketout.close();
		} catch (Exception e) {
			log.error("Error sending FoundInformation response to requester");
		}
	}

	private void forwardToPeers(P2PSearchRequest input) {
		if(input.getTtl()>0){
			P2PSearchRequest request = new P2PSearchRequest();
            request.setId(input.getId());
            request.setFingerprint(input.getFingerprint());
            request.setRequesterAddress(input.getRequesterAddress());
            request.setRequesterPort(input.getRequesterPort());
            request.setTtl(input.getTtl()-1);
			List<PeerEndpoint> peerList = selectRandomPeers(input.getMaxPeersForForwarding());
			for(PeerEndpoint pe : peerList){
				forwardRequest(request, pe);
			}
		}
	}

	private void forwardRequest(P2PSearchRequest request, PeerEndpoint pe) {
		try {
			Socket socket = new Socket(pe.getAddress(), pe.getListeningPort());
			ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
		    socketout.writeObject(request);
		    socketout.close();
		} catch (Exception e) {
			log.error("Error forwarding requests to peer "+pe.getAddress()+":"+pe.getListeningPort());
		}
	}

	private List<PeerEndpoint> selectRandomPeers(int numberOfWantedPeers){
    	List<String> peerList = new ArrayList<String>(peerManager.getPeerSnapshot());
		ArrayList<PeerEndpoint> randomPeersSelection = new ArrayList<PeerEndpoint>();
		Random random = new Random();
		int randomlyChosenPeersCount = 0;
		
		while((randomlyChosenPeersCount < numberOfWantedPeers) && (randomlyChosenPeersCount < peerList.size())){
			PeerEndpoint randomPeer = peerManager.getPeerEndpoint(peerList.get(random.nextInt(peerList.size())));
			if(!randomPeersSelection.contains(randomPeer)){
				randomPeersSelection.add(randomPeer);
				randomlyChosenPeersCount++;
			}
		}
		return randomPeersSelection;
    }
}
