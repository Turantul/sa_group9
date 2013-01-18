package sa12.group9.server.handler;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.PeerList;
import sa12.group9.commons.dto.PeerDTO;

public class PeerServiceHandlerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IPeerServiceHandler handler = new PeerServiceHandler();
		
		PeerList randompeers = handler.getRandomPeerList(2);
		
		for(PeerDTO p : randompeers.getPeers()){
			System.out.println(p.getAddress().toString());
		}

	}

}
