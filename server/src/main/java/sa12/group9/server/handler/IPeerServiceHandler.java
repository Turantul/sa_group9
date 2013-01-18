package sa12.group9.server.handler;

import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.common.beans.PeerList;

public interface IPeerServiceHandler
{


	/**
	 * 
	 * 
	 * @param numberOfWantedPeers
	 * @return 
	 */
	PeerList getRandomPeerList(int numberOfWantedPeers);

	/**
	 * 
	 * @param request
	 * @return
	 */
	boolean verifyLogin(IsAliveNotification request);
}
