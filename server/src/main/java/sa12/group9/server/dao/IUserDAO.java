package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.server.dto.UserDTO;


public interface IUserDAO {
	public abstract void storeUser(UserDTO user);
	public abstract void storeUser(List<UserDTO> users);
	public abstract UserDTO searchUser(String userName);
	public abstract List<UserDTO> getAllUser();

}
