package sa12.group9.server.dao;

import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import sa12.group9.common.beans.User;
import sa12.group9.common.util.Encrypter;

public class MongoUserDAO implements IUserDAO
{

    private static MongoUserDAO instance = new MongoUserDAO();

    private MongoOperations mongoOperation;

    private MongoUserDAO()
    {
        super();
        mongoOperation = (MongoOperations) new GenericXmlApplicationContext("mongo-config.xml").getBean("mongoOperation");
    }

    public static MongoUserDAO getInstance()
    {
        return instance;
    }

    public void storeUser(User user)
    {
        mongoOperation.save(user, "users");
    }

    public void storeUser(List<User> users)
    {
        mongoOperation.save(users, "users");
    }

    public User searchUser(String userName)
    {
        return mongoOperation.findOne(new Query(Criteria.where("username").is(userName)), User.class, "users");
    }

    @Override
    public List<User> getAllUser()
    {
        return mongoOperation.findAll(User.class, "users");
    }

    @Override
    public void updateUser(User user)
    {
        mongoOperation.updateFirst(new Query(Criteria.where("_id").is(user.getUsername())), Update.update("password", user.getPassword()), User.class);
        mongoOperation.updateFirst(new Query(Criteria.where("_id").is(user.getUsername())), Update.update("coins", user.getCoins()), User.class);
    }

    @Override
    public void deleteUser(String userName)
    {
        mongoOperation.remove(new Query(Criteria.where("username").is(userName)), User.class);
    }
}