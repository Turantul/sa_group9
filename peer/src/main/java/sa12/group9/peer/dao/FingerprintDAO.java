package sa12.group9.peer.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import sa12.group9.common.beans.SongMetadata;
import ac.at.tuwien.infosys.swa.audio.Fingerprint;

public class FingerprintDAO implements IFingerprintDAO
{
    private DataSource dataSource;

    private String sql_create;
    private String sql_insert;
    private String sql_delete;
    private String sql_findFingerprintByUserAndMetadata;
    private String sql_getSongForUserAndFingerprint;
    private String sql_getAllFingerprintsForUser;

    @SuppressWarnings("unused")
    private void init() throws SQLException
    {
        Statement stat = dataSource.getConnection().createStatement();
        stat.execute(sql_create);
    }

    @Override
    public void persistFingerprint(String user, SongMetadata smd, Fingerprint fp) throws SQLException
    {
        PreparedStatement prep = dataSource.getConnection().prepareStatement(sql_insert);
        prep.setString(1, user);
        prep.setObject(2, smd);
        prep.setObject(3, fp);
        prep.execute();
    }

    public void deleteFingerprint(Long id, String user) throws SQLException
    {
        PreparedStatement prep = dataSource.getConnection().prepareStatement(sql_delete);
        prep.setLong(1, id);
        prep.setObject(2, user);
        prep.executeUpdate();
    }

    public Fingerprint findFingerprintByUserAndMetadata(String user, SongMetadata smd) throws SQLException
    {
        Fingerprint fp = null;

        PreparedStatement prep = dataSource.getConnection().prepareStatement(sql_findFingerprintByUserAndMetadata);
        prep.setString(1, user);
        prep.setObject(2, smd);

        ResultSet rs = prep.executeQuery();
        if (rs.next())
        {
            fp = (Fingerprint) rs.getObject(3);
        }

        return fp;
    }

    public SongMetadata getSongForUserAndFingerprint(String user, Fingerprint fp) throws SQLException
    {
        SongMetadata smd = null;

        PreparedStatement prep = dataSource.getConnection().prepareStatement(sql_getSongForUserAndFingerprint);
        prep.setString(1, user);
        prep.setObject(2, fp);

        ResultSet rs = prep.executeQuery();
        if (rs.next())
        {
            smd = (SongMetadata) rs.getObject(3);
        }

        return smd;
    }

    public Map<Long, Object[]> getAllFingerprintsForUser(String user) throws SQLException
    {
        Map<Long, Object[]> ret = new HashMap<Long, Object[]>();

        PreparedStatement prep = dataSource.getConnection().prepareStatement(sql_getAllFingerprintsForUser);
        prep.setString(1, user);

        ResultSet rs = prep.executeQuery();
        while (rs.next())
        {
            Object[] data = new Object[] { rs.getObject(3), rs.getObject(4) };
            ret.put((Long) rs.getObject(1), data);
        }

        return ret;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public void setSql_create(String sql_create)
    {
        this.sql_create = sql_create;
    }

    public void setSql_insert(String sql_insert)
    {
        this.sql_insert = sql_insert;
    }

    public void setSql_delete(String sql_delete)
    {
        this.sql_delete = sql_delete;
    }

    public void setSql_findFingerprintByUserAndMetadata(String sql_findFingerprintByUserAndMetadata)
    {
        this.sql_findFingerprintByUserAndMetadata = sql_findFingerprintByUserAndMetadata;
    }

    public void setSql_getSongForUserAndFingerprint(String sql_getSongForUserAndFingerprint)
    {
        this.sql_getSongForUserAndFingerprint = sql_getSongForUserAndFingerprint;
    }

    public void setSql_getAllFingerprintsForUser(String sql_getAllFingerprintsForUser)
    {
        this.sql_getAllFingerprintsForUser = sql_getAllFingerprintsForUser;
    }
}
