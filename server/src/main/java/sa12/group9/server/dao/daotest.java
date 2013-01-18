package sa12.group9.server.dao;

import java.util.UUID;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.common.beans.User;



public class daotest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//testpeerdao();
		//testuserdao();
		//removeuser();
		updateuser();
	}
	
	public static void updateuser(){
		IUserDAO userdao = MongoUserDAO.getInstance();
		User affe = new User();
		affe.setUsername("affe");
		affe.setPassword("herbert1234566");
		
		userdao.updateUser(affe);
		
	}
	
	public static void removeuser(){
		IUserDAO userdao = MongoUserDAO.getInstance();
		userdao.deleteUser("smoun");
		
	}
	
	public static void testuserdao(){
		User user = new User();
		
		user.setUsername("hugo");
		user.setPassword("password");
		user.setCoins(20);
		
		
		IUserDAO userdao = MongoUserDAO.getInstance();
		
		userdao.storeUser(user);
		
		System.out.println(userdao.searchUser("hugo").getUsername());
	}
	
	public static void testpeerdao(){
		
		PeerEndpoint peer = new PeerEndpoint();
		peer.setUuid(UUID.randomUUID().toString());
		peer.setAddress("10.0.0.1");
		peer.setKeepAlivePort(4444);
		peer.setListeningPort(3333);
		
		PeerEndpoint peer2 = new PeerEndpoint();
		peer2.setUuid(UUID.randomUUID().toString());

		peer2.setAddress("10.0.0.2");
		
		PeerEndpoint peer3 = new PeerEndpoint();
		peer3.setUuid(UUID.randomUUID().toString());

		peer3.setAddress("10.0.0.3");
		
		PeerEndpoint peer4 = new PeerEndpoint();
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
