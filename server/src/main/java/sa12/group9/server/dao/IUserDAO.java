package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.User;

public interface IUserDAO {
	public abstract void storeUser(User user);
	public abstract void storeUser(List<User> users);
	public abstract User searchUser(String userName);
	public abstract List<User> getAllUser();
	public abstract void updateUser(User user);
	public abstract void deleteUser(String userName);

}
