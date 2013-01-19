package sa12.group9.peer.dao;

import java.sql.SQLException;
import java.util.List;

import sa12.group9.common.beans.SongMetadata;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public interface IFingerprintDao {
	
	public void persistFingerprint(String user, SongMetadata smd, Fingerprint fp) throws SQLException;
	
	public void deleteFingerprint(String user, SongMetadata smd) throws SQLException;
	
	public Fingerprint findFingerprintByUserAndMetadata(String user, SongMetadata smd) throws SQLException;
	
	public SongMetadata getSongForUserAndFingerprint(String user, Fingerprint fp) throws SQLException;
	
	public List<Fingerprint> getAllFingerprintsForUser(String user) throws SQLException;
}
