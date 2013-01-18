package sa12.group9.server.handler;

import sa12.group9.common.beans.IsAliveNotification;
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
	boolean verifyLogin(String username, String passwordrequest);

	/**
	 * 
	 * 
	 * @param numberOfWantedPeers
	 * @return 
	 */
	PeerList getRandomPeerList(int numberOfWantedPeers);

	boolean verifyLogin(IsAliveNotification request);
}
