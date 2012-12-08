package sa12.group9.client.service;

import sa12.group9.common.beans.FoundInformation;
import sa12.group9.common.beans.SearchIssueResponse;

/**
 * Service interface for communicating with the server
 */
public interface IServerHandler
{
    /**
     * Logs in a user if possible
     * 
     * @param username
     * @param password
     * @return true if successful
     */
    boolean loginAtServer(String username, String password);

    /**
     * Reports a new search request to the server
     * 
     * @param username
     * @param password
     * @param id id of the request
     * @param hash of the file to be looked up
     * @return Response containing the list of peers to be contacted
     */
    SearchIssueResponse generateSearchRequest(String username, String password, String id, int hash);

    /**
     * Closes a search request by notifying about its success
     * 
     * @param username
     * @param password
     * @param id of the lookup request
     * @param information holding information about the peer which found the song
     */
    void notifySuccess(String username, String password, String id, FoundInformation information);
}
