package sa12.group9.server.handler;

import sa12.group9.common.beans.IsAliveNotification;
import sa12.group9.common.beans.LoginRequest;
import sa12.group9.common.beans.PeerList;

public interface IPeerServiceHandler
{
	/**
	 * returns a Random List of Peers 
	 * 
	 * @param the request for finding a random list of peers
	 * @return
	 */
	PeerList getRandomPeerList(LoginRequest request);

	/**
	 * 
	 * Marks the peer as alive
	 * 
	 * @param the Request that marks a peer as alive
	 * @param the remoteaddress of the peer 
	 * @return
	 */
	boolean markAsAlive(IsAliveNotification request, String remoteAddress);
}
