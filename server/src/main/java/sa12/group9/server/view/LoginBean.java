package sa12.group9.server.view;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import sa12.group9.common.util.Encrypter;
import sa12.group9.commons.dto.UserDTO;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoUserDAO;

@ManagedBean
@RequestScoped
public class LoginBean{
	String loginname;
	String password;
	int coins;

	public int getCoins() {
		
		try {
			MongoUserDAO usersdao = MongoUserDAO.getInstance();
			
			UserDTO loggedinuser = usersdao.searchUser(loginname);
			
			return loggedinuser.getCoins();
		} catch (Exception e) {
			System.out.println(e.getMessage() + "could not get coins");
			return 0;
		}
		
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public LoginBean(){}

	public String getLoginname(){
		return loginname;
	}

	public void setLoginname(String loginname){
		this.loginname = loginname;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String CheckValidUser(){

		IUserDAO userdao = MongoUserDAO.getInstance();
		
		UserDTO fetcheduser = userdao.searchUser(loginname);
	
		if(fetcheduser == null){
			return "fail";
		}
	
		
		if(loginname.equals(fetcheduser.getUsername()) && 
		Encrypter.encryptString(password).equals(fetcheduser.getPassword())){
			System.out.println("successfully logged in as " + fetcheduser.getUsername().toString() + "");
					return "success";
		}
		else{
			return "fail";
		}	
	}

}