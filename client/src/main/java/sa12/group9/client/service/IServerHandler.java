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
     * @return unique ID for identification in further actions
     */
    String loginAtServer(String username, String password);

    /**
     * Reports a new search request to the server
     * 
     * @param userId of the current session
     * @param hash of the file to be looked up
     * @return Response containing the list of peers to be contacted
     */
    SearchIssueResponse generateSearchRequest(String userId, int hash);

    /**
     * Closes a search request by notifying about its success
     * 
     * @param userId of the current session
     * @param hash of the file which was looked up
     * @param information holding information about the peer which found the song
     */
    void notifySuccess(String userId, int hash, FoundInformation information);
}
