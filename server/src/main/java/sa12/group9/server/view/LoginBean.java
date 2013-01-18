package sa12.group9.server.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import sa12.group9.common.beans.User;
import sa12.group9.common.util.Encrypter;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoUserDAO;

@ManagedBean
@SessionScoped
public class LoginBean{
	String loginname;
	String password;
	String retypePassword;
	int coins;

	public int getCoins() {
		
		try {
			MongoUserDAO usersdao = MongoUserDAO.getInstance();
			
			User loggedinuser = usersdao.searchUser(loginname);
			
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
	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public String CheckValidUser(){

		IUserDAO userdao = MongoUserDAO.getInstance();
		
		User fetcheduser = userdao.searchUser(loginname);
	
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
	public String DeleteUser() {
		  
		  try{
			  IUserDAO userdao = MongoUserDAO.getInstance();
			  
			  
			  userdao.deleteUser(loginname);	  
			  return "deletesuccess";
			  
		  }catch (Exception e) {
			e.printStackTrace();
			return "deletefail";
		}
	}
	public String UpdateUser() {
		  
		  try{
			  
			  if(password.equals(retypePassword)){
				  IUserDAO userdao = MongoUserDAO.getInstance();
					
					
				  User updatedUser = new User();
				  
				  
				  updatedUser.setUsername(loginname);
				  updatedUser.setPassword(password);
				  
				  userdao.updateUser(updatedUser);
				  
				  
				  return "updatesuccess";
			  
			  }else{
				  return "updatefail";
			  }
			  	  
		  }catch (Exception e) {
			e.printStackTrace();
			return "updatefail";
		}
	}

	
		  
		 
}