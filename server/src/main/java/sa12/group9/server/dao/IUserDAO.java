package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.User;

public interface IUserDAO
{
    /**
     * 
     * stores a User in the database
     * 
     * @param user
     */
    void storeUser(User user);

    /**
     * 
     * stores a list of users in the database
     * 
     * @param users
     */
    void storeUser(List<User> users);

    /**
     * 
     * searches a user in the database by the username
     * 
     * @param userName
     * @return
     */
    User searchUser(String userName);

    /**
     * 
     * returns all Users as a List of Users
     * 
     * @return
     */
    List<User> getAllUser();

    /**
     * 
     * updates a User
     * 
     * @param user
     */
    void updateUser(User user);

    /**
     * 
     * deletes a user
     * 
     * @param userName
     */
    void deleteUser(String userName);
}
