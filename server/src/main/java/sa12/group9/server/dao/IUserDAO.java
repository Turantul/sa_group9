package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.User;

public interface IUserDAO
{
    /**
     * 
     * @param user
     */
    void storeUser(User user);

    /**
     * 
     * @param users
     */
    void storeUser(List<User> users);

    /**
     * 
     * @param userName
     * @return
     */
    User searchUser(String userName);

    /**
     * 
     * @return
     */
    List<User> getAllUser();

    /**
     * 
     * @param user
     */
    void updateUser(User user);

    /**
     * 
     * @param userName
     */
    void deleteUser(String userName);
}
