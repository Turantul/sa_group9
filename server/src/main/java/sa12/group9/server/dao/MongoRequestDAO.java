package sa12.group9.server.dao;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import sa12.group9.common.beans.Request;

public class MongoRequestDAO implements IRequestDAO
{
    private static MongoRequestDAO instance = new MongoRequestDAO();

    private MongoOperations mongoOperation;

    private MongoRequestDAO()
    {
        super();
        mongoOperation = (MongoOperations) new GenericXmlApplicationContext("mongo-config.xml").getBean("mongoOperation");
    }

    public static MongoRequestDAO getInstance()
    {
        return instance;
    }

    @Override
    public void storeRequest(Request request)
    {
        mongoOperation.save(request, "requests");
    }

    @Override
    public void storeRequests(List<Request> requests)
    {
        mongoOperation.save(requests, "requests");
    }

    @Override
    public Request searchRequestByUsername(String username)
    {
        return mongoOperation.findOne(new Query(Criteria.where("username").is(username)), Request.class, "requests");
    }

    @Override
    public Request searchRequestById(String Id)
    {
        return mongoOperation.findOne(new Query(Criteria.where("_id").is(Id)), Request.class, "requests");
    }

    @Override
    public List<Request> getAllRequests()
    {
        return mongoOperation.findAll(Request.class, "requests");
    }

    @Override
    public long getCountOfRequestsForUser(String username)
    {
        return mongoOperation.count(new Query(Criteria.where("username").is(username)), "requests");
    }

    @Override
    public void updateRequest(Request request)
    {
        mongoOperation.updateFirst(new Query(Criteria.where("_id").is(request.getId())), Update.update("finisheddate", request.getFinisheddate()), Request.class);
        mongoOperation.updateFirst(new Query(Criteria.where("_id").is(request.getId())), Update.update("status", request.getStatus()), Request.class);
        mongoOperation.updateFirst(new Query(Criteria.where("_id").is(request.getId())), Update.update("foundbyuser", request.getFoundbyuser()), Request.class);
        mongoOperation.updateFirst(new Query(Criteria.where("_id").is(request.getId())), Update.update("interpret", request.getInterpret()), Request.class);
        mongoOperation.updateFirst(new Query(Criteria.where("_id").is(request.getId())), Update.update("title", request.getTitle()), Request.class);
    }

    @Override
    public void cleanupRequests()
    {
        mongoOperation.updateMulti(new Query(Criteria.where("status").is("pending").and("finisheddate").lt(new Date(System.currentTimeMillis()))),
                new Update().set("status", "failed"), "requests");
    }
}
