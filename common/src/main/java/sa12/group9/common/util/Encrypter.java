package sa12.group9.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {

	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Encrypter(String password){
		this.password = password;
	}
	public String getEncryptedPassword(){
		
		MessageDigest mdEnc;
		String md5sum = "";
		
		try {
			mdEnc = MessageDigest.getInstance("MD5");
			mdEnc.update(password.getBytes(), 0, password.length());
			md5sum = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return md5sum;

	}
}
