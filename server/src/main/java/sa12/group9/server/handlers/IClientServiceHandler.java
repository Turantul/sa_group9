package sa12.group9.server.handlers;

import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.SuccessRequest;

public interface IClientServiceHandler
{
	/**
	 * 
	 * 
	 * @param username
	 * @param password
	 * @return 
	 */
	boolean verifyLogin(String username, String password);

	/**
	 * 
	 * @param request
	 * @return
	 */
	
	public SearchIssueResponse issueSearchRequest(SearchIssueRequest request);
	
	/**
	 * 
	 * @param request
	 */
	
	public void notifySuccess(SuccessRequest request);

	/**
	 * 
	 * @param numberOfWantedPeers
	 * @return
	 */
	
	PeerList getRandomPeerList(int numberOfWantedPeers);
    
}
