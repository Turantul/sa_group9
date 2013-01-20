package sa12.group9.server.dao;

import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import sa12.group9.common.beans.CoinHistory;

public class MongoCoinHistoryDAO implements ICoinHistoryDAO
{
    private static MongoCoinHistoryDAO instance = new MongoCoinHistoryDAO();

    private MongoOperations mongoOperation;

    private MongoCoinHistoryDAO()
    {
        super();
        mongoOperation = (MongoOperations) new GenericXmlApplicationContext("mongo-config.xml").getBean("mongoOperation");
    }

    public static MongoCoinHistoryDAO getInstance()
    {
        return instance;
    }

    @Override
    public void storeCoinHistory(CoinHistory coinhistory)
    {
        mongoOperation.save(coinhistory, "coinhistory");
    }

    @Override
    public void storeCoinHistory(List<CoinHistory> coinhistories)
    {
        mongoOperation.save(coinhistories, "coinhistory");
    }

    @Override
    public CoinHistory searchCoinHistoryByUsername(String username)
    {
        return mongoOperation.findOne(new Query(Criteria.where("username").is(username)), CoinHistory.class, "coinhistory");
    }

    @Override
    public List<CoinHistory> getAllCoinHistories()
    {
        return mongoOperation.findAll(CoinHistory.class, "coinhistory");
    }
}
