package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;

public interface IPeerDAO
{
    /**
     * Stores the peerEndpoint in the database
     * 
     * @param peer information about the peer
     */
    void storePeer(PeerEndpoint peer);

    /**
     * Stores a List of PeerEndpoints in the database
     * 
     * @param peers a List of PeerEndpoints (information about the peer)
     */
    void storePeers(List<PeerEndpoint> peers);

    /**
     * Searches a peer by address in the database given as a String
     * 
     * @param address the address of the peer
     * @return the peer endpoint
     */
    PeerEndpoint searchPeer(String address);

    /**
     * Returns all peers from the database
     * 
     * @return list of peer endpoints
     */
    List<PeerEndpoint> getAllPeers();

    /**
     * Gets the count of all the peers in the database
     * 
     * @return amount of peers
     */
    long getCountOfPeers();

    /**
     * Returns a peer from the database given an address, a listeningport and a
     * keepaliveport
     * 
     * @param address the address of the peer
     * @param listeningPort the listeningPort of the peer
     * @param keepAlivePort the keepAlivePort of the peer
     * @return the peer endpoint
     */
    PeerEndpoint getPeer(String address, int listeningPort, int keepAlivePort);

    /**
     * Defines the cleanup period for the peers
     * 
     * @param cleanupPeriod defines the cleanup period when peers are removed
     *        from the database
     */
    void cleanupPeers(int cleanupPeriod);
}
