package sa12.group9.server.dao;

import java.util.Date;
import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import sa12.group9.common.beans.PeerEndpoint;

public class MongoPeerDAO implements IPeerDAO
{
    private static MongoPeerDAO instance = new MongoPeerDAO();

    private MongoOperations mongoOperation;

    private MongoPeerDAO()
    {
        super();
        mongoOperation = (MongoOperations) new GenericXmlApplicationContext("mongo-config.xml").getBean("mongoOperation");
    }

    public static MongoPeerDAO getInstance()
    {
        return instance;
    }

    @Override
    public void storePeer(PeerEndpoint peer)
    {
        mongoOperation.save(peer, "peers");
    }

    @Override
    public void storePeers(List<PeerEndpoint> peers)
    {
        mongoOperation.save(peers, "peers");
    }

    @Override
    public PeerEndpoint searchPeer(String address)
    {
        return mongoOperation.findOne(new Query(Criteria.where("address").is(address)), PeerEndpoint.class, "peers");
    }

    @Override
    public List<PeerEndpoint> getAllPeers()
    {
        return mongoOperation.findAll(PeerEndpoint.class, "peers");
    }

    @Override
    public long getCountOfPeers()
    {
        return mongoOperation.count(new Query(), "peers");
    }

    @Override
    public PeerEndpoint getPeer(String address, int listeningPort, int keepAlivePort)
    {
        return mongoOperation.findOne(new Query(Criteria.where("address").is(address).and("listeningPort").is(listeningPort).and("keepAlivePort").is(keepAlivePort)),
                PeerEndpoint.class, "peers");
    }

    @Override
    public void cleanupPeers(int cleanupPeriod)
    {
        mongoOperation.remove(new Query(Criteria.where("lastKeepAlive").lt(new Date(System.currentTimeMillis() - cleanupPeriod))), "peers");
    }
}
