package sa12.group9.common.beans;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coinhistory")
public class CoinHistory

{ 
	@Id
    private UUID uuid;
	private String username;
	private Date changedate;
	private int coins;
	private String requestid;
    

}