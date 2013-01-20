package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.User;

public interface IUserDAO
{
    /**
     * Stores a User in the database
     * 
     * @param user the userobject that gets stored in the db
     */
    void storeUser(User user);

    /**
     * Stores a list of users in the database
     * 
     * @param users a list of users to get stored in the db
     */
    void storeUser(List<User> users);

    /**
     * Searches a user in the database by the username
     * 
     * @param userName the searchparameter to find a user
     * @return the user object
     */
    User searchUser(String userName);

    /**
     * Returns all Users as a List of Users
     * 
     * @return list of all users
     */
    List<User> getAllUser();

    /**
     * Updates a User
     * 
     * @param user the user that gets updated
     */
    void updateUser(User user);

    /**
     * Deletes a user
     * 
     * @param userName the name of the user that gets deleted
     */
    void deleteUser(String userName);
}
