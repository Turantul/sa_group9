package sa12.group9.peer.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import sa12.group9.common.beans.PeerEndpoint;

public class PeerManager implements IPeerManager{
	private Map<String, PeerEndpoint> peerMap;
	
	public PeerManager(){
		peerMap = Collections.synchronizedMap(new HashMap<String, PeerEndpoint>());
	}
	
	public void addPeerEndpoint(PeerEndpoint peer){
    	synchronized(peerMap){
    		peerMap.put(peer.getAddress()+":"+peer.getListeningPort(), peer);
    	}
    }
    
    public Set<String> getPeerSnapshot(){
    	synchronized(peerMap){
    		return new HashSet<String>(peerMap.keySet());
    	}
    }
    
    public PeerEndpoint getPeerEndpoint(String key){
    	synchronized(peerMap){
    		return peerMap.get(key);
    	}
    }
    
    public void removePeerEndpoint(String key) {
		synchronized(peerMap){
			if(peerMap.containsKey(key)){
				peerMap.remove(key);
			}
		}
	}

	public int getPeerCount() {
		synchronized(peerMap){
			return peerMap.size();
		}
	}
	
	
}
