package sa12.group9.server.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import sa12.group9.common.beans.Request;
import sa12.group9.common.beans.SearchIssueRequest;
import sa12.group9.common.beans.User;
import sa12.group9.common.util.Encrypter;

public class MongoRequestDAO implements IRequestDAO
{
    private static MongoRequestDAO instance = new MongoRequestDAO();

    private MongoOperations mongoOperation;

    private MongoRequestDAO()
    {
        super();
        ApplicationContext ctx = new GenericXmlApplicationContext("mongo-config.xml");
        mongoOperation = (MongoOperations) ctx.getBean("mongoOperation");
    }

    public static MongoRequestDAO getInstance()
    {
        return instance;
    }


	@Override
	public void storeRequest(Request request) {
		mongoOperation.save(request, "requests");
		
	}

	@Override
	public void storeRequests(List<Request> requests) {
		mongoOperation.save(requests, "requests");
		
	}

	@Override
	public Request searchRequestByUsername(String username) {
		 return mongoOperation.findOne(new Query(Criteria.where("username").is(username)), Request.class, "requests");
	}

	@Override
	public List<Request> getAllRequests() {
	       return mongoOperation.findAll(Request.class, "requests");
	}

	@Override
	public long getCountOfRequestsForUser(String username) {
		return mongoOperation.count(new Query(Criteria.where("username").is(username)), "requests");
	}

	@Override
	public void updateRequest(Request request) {
	 
		mongoOperation.updateFirst(new Query(Criteria.where("_id").is(request.getUsername())), Update.update("finisheddate", request.getFinishedDate()) , Request.class);
		mongoOperation.updateFirst(new Query(Criteria.where("_id").is(request.getUsername())), Update.update("status", request.getStatus()) , Request.class);
		
	}

}
