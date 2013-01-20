package sa12.group9.peer.dao;

import java.sql.SQLException;
import java.util.Map;

import sa12.group9.common.beans.SongMetadata;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public interface IFingerprintDAO
{
    /**
     * Persists a Fingerprint in the database
     * 
     * @param username
     * @param the metadata object for the Fingerprint
     * @param the Fingerprint
     * @throws SQLException if there occurs a database error
     */
    void persistFingerprint(String user, SongMetadata smd, Fingerprint fp) throws SQLException;

    /**
     * Deletes a Fingerprint from the database
     * 
     * @param username
     * @param the metadata object for the Fingerprint
     * @throws SQLException if there occurs a database error
     */
    void deleteFingerprint(Long id, String user) throws SQLException;

    /**
     * Retrieves a Fingerprint associated with username and metadata from the
     * database
     * 
     * @param username
     * @param the metadata object for the Fingerprint
     * @return the Fingerprint
     * @throws SQLException if there occurs a database error
     */
    Fingerprint findFingerprintByUserAndMetadata(String user, SongMetadata smd) throws SQLException;

    /**
     * Retrieves metadata for a user and associated Fingerprint
     * 
     * @param username
     * @param the Fingerprint
     * @return the metadata object for the Fingerprint
     * @throws SQLException if there occurs a database error
     */
    SongMetadata getSongForUserAndFingerprint(String user, Fingerprint fp) throws SQLException;

    /**
     * Retrieves a list of Fingerprints for a user
     * 
     * @param username
     * @return the list of Fingerprints
     * @throws SQLException if there occurs a database error
     */
    Map<Long, Object[]> getAllFingerprintsForUser(String user) throws SQLException;
}
