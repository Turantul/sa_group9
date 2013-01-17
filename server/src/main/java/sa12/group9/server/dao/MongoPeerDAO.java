package sa12.group9.server.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.commons.dto.UserDTO;

public class MongoPeerDAO implements IPeerDAO {
	private static MongoPeerDAO instance = new MongoPeerDAO();

	private MongoOperations mongoOperation;

	private MongoPeerDAO() {
		super();
		ApplicationContext ctx = new GenericXmlApplicationContext("mongo-config.xml");
		mongoOperation = (MongoOperations)ctx.getBean("mongoOperation");
	}
	
	public static MongoPeerDAO getInstance() {
		return instance;
	}
		
	
	@Override
	public void storePeer(PeerEndpoint peer) {
		mongoOperation.save(peer, "peers");
		
	}

	@Override
	public void storePeer(List<PeerEndpoint> peers) {
		mongoOperation.save(peers, "peers");
		
	}

	@Override
	public PeerEndpoint searchPeer(String address) {
		return mongoOperation.findOne(new Query(Criteria.where("address").is(address)),PeerEndpoint.class, "peers");
	}

	@Override
	public List<PeerEndpoint> getAllPeers() {
		return mongoOperation.findAll(PeerEndpoint.class, "peers");
		
	}

}