package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.User;

public interface IUserDAO
{
    /**
     * 
     * stores a User in the database
     * 
     * @param the userobject that gets stored in the db
     */
    void storeUser(User user);

    /**
     * 
     * stores a list of users in the database
     * 
     * @param a list of users to get stored in the db
     */
    void storeUser(List<User> users);

    /**
     * 
     * searches a user in the database by the username
     * 
     * @param the searchparameter to find a user
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
     * @param the user that gets updated
     */
    void updateUser(User user);

    /**
     * 
     * deletes a user
     * 
     * @param the name of the user that gets deleted
     */
    void deleteUser(String userName);
}
