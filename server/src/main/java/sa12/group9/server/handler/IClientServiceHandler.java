package sa12.group9.server.handler;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.SuccessRequest;

public interface IClientServiceHandler
{
	/**
	 * TODO
	 * 
	 * @param request
	 * @return
	 */
	boolean verifyLogin(LoginRequest request);

	/**
	 * TODO
	 * 
	 * @param request
	 * @return
	 */
	public SearchIssueResponse issueSearchRequest(SearchIssueRequest request);

	/**
	 * TODO
	 * 
	 * @param request
	 */
	public void notifySuccess(SuccessRequest request);

	/**
	 * TODO
	 * 
	 * @param numberOfWantedPeers
	 * @return
	 */
	PeerList getRandomPeerList(int numberOfWantedPeers);
}
