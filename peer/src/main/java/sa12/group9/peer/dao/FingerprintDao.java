package sa12.group9.peer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.common.beans.SongMetadata;
import sa12.group9.common.util.Constants;

public class FingerprintDao implements IFingerprintDao {
	private Connection con;
	
	public FingerprintDao() throws SQLException
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext(Constants.SPRINGBEANS);
		DriverManagerDataSource dataSource = (DriverManagerDataSource) ctx.getBean("H2DataSource");
		
		con = dataSource.getConnection();
		Statement stat = con.createStatement();
		stat.execute("CREATE TABLE IF NOT EXISTS fingerprint (id IDENTITY PRIMARY KEY, user VARCHAR, metadata OTHER, fingerprint OTHER);");
	}
	
	public FingerprintDao(String jdbcUrl) throws SQLException
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext(Constants.SPRINGBEANS);
		DriverManagerDataSource dataSource = (DriverManagerDataSource) ctx.getBean("H2DataSource");
		dataSource.setUrl(jdbcUrl);
		
		con = dataSource.getConnection();
		Statement stat = con.createStatement();
		stat.execute("CREATE TABLE IF NOT EXISTS fingerprint (id IDENTITY PRIMARY KEY, user VARCHAR, metadata OTHER, fingerprint OTHER);");
	}
	
	public void persistFingerprint(String user, SongMetadata smd, Fingerprint fp) throws SQLException {
		PreparedStatement prep = con.prepareStatement("INSERT INTO fingerprint (user, metadata, fingerprint) values(?,?,?);");
		prep.setString(1, user);
		prep.setObject(2, smd);
		prep.setObject(3, fp);
		prep.execute();
	}
	
	public void deleteFingerprint(String user, SongMetadata smd) throws SQLException {
		PreparedStatement prep = con.prepareStatement("DELETE FROM fingerprint WHERE user=? and metadata=?;");
		prep.setString(1, user);
		prep.setObject(2, smd);
		prep.executeUpdate();
	}
	
	public Fingerprint findFingerprintByUserAndMetadata(String user, SongMetadata smd) throws SQLException {
		Fingerprint fp = null;
		
		PreparedStatement prep = con.prepareStatement("SELECT * FROM fingerprint WHERE user=? and metadata=?;");
		prep.setString(1, user);
		prep.setObject(2, smd);

		ResultSet rs = prep.executeQuery();
		if (rs.next()) {
			fp = (Fingerprint) rs.getObject(3);
		}

		return fp;
	}
	
	public SongMetadata getSongForUserAndFingerprint(String user, Fingerprint fp) throws SQLException {
		SongMetadata smd = null;
		
		PreparedStatement prep = con.prepareStatement("SELECT * FROM fingerprint WHERE user=? and fingerprint=?;");
		prep.setString(1, user);
		prep.setObject(2, fp);

		ResultSet rs = prep.executeQuery();
		if (rs.next()) {
			smd = (SongMetadata) rs.getObject(3);
		}
		
		return smd;
	}
	
	public List<Fingerprint> getAllFingerprintsForUser(String user) throws SQLException {
		List<Fingerprint> fingerprints = new ArrayList<Fingerprint>();
		
		PreparedStatement prep = con.prepareStatement("SELECT * FROM fingerprint WHERE user=?;");
		prep.setString(1, user);

		ResultSet rs = prep.executeQuery();
		while (rs.next()) {
			fingerprints.add((Fingerprint) rs.getObject(2));
		}
		
		return fingerprints;
	}
}
