package sa12.group9.server.handlers;

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

		
		
		
        // TODO: check coins and log
        // also provide reasonable TTL, seconds to wait and amount of peers for forwarding!
		return null;
	}

	@Override
	public void notifySuccess(SuccessRequest request) {
		// TODO update coins (peer and client) and log
		
	}



}
