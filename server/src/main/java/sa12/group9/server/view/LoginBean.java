package sa12.group9.server.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import sa12.group9.commons.dto.UserDTO;
import sa12.group9.server.dao.MongoUserDAO;
import sa12.group9.server.handlers.PeerServiceHandler;
import sa12.group9.server.handlers.IPeerServiceHandler;

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
        
		
		IPeerServiceHandler servicehandler = new PeerServiceHandler();
		
		if (servicehandler.verifyLogin(loginname, password)){
			return "success";
		}
		else{
			return "fail";
		}
		
//		IUserDAO userdao = MongoUserDAO.getInstance();
//		
//		UserDTO fetcheduser = userdao.searchUser(loginname);
//	
//		if(fetcheduser == null){
//			return "fail";
//		}
//		
//		
//		if(loginname.equals(fetcheduser.getUsername()) && 
//		password.equals(fetcheduser.getPassword())){
//			System.out.println("successfully logged in as " + fetcheduser.getUsername().toString() + "");
//					return "success";
//		}
//		else{
//			return "fail";
//		}
	}

}