package sa12.group9.server.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import sa12.group9.commons.dto.UserDTO;
import sa12.group9.server.dao.IPeerDAO;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoPeerDAO;
import sa12.group9.server.dao.MongoUserDAO;


@ManagedBean
@RequestScoped
public class LoginBean{
	String loginname;
	String password;

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
		password.equals(fetcheduser.getPassword())){
			System.out.println("successfully logged in as " + fetcheduser.getUsername().toString() + "");
					return "success";
		}
		else{
			return "fail";
		}
	}
}