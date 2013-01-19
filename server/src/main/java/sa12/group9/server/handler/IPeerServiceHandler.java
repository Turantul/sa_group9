package sa12.group9.server.handler;

import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;

public interface IPeerServiceHandler
{
	/**
	 * TODO
	 * 
	 * @param request
	 * @param numberOfWantedPeers
	 * @return
	 */
	PeerList getRandomPeerList(LoginRequest request, int numberOfWantedPeers);

	/**
	 * TODO
	 * 
	 * @param request
	 * @param string 
	 * @return
	 */
	boolean verifyLogin(IsAliveNotification request, String remoteAddress);
	
	/**
	 * TODO
	 * 
	 * @param request
	 * @param remoteAddress 
	 */
	void markAsAlive(IsAliveNotification request, String remoteAddress);
}
