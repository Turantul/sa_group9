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
     * TODO
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
    
}
