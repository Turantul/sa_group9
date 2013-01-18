package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.commons.dto.PeerDTO;



public interface IPeerDAO {

	public abstract void storePeer(PeerDTO peer);
	public abstract void storePeer(List<PeerDTO> peers);
	public abstract PeerDTO searchPeer(String address);
	public abstract List<PeerDTO> getAllPeers();
}
