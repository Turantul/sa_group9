package sa12.group9.server.handler;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.SuccessRequest;

public interface IClientServiceHandler
{
	/**
	 * verifies the login
	 * 
	 * @param request
	 * @return
	 */
	boolean verifyLogin(LoginRequest request);

	/**
	 * 
	 * issues a Search Request and returns a Response
	 * 
	 * @param request
	 * @return
	 */
	public SearchIssueResponse issueSearchRequest(SearchIssueRequest request);

	/**
	 * 
	 * notifies on successful match and updates the coins
	 * 
	 * @param request
	 */
	public void notifySuccess(SuccessRequest request);

	/**
	 * 
	 * returns a random list of peers
	 * 
	 * @param numberOfWantedPeers
	 * @return
	 */
	PeerList getRandomPeerList(int numberOfWantedPeers);
}
