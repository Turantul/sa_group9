package sa12.group9.server.dao;

import java.util.List;


import sa12.group9.common.beans.Request;


public interface IRequestDAO
{
    /**
     * stores the Request in the database
     * 
     * @param request
     */
  
    void storeRequest(Request request);

    /**
     * 
     * stores a List of Requests in the database
     * 
     * @param requests
     */
    void storeRequests(List<Request> requests);

    /**
     * 
     * searches a Requests by username in the database
     * 
     * @param username
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
     * @param request
     */
    void updateRequest(Request request);
    
}
