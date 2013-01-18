package sa12.group9.server.view;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import sa12.group9.commons.dto.UserDTO;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoUserDAO;

@ManagedBean
@RequestScoped
public class RegisterBean{
  String loginname;
  String password;
  String retypePassword;

  public RegisterBean(){}

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

  public String getRetypePassword() {
		return retypePassword;
  }
	
  public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
  }
  
  public String RegisterUser(){
	  
	  IUserDAO userdao = MongoUserDAO.getInstance();
	  
	  if(checkPasswordMatch()){
		  UserDTO newlyRegisteredUser = new UserDTO();
		  
		  newlyRegisteredUser.setUsername(loginname);
		  
			try {
				
				MessageDigest mdEnc = MessageDigest.getInstance("MD5");
				mdEnc.update(password.getBytes(), 0, password.length());
				String md5sum = new BigInteger(1, mdEnc.digest()).toString(16); // Encrypted string
				
				newlyRegisteredUser.setPassword(md5sum);
				
			}catch (Exception e) {
				return "registerfail";
			}
		  newlyRegisteredUser.setCoins(20);
		
		  userdao.storeUser(newlyRegisteredUser);
		  
		  return "registersuccess";
	  }else{
		  return "registerfail";
	  }
  }
  private Boolean checkPasswordMatch(){
	 
	  if(password.equals(retypePassword)){
		  return true;  
	  }
	  else{
		  return false;
	  }
	  
	  
  }



}
