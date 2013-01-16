package sa12.group9.server.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;
import sa12.group9.commons.dto.UserDTO;
import sa12.group9.server.dao.IPeerDAO;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoPeerDAO;
import sa12.group9.server.dao.MongoUserDAO;

public class ClientServiceHandler implements IClientServiceHandler {

	@Override
	public boolean verifyLogin(String username, String password) {
		
		IUserDAO userdao = MongoUserDAO.getInstance();
		
		UserDTO fetcheduser = userdao.searchUser(username);
	
		if(fetcheduser == null){
			return false;
		}
		
		
		if(username.equals(fetcheduser.getUsername()) && 
		password.equals(fetcheduser.getPassword())){
			System.out.println("successfully logged in as " + fetcheduser.getUsername().toString() + "");
			return true;
		}
		else{
			return false;
		}
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
