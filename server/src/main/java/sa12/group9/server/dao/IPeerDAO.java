package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;

public interface IPeerDAO
{
    /**
     * 
     * stores the peerEndpoint in the database
     * 
     * @param information about the peer
     */
    void storePeer(PeerEndpoint peer);

    /**
     * 
     * stores a List of PeerEndpoints in the database
     * 
     * @param a List of PeerEndpoints (information about the peer)
     */
    void storePeers(List<PeerEndpoint> peers);

    /**
     * 
     * searches a peer by address in the database given as a String 
     * 
     * @param the address of the peer
     * @return
     */
    PeerEndpoint searchPeer(String address);

    /**
     *
     *	returns all peers from the database
     * 
     * @return
     */
    List<PeerEndpoint> getAllPeers();

    /**
     * 
     * gets the count of all the peers in the database
     * 
     * @return 
     */
    long getCountOfPeers();
    
    /**
     * 
     * returns a peer from the database given an address, a listeningport and a keepaliveport
     * 
     * @param the address of the peer
     * @param the listeningPort of the peer
     * @param the keepAlivePort of the peer
     * @return
     */
    PeerEndpoint getPeer(String address, int listeningPort, int keepAlivePort);
    /**
     * 
     * defines the cleanup period for the peers
     * 
     * @param defines the cleanup period when peers are removed from the database
     */
    void cleanupPeers(int cleanupPeriod);
}
