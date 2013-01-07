package sa12.group9.server.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;


@ManagedBean
@RequestScoped
public class SimpleLogin{
	String loginname;
	String password;

	public SimpleLogin(){}

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
        if(loginname.equals("test") && 
password.equals("test")){
	System.out.println("chandan");
			return "success";
		}
		else{
			return "fail";
		}
	}
}