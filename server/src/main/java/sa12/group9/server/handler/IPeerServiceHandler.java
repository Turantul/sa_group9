package sa12.group9.server.handler;

import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;

public interface IPeerServiceHandler
{
	/**
	 * returns a Random List of Peers 
	 * 
	 * @param request
	 * @return
	 */
	PeerList getRandomPeerList(LoginRequest request);

	/**
	 * 
	 * Marks the peer as alive
	 * 
	 * @param request
	 * @param remoteAddress 
	 * @return
	 */
	boolean markAsAlive(IsAliveNotification request, String remoteAddress);
}
