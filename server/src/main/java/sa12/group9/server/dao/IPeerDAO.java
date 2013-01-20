package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;

public interface IPeerDAO
{
    /**
     * 
     * @param peer
     */
    void storePeer(PeerEndpoint peer);

    /**
     * 
     * @param peers
     */
    void storePeers(List<PeerEndpoint> peers);

    /**
     * 
     * @param address
     * @return
     */
    PeerEndpoint searchPeer(String address);

    /**
     * 
     * @return
     */
    List<PeerEndpoint> getAllPeers();

    /**
     * 
     * @return
     */
    long getCountOfPeers();
    
    /**
     * 
     * @param address
     * @param listeningPort
     * @param keepAlivePort
     * @return
     */
    PeerEndpoint getPeer(String address, int listeningPort, int keepAlivePort);
    
    void cleanupPeers(int cleanupPeriod);
}
