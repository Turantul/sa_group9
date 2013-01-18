package sa12.group9.client.service;

import java.io.IOException;

import sa12.group9.common.beans.PeerEndpoint;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

/**
 * Service interface for communicating with the peer to peer network
 */
public interface IPeerHandler
{
    /**
     * Opens a socket to listen for incoming replies from peers
     * 
     * @param mainAction object to be called back when a reply comes in
     * @param seconds how long the port should listen
     * @throws IOException
     */
    void openListeningSocket(ICallback mainAction, int seconds) throws IOException;

    /**
     * Issues a search request and sends it to the respective peer
     * @param id 
     * 
     * @param id of the request
     * @param peer which gets the search request
     * @param fingerprint of the song to be searched
     * @throws IOException
     */
    void sendSearchRequest(String id, PeerEndpoint peer, Fingerprint fingerprint) throws IOException;
}
