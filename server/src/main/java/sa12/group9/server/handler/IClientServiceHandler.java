package sa12.group9.server.handler;

import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.SearchIssueResponse;
import sa12.group9.common.beans.SuccessRequest;

public interface IClientServiceHandler
{
    /**
     * Verifies the login
     * 
     * @param request the request to verify the login
     * @return true if successful
     */
    boolean verifyLogin(LoginRequest request);

    /**
     * Issues a Search Request and returns a Response
     * 
     * @param request the request to issue a Search
     * @return response
     */
    SearchIssueResponse issueSearchRequest(SearchIssueRequest request);

    /**
     * Notifies on successful match and updates the coins
     * 
     * @param request the request that comes back on a successful match
     */
    void notifySuccess(SuccessRequest request);

    /**
     * Returns a random list of peers
     * 
     * @param numberOfWantedPeers the number of wanted peers to fetch
     * @return random subset
     */
    PeerList getRandomPeerList(int numberOfWantedPeers);
}
