package sa12.group9.peer.service;

import java.util.Set;

/**
 * Interface for handling the synchronized acces to the requestlist
 */
public interface IRequestManager
{
    /**
     * Adds a new request to the list
     * 
     * @param id id of the request
     * @param time time in milliseconds until the request is active
     */
    void addRequest(String id, Long time);
    
    /**
     * Determines whether a request was already processed
     * 
     * @param id id of the request
     * @return true if it was already handled
     */
    boolean wasAlreadyHandled(String id);
    
    /**
     * Removes a request from the list
     * 
     * @param id id of the request
     */
    void removeRequest(String id);
    
    /**
     * Gets all current requests
     * 
     * @return List of the ids
     */
    Set<String> getRequestSnapshot();
    
    /**
     * Returns the validity timestamp for a certain request
     * 
     * @param id id of the request
     * @return time in milliseconds
     */
    Long getRequestTime(String id);
}
