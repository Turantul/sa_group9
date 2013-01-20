package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.Request;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.User;

public interface IRequestDAO
{
    /**
     * 
     * @param request
     */
  
    void storeRequest(Request request);

    /**
     * 
     * @param requests
     */
    void storeRequests(List<Request> requests);

    /**
     * 
     * @param username
     * @return
     */
    
    Request searchRequestByUsername(String username);

    /**
     *
     * 
     * @return
     */
    
    Request searchRequestById(String Id);

    /**
     *
     * 
     * @return
     */
    List<Request> getAllRequests();

    /**
     * 
     * @return
     */

	long getCountOfRequestsForUser(String username);
	
    /**
     * 
     * @param request
     */
    void updateRequest(Request request);
    
    /**
     * Setting all requests which have ther expected finishingTime exeeded to failed
     */
    void cleanupRequests();
     
    
}
