package sa12.group9.server.dao;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import sa12.group9.common.beans.PeerEndpoint;
import sa12.group9.commons.dto.PeerDTO;
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
	public void storePeer(PeerDTO peer) {
		mongoOperation.save(peer, "peers");
		
	}

	@Override
	public void storePeer(List<PeerDTO> peers) {
		mongoOperation.save(peers, "peers");
		
	}

	@Override
	public PeerDTO searchPeer(String address) {
		return mongoOperation.findOne(new Query(Criteria.where("address").is(address)),PeerDTO.class, "peers");
	}

	@Override
	public List<PeerDTO> getAllPeers() {
		return mongoOperation.findAll(PeerDTO.class, "peers");
		
	}

}
