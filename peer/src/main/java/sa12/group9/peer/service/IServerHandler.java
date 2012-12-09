package sa12.group9.peer.service;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;

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
     * Gets random neighbors for this peer
     * 
     * @param username
     * @param password
     * @return list of peers (may be empty)
     */
    List<PeerEndpoint> getNeighbors(String username, String password);
}
