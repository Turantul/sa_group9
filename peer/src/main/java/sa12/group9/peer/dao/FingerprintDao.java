package sa12.group9.peer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;

import sa12.group9.common.beans.SongMetadata;
import sa12.group9.common.util.Constants;

public class FingerprintDao {
	private Connection con;
	
	public FingerprintDao()
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext(Constants.SPRINGBEANS);
		DriverManagerDataSource dataSource = (DriverManagerDataSource) ctx.getBean("H2DataSource");
		
		try {
			con = dataSource.getConnection();
			Statement stat = con.createStatement();
			stat.execute("CREATE TABLE IF NOT EXISTS fingerprint (id IDENTITY PRIMARY KEY, user VARCHAR, metadata OTHER, fingerprint OTHER);");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void persistFingerprint(String user, SongMetadata smd, Fingerprint fp) {
		try {
			PreparedStatement prep = con.prepareStatement("INSERT INTO fingerprint (user, metadata, fingerprint) values(?,?,?);");
			prep.setString(1, user);
			prep.setObject(2, smd);
			prep.setObject(3, fp);
			prep.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteFingerprint(String user, SongMetadata smd) {
		try {
			PreparedStatement prep = con.prepareStatement("DELETE FROM fingerprint WHERE user=? and metadata=?;");
			prep.setString(1, user);
			prep.setObject(2, smd);
			prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Fingerprint findFingerprintByUserLoc(String user, SongMetadata smd) {
		Fingerprint fp = null;
		
		try {
			PreparedStatement prep = con.prepareStatement("SELECT * FROM fingerprint WHERE user=? and metadata=?;");
			prep.setString(1, user);
			prep.setObject(2, smd);
			
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				fp = (Fingerprint) rs.getObject(3);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fp;
	}
}
