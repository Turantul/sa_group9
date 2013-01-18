package sa12.group9.server.handler;

import java.util.ArrayList;
import java.util.Random;

import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;
import sa12.group9.commons.dto.UserDTO;
import sa12.group9.server.dao.IPeerDAO;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoPeerDAO;
import sa12.group9.server.dao.MongoUserDAO;

public class PeerServiceHandler implements IPeerServiceHandler {

	@Override
	public boolean verifyLogin(IsAliveNotification request) {
		
		IUserDAO userdao = MongoUserDAO.getInstance();
		IPeerDAO peerdao = MongoPeerDAO.getInstance();
		
		UserDTO fetcheduser = userdao.searchUser(request.getUsername());
	
		if(fetcheduser == null){
			return false;
		}
		
		
		if(request.getUsername().equals(fetcheduser.getUsername()) && 
		request.getPassword().equals(fetcheduser.getPassword())){
			System.out.println("successfully logged in as " + fetcheduser.getUsername().toString() + "");
			return true;
		}
		else{
			return false;
		}
		
		peerdao.storePeer(request.g)
	}

	@Override
	public PeerList getRandomPeerList(int numberOfWantedPeers) {

		IPeerDAO peerdao = MongoPeerDAO.getInstance();
		PeerList allPeers = new PeerList();
		ArrayList<PeerEndpoint> randomPeersSelection = new ArrayList<PeerEndpoint>();
		allPeers.setPeers(peerdao.getAllPeers());
		
		
		Random random = new Random();
		int randomlyChosenPeersCount = 0;
		
		
		//iterate as long as either the wanted number is reached or the maximum number of peers in database return
		while((randomlyChosenPeersCount < numberOfWantedPeers) && (randomlyChosenPeersCount < allPeers.getPeers().size())){

			randomPeersSelection.add(allPeers.getPeers().get(random.nextInt(allPeers.getPeers().size())));
			
			randomlyChosenPeersCount++;
		}

		
		PeerList returnPeerList = new PeerList();
		returnPeerList.setPeers(randomPeersSelection);

		return returnPeerList;
		
	}

}
