package sa12.group9.server.dao;

import java.util.List;


import sa12.group9.common.beans.Request;


public interface IRequestDAO
{
    /**
     * stores the Request in the database
     * 
     * @param information about the request a Client calls
     */
  
    void storeRequest(Request request);

    /**
     * 
     * stores a List of Requests in the database
     * 
     * @param a List of requests
     */
    void storeRequests(List<Request> requests);

    /**
     * 
     * searches a Requests by username in the database
     * 
     * @param the username for finding a Request
     * @return
     */
    
    Request searchRequestByUsername(String username);

    /**
     *
     * searches a Request by Id in the database
     * 
     * @return
     */
    
    Request searchRequestById(String Id);

    /**
     *
     * returns all the Requests as a List from the database
     * 
     * @return
     */
    List<Request> getAllRequests();

    /**
     * 
     * returns the count of all requests for a user from the database
     * 
     * @return
     */

	long getCountOfRequestsForUser(String username);
	
    /**
     * 
     * updates the Request in the database
     * 
     * @param the request that has to be updated
     */
    void updateRequest(Request request);
    
    /**
     * Setting all requests which have ther expected finishingTime exeeded to failed
     */
    void cleanupRequests();
     
    
}
