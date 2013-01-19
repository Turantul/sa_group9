package sa12.group9.server.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.User;
import sa12.group9.common.util.Encrypter;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoUserDAO;

@ManagedBean
@SessionScoped
public class LoginBean
{
    private static Log log = LogFactory.getLog(LoginBean.class);

    private String loginname;
    private String password;
    private String retypePassword;

    public int getCoins()
    {
        try
        {
            MongoUserDAO usersdao = MongoUserDAO.getInstance();

            User loggedinuser = usersdao.searchUser(loginname);

            return loggedinuser.getCoins();
        }
        catch (Exception e)
        {
            log.error("Could not get coins for user " + loginname);
            return 0;
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

    public String CheckValidUser()
    {
        IUserDAO userdao = MongoUserDAO.getInstance();

        User fetcheduser = userdao.searchUser(loginname);

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
            IUserDAO userdao = MongoUserDAO.getInstance();

            registeredUser = userdao.searchUser(loginname);
            registeredUser.setUsername(loginname);
            registeredUser.setPassword(Encrypter.encryptString(password));

            userdao.storeUser(registeredUser);

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
            IUserDAO userdao = MongoUserDAO.getInstance();

            userdao.deleteUser(loginname);
            
            log.info("Successfully deleted user " + loginname);
            
            return "deletesuccess";
        }
        catch (Exception e)
        {
            log.error("Error deleting user " + loginname);
            return "deletefail";
        }
    }
}