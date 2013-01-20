package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.Request;

public interface IRequestDAO
{
    /**
     * Stores the Request in the database
     * 
     * @param request information about the request a Client calls
     */
    void storeRequest(Request request);

    /**
     * Stores a List of Requests in the database
     * 
     * @param requests a List of requests
     */
    void storeRequests(List<Request> requests);

    /**
     * Searches a Requests by username in the database
     * 
     * @param username the username for finding a Request
     * @return the request
     */
    Request searchRequestByUsername(String username);

    /**
     * Searches a Request by Id in the database
     * 
     * @param id id of the request
     * @return the request
     */
    Request searchRequestById(String id);

    /**
     * Returns all the Requests as a List from the database
     * 
     * @return list of requests
     */
    List<Request> getAllRequests();

    /**
     * Returns the count of all requests for a user from the database
     * 
     * @param username username to look for
     * @return amount of requests
     */
    long getCountOfRequestsForUser(String username);

    /**
     * Updates the Request in the database
     * 
     * @param request the request that has to be updated
     */
    void updateRequest(Request request);

    /**
     * Setting all requests which have ther expected finishingTime exeeded to
     * failed
     */
    void cleanupRequests();
}
