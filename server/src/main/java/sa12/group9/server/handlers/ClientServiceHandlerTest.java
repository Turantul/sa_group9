package sa12.group9.server.handlers;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;

public class ClientServiceHandlerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IClientServiceHandler handler = new ClientServiceHandler();
		
		PeerList randompeers = handler.getRandomPeerList(2);
		
		for(PeerEndpoint p : randompeers.getPeers()){
			System.out.println(p.getAddress().toString());
		}

	}

}
