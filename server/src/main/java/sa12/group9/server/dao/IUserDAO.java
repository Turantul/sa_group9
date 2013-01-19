package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.User;

public interface IUserDAO
{
    /**
     * TODO
     * 
     * @param user
     */
    void storeUser(User user);

    /**
     * TODO
     * 
     * @param users
     */
    void storeUser(List<User> users);

    /**
     * TODO
     * 
     * @param userName
     * @return
     */
    User searchUser(String userName);

    /**
     * TODO
     * 
     * @return
     */
    List<User> getAllUser();

    /**
     * TODO
     * 
     * @param user
     */
    void updateUser(User user);

    /**
     * TODO
     * 
     * @param userName
     */
    void deleteUser(String userName);
}
