package sa12.group9.server.handler;

import java.util.ArrayList;
import java.util.Random;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.SuccessRequest;
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
	public SearchIssueResponse issueSearchRequest(SearchIssueRequest request) {

		
		SearchIssueResponse response = new SearchIssueResponse();
		
		try{
			IUserDAO userdao = MongoUserDAO.getInstance();
			
			UserDTO fetcheduser = userdao.searchUser(request.getUsername());
			
			
			if(fetcheduser.getCoins() <=0){
				response.setErrorMsg("No coins left");
			}
			else{
			    response.setPeers(this.getRandomPeerList(10));
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		

		
        // TODO: check coins and log
        // also provide reasonable TTL, seconds to wait and amount of peers for forwarding!
		return response;
	}

	@Override
	public void notifySuccess(SuccessRequest request) {
		
		try{
			IUserDAO userdao = MongoUserDAO.getInstance();
			
			UserDTO fetcheduser = userdao.searchUser(request.getUsername());
		
			fetcheduser.setCoins(fetcheduser.getCoins() - 1);
					
			
		}catch (Exception e) {
			e.printStackTrace();
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
