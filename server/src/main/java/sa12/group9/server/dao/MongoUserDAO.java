package sa12.group9.server.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import sa12.group9.common.beans.User;


public class MongoUserDAO implements IUserDAO{

	private static MongoUserDAO instance = new MongoUserDAO();

	private MongoOperations mongoOperation;

	private MongoUserDAO(){
		super();
		ApplicationContext ctx = new GenericXmlApplicationContext("mongo-config.xml");
		mongoOperation = (MongoOperations)ctx.getBean("mongoOperation");
	}

	public static MongoUserDAO getInstance() {
		return instance;
	}

	public void storeUser(User user) {
		mongoOperation.save(user, "users");
	}

	public void storeUser(List<User> users) {
		mongoOperation.save(users, "users");
	}

	public User searchUser(String userName) {
		return mongoOperation.findOne(new Query(Criteria.where("username").is(userName)),User.class, "users");		
	}

	@Override
	public List<User> getAllUser() {
		return mongoOperation.findAll(User.class, "users");
	}

}