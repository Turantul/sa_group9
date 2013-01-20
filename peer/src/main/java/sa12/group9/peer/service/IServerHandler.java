package sa12.group9.peer.service;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;

/**
 * Service interface for communicating with the server
 */
public interface IServerHandler
{
    /**
     * Gets random neighbors for this peer
     * 
     * @param username
     * @param password
     * @return list of peers (may be empty)
     */
    List<PeerEndpoint> getNeighbors(String username, String password);

    /**
     * Notifies about the availability
     * 
     * @param username
     * @param password
     * @param listeningPort
     * @param keepAlivePort
     * @return success of notification
     */
    boolean isAlive(String username, String password, int listeningPort, int keepAlivePort);
}
