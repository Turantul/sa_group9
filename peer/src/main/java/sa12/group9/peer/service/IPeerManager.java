package sa12.group9.peer.service;

import java.util.HashSet;
import java.util.Set;

import sa12.group9.common.beans.PeerEndpoint;

/**
 * Interface for handling the synchronized acces to the peerlist
 */
public interface IPeerManager {
	
	/**
	 * Add a new PeerEndpoint to the list of peers
	 * @param peer
	 */
	public void addPeerEndpoint(PeerEndpoint peer);
    
	/**
	 * Returns a current snapshot of all known peers
	 * @return An iterable list of all the peers
	 */
    public Set<String> getPeerSnapshot();
    
    /**
     * Returns the PeerEnpoint matching the key
     * @param key
     * @return the requested PeerEndpoint
     */
    public PeerEndpoint getPeerEndpoint(String key);
    
    /**
     * Deletes the PeerEndpoint matching the provided key
     * @param key
     */
    public void removePeerEndpoint(String key);

    /**
     * Returns the number of currentlyn known peers
     * @return number of currently known peers
     */
	public int getPeerCount();
}
