package sa12.group9.server.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import sa12.group9.common.beans.User;
import sa12.group9.common.util.Encrypter;
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
		  User newlyRegisteredUser = new User();
		  
		  newlyRegisteredUser.setUsername(loginname);
		  
				
		  newlyRegisteredUser.setPassword(Encrypter.encryptString(password));
				
			
		  newlyRegisteredUser.setCoins(20);
		
		  userdao.storeUser(newlyRegisteredUser);
		  
		  return "registersuccess";
	  }else{
		  return "registerfail";
	  }
  }
  
  public String UpdateUser() {
	  
	  try{
		  User registeredUser = new User();
		  IUserDAO userdao = MongoUserDAO.getInstance();
		  
		  registeredUser = userdao.searchUser(loginname);
		  registeredUser.setUsername(loginname);
		  registeredUser.setPassword(Encrypter.encryptString(password));
		  
		  userdao.storeUser(registeredUser);
		  
		  return "editsuccess";
		  
	  }catch (Exception e) {
		e.printStackTrace();
		return "editfail";
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
