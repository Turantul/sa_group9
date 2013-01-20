package sa12.group9.server.view;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.common.beans.User;
import sa12.group9.common.util.Encrypter;
import sa12.group9.server.dao.IUserDAO;
import sa12.group9.server.dao.MongoUserDAO;
import sa12.group9.server.util.PropertiesHelper;

@ManagedBean
@RequestScoped
public class RegisterBean implements Serializable
{
    private static Log log = LogFactory.getLog(RegisterBean.class);
    private IUserDAO userdao = MongoUserDAO.getInstance();

    private String loginname;
    private String password;
    private String retypePassword;

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

    public String registerUser()
    {

        if (checkPasswordMatch())
        {
            User newlyRegisteredUser = new User();

            newlyRegisteredUser.setUsername(loginname);
            newlyRegisteredUser.setPassword(Encrypter.encryptString(password));
            int initialCoins = 20;
            try
            {
                initialCoins = Integer.parseInt(PropertiesHelper.getProperty("initialCoins"));
            }
            catch (IOException e)
            {
                log.info("Failed to read properties file");
            }
            newlyRegisteredUser.setCoins(initialCoins);
            userdao.storeUser(newlyRegisteredUser);

            log.info("Successfully registered new user " + loginname);

            return "registersuccess";
        }
        else
        {
            return "registerfail";
        }
    }

    private boolean checkPasswordMatch()
    {
        return password.equals(retypePassword);
    }
}
