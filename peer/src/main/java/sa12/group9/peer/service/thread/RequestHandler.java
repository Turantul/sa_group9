package sa12.group9.peer.service.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.P2PSearchRequest;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;
import sa12.group9.peer.service.Kernel;

public class RequestHandler extends Thread
{
    private Socket socket;
    private Kernel kernel;
    
    public RequestHandler(Socket socket, Kernel kernel)
    {
        this.socket = socket;
        this.kernel = kernel;
    }
    
    @Override
    public void run()
    {
		
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			P2PSearchRequest input = (P2PSearchRequest) in.readObject();
			System.out.println("Got Request "+input.getId());
			forwardToPeers(input);
			calculateMatch(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }
    
    private void calculateMatch(P2PSearchRequest input) {
		List<Fingerprint> fingerprintList = kernel.getFingerprintSnapshot();
		System.out.println(new Date(System.currentTimeMillis())+" - trying to calculate match with "+fingerprintList.size()+" fingerprints.");
		for(Fingerprint fp : fingerprintList){
			Double match = fp.match(input.getFingerprint());
			//if(match>0.5)
			{
				System.out.println("Fingerprint match found. Send Success to client.");
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		System.out.println(new Date(System.currentTimeMillis())+" - matching done.");
	}

	private void forwardToPeers(P2PSearchRequest input) {
		//Should be configurable in request or per peer ?
		if(input.getTtl()>0){
			P2PSearchRequest request = new P2PSearchRequest();
            request.setId(input.getId());
            request.setFingerprint(input.getFingerprint());
            request.setRequesterAddress(input.getRequesterAddress());
            request.setRequesterPort(input.getRequesterPort());
            request.setTtl(input.getTtl()-1);
			List<PeerEndpoint> peerList = selectRandomPeers(4);
			for(PeerEndpoint pe : peerList){
				try {
					Socket socket = new Socket(pe.getAddress(), pe.getListeningPort());
					ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
		            socketout.writeObject(request);
		            socketout.close();
				} catch (Exception e) {
					System.out.println("Error forwarding requests to other peers");
				} 
	            
			}
		}
	}

	private List<PeerEndpoint> selectRandomPeers(int numberOfWantedPeers){
    	List<String> peerList = new ArrayList<String>(kernel.getPeerSnapshot());
    	
		ArrayList<PeerEndpoint> randomPeersSelection = new ArrayList<PeerEndpoint>();		
		
		Random random = new Random();
		int randomlyChosenPeersCount = 0;
		
		
		//iterate as long as either the wanted number is reached or the maximum number of peers in database return
		while((randomlyChosenPeersCount < numberOfWantedPeers) && (randomlyChosenPeersCount < peerList.size())){
			PeerEndpoint randomPeer = kernel.getPeerEndpoint(peerList.get(random.nextInt(peerList.size())));
			if(!randomPeersSelection.contains(randomPeer)){
				randomPeersSelection.add(randomPeer);
				randomlyChosenPeersCount++;
			}
		}

		return randomPeersSelection;
    }
}
