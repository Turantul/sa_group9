package sa12.group9.server.handler;

import sa12.group9.common.beans.PeerList;

public interface IPeerServiceHandler
{
	/**
	 * 
	 * 
	 * @param username
	 * @param password
	 * @return 
	 */
	boolean verifyLogin(String username, String password);

	/**
	 * 
	 * 
	 * @param numberOfWantedPeers
	 * @return 
	 */
	PeerList getRandomPeerList(int numberOfWantedPeers);
}
