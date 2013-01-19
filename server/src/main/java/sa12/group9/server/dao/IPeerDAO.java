package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;

public interface IPeerDAO
{
    /**
     * TODO
     * 
     * @param peer
     */
    void storePeer(PeerEndpoint peer);

    /**
     * TODO
     * 
     * @param peers
     */
    void storePeers(List<PeerEndpoint> peers);

    /**
     * TODO
     * 
     * @param address
     * @return
     */
    PeerEndpoint searchPeer(String address);

    /**
     * TODO
     * 
     * @return
     */
    List<PeerEndpoint> getAllPeers();

    /**
     * TODO
     * 
     * @return
     */
    long getCountOfPeers();
    
    /**
     * TODO
     * 
     * @param address
     * @param listeningPort
     * @param keepAlivePort
     * @return
     */
    PeerEndpoint getPeer(String address, int listeningPort, int keepAlivePort);
}
