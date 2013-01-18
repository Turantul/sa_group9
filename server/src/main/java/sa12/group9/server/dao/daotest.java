package sa12.group9.server.dao;

import java.util.List;
import java.util.UUID;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.commons.dto.PeerDTO;
import sa12.group9.commons.dto.UserDTO;



public class daotest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		testpeerdao();
		//testuserdao();
	}
	
	public static void testuserdao(){
		UserDTO user = new UserDTO();
		
		user.setUsername("hugo");
		user.setPassword("password");
		user.setCoins(20);
		
		
		IUserDAO userdao = MongoUserDAO.getInstance();
		
		userdao.storeUser(user);
		
		System.out.println(userdao.searchUser("hugo").getUsername());
	}
	
	public static void testpeerdao(){
		
		PeerDTO peer = new PeerDTO();
		peer.setUuid(UUID.randomUUID().toString());
		peer.setAddress("10.0.0.1");
		peer.setKeepAlivePort(4444);
		peer.setListeningPort(3333);
		
		PeerDTO peer2 = new PeerDTO();
		peer2.setUuid(UUID.randomUUID().toString());

		peer2.setAddress("10.0.0.2");
		
		PeerDTO peer3 = new PeerDTO();
		peer3.setUuid(UUID.randomUUID().toString());

		peer3.setAddress("10.0.0.3");
		
		PeerDTO peer4 = new PeerDTO();
		peer4.setUuid(UUID.randomUUID().toString());

		peer4.setAddress("10.0.0.4");
		
		
		IPeerDAO peerdao = MongoPeerDAO.getInstance();
		
		peerdao.storePeer(peer);
		peerdao.storePeer(peer2);
		peerdao.storePeer(peer3);
		peerdao.storePeer(peer4);
		
		System.out.println(peerdao.searchPeer("10.0.0.1").getKeepAlivePort());
	}

}
