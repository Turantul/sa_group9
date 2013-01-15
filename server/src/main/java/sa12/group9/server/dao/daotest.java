package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.commons.dto.UserDTO;



public class daotest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		testpeerdao();
	}
	
	public static void testuserdao(){
		UserDTO user = new UserDTO();
		
		user.setUsername("hugo");
		user.setPassword("password");
		user.setCoins(20);
		
		
		IUserDAO userdao = MongoUserDAO.getInstance();
		
		//userdao.storeUser(user);
		
		System.out.println(userdao.searchUser("hugo").getUsername());
	}
	
	public static void testpeerdao(){
		
		PeerEndpoint peer = new PeerEndpoint();
	
		peer.setAddress("10.0.0.1");
		peer.setKeepAlivePort(4444);
		peer.setListeningPort(3333);
		
		IPeerDAO peerdao = MongoPeerDAO.getInstance();
		
		//peerdao.storePeer(peer);
		
		
		System.out.println(peerdao.searchPeer("10.0.0.1").getKeepAlivePort());
	}

}
