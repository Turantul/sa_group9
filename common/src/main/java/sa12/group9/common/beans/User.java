package sa12.group9.common.beans;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="users")
public class User
{ 
	@Id
    private String username;
    private String password;
    private Integer coins;
    
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getCoins() {
		return coins;
	}
	public void setCoins(Integer coins) {
		this.coins = coins;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", coins=" + coins + "]";
	}

    
  
}