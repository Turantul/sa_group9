package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.server.dto.UserDTO;

public class daotest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		UserDTO user = new UserDTO();
		
		user.setUsername("hugo");
		user.setPassword("password");
		user.setCoins(20);
		
		
		IUserDAO userdao = MongoUserDAO.getInstance();
		
		userdao.storeUser(user);
		
		System.out.println(userdao.getAllUser());

	}

}
