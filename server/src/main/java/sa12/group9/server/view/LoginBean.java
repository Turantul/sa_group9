package sa12.group9.server.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.CoinHistory;
import sa12.group9.common.beans.Request;
import sa12.group9.common.beans.User;
import sa12.group9.common.util.Encrypter;
import sa12.group9.server.dao.ICoinHistoryDAO;
import sa12.group9.server.dao.IRequestDAO;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoCoinHistoryDAO;
import sa12.group9.server.dao.MongoRequestDAO;
import sa12.group9.server.dao.MongoUserDAO;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable
{
    private static Log log = LogFactory.getLog(LoginBean.class);

    private IUserDAO usersdao = MongoUserDAO.getInstance(); 
    private IRequestDAO requestdao = MongoRequestDAO.getInstance();
    private ICoinHistoryDAO coinhistorydao = MongoCoinHistoryDAO.getInstance();
    
    
    private String loginname;
    private String password;
    private String retypePassword;
    private List<Request> requestsforuser;
    private List<CoinHistory> coinhistoryforuser;



	public int getCoins()
    {
        try
        {
            User loggedinuser = usersdao.searchUser(loginname);

            return loggedinuser.getCoins();
        }
        catch (Exception e)
        {
            log.error("Could not get coins for user " + loginname);
            return -1;
        }
    }

    public String getLoginname()
    {
        return loginname;
    }

    public void setLoginname(String loginname)
    {
        this.loginname = loginname;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRetypePassword()
    {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword)
    {
        this.retypePassword = retypePassword;
    }

    public List<Request> getRequestsforuser()
    {
        return requestsforuser;
    }

    public void setRequestsforuser(List<Request> requestsforuser)
    {
        this.requestsforuser = requestsforuser;
    }
    
    public List<CoinHistory> getCoinhistoryforuser() {
		return coinhistoryforuser;
	}

	public void setCoinhistoryforuser(List<CoinHistory> coinhistoryforuser) {
		this.coinhistoryforuser = coinhistoryforuser;
	}

    public String CheckValidUser()
    {

        User fetcheduser = usersdao.searchUser(loginname);

        if (fetcheduser == null)
        {
            return "fail";
        }

        if (loginname.equals(fetcheduser.getUsername()) && Encrypter.encryptString(password).equals(fetcheduser.getPassword()))
        {
            log.debug("Successfully logged in as " + fetcheduser.getUsername());
            return "success";
        }
        else
        {
            return "fail";
        }
    }

    public String updateUser()
    {
        try
        {
            User registeredUser = new User();

            registeredUser = usersdao.searchUser(loginname);
            registeredUser.setUsername(loginname);
            registeredUser.setPassword(Encrypter.encryptString(password));

            usersdao.storeUser(registeredUser);

            log.info("Successfully updated user " + loginname);

            return "editsuccess";

        }
        catch (Exception e)
        {
            log.error("Failed to update user " + loginname);
            return "editfail";
        }
    }

    public String deleteUser()
    {
        try
        {
            usersdao.deleteUser(loginname);

            log.info("Successfully deleted user " + loginname);

            return "deletesuccess";
        }
        catch (Exception e)
        {
            log.error("Error deleting user " + loginname);
            return "deletefail";
        }
    }

    public String GetRequestsForUser()
    {
        try
        {
            requestsforuser = new ArrayList<Request>();
            List<Request> allrequests = requestdao.getAllRequests();

            for (Request request : allrequests)
            {
                if (request.getUsername().toString().equals(loginname))
                {
                    requestsforuser.add(request);
                }
            }

            return "success";

        }
        catch (Exception e)
        {
            log.info("problem with fetching requests for user, could not get all requests for a user");
            return "fail";
        }
    }
    
    public String GetCoinHistoryForUser()
    {
        try
        {
        	coinhistoryforuser = new ArrayList<CoinHistory>();
            List<CoinHistory> allcoinhistories = coinhistorydao.getAllCoinHistories();

            for (CoinHistory coinhistory : allcoinhistories)
            {
                if (coinhistory.getUsername().toString().equals(loginname))
                {
                	coinhistoryforuser.add(coinhistory);
                }
            }

            return "success";

        }
        catch (Exception e)
        {
            log.info("problem with fetching coinhistory for user, could not get all requests for a user");
            return "fail";
        }
    }
}