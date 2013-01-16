package sa12.group9.server.handlers;


import sa12.group9.common.beans.PeerList;

public interface IClientServiceHandler {

	
	public abstract boolean verifyLogin(String username, String password);
	public abstract PeerList getRandomPeerList(int numberOfWantedPeers);

}
