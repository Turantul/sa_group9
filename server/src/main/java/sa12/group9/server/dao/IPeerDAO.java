package sa12.group9.server.dao;

import java.util.List;

import sa12.group9.common.beans.PeerEndpoint;



public interface IPeerDAO {

	public abstract void storePeer(PeerEndpoint peer);
	public abstract void storePeer(List<PeerEndpoint> peers);
	public abstract PeerEndpoint searchPeer(String address);
	public abstract List<PeerEndpoint> getAllPeers();
}
