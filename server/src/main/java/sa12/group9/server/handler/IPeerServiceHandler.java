package sa12.group9.server.handler;

import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;

public interface IPeerServiceHandler
{
	/**
	 * Returns a Random List of Peers 
	 * 
	 * @param request the request for finding a random list of peers
	 * @return random subset
	 */
	PeerList getRandomPeerList(LoginRequest request);

	/**
	 * Marks the peer as alive
	 * 
	 * @param request the Request that marks a peer as alive
	 * @param remoteAddress the remoteaddress of the peer 
	 * @return whether the operation was successful
	 */
	boolean markAsAlive(IsAliveNotification request, String remoteAddress);
}
