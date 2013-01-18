package sa12.group9.server.handlers;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;

public class PeerServiceHandlerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPeerServiceHandler handler = new PeerServiceHandler();
		
		PeerList randompeers = handler.getRandomPeerList(2);
		
		for(PeerEndpoint p : randompeers.getPeers()){
			System.out.println(p.getAddress().toString());
		}

	}

}
