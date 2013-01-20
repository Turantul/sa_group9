package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;

public interface IPeerDAO
{
    /**
     * 
     * stores the peerEndpoint in the database
     * 
     * @param peer
     */
    void storePeer(PeerEndpoint peer);

    /**
     * 
     * stores a List of PeerEndpoints in the database
     * 
     * @param peers
     */
    void storePeers(List<PeerEndpoint> peers);

    /**
     * 
     * searches a peer by address in the database given as a String 
     * 
     * @param address
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
     * @param address
     * @param listeningPort
     * @param keepAlivePort
     * @return
     */
    PeerEndpoint getPeer(String address, int listeningPort, int keepAlivePort);
    /**
     * 
     * defines the cleanup period for the peers
     * 
     * @param cleanupPeriod
     */
    void cleanupPeers(int cleanupPeriod);
}
