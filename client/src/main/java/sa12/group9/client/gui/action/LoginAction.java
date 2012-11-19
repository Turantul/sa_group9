package sa12.group9.client.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.client.gui.misc.ActionCommands;
import sa12.group9.client.gui.swing.LoginFrame;
import sa12.group9.client.service.IServerHandler;

public class LoginAction implements ActionListener
{
    private static Log log = LogFactory.getLog(LoginAction.class);

    private LoginFrame frame;
    private MainAction actlist;
    private IServerHandler provider;

    public LoginAction(MainAction actlist, IServerHandler provider)
    {
        this.actlist = actlist;
        this.provider = provider;
        frame = new LoginFrame(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals(ActionCommands.LOGIN))
        {
            if (frame.checkInput())
            {
                try
                {
                    String id = provider.loginAtServer(frame.getUsername(), frame.getPassword());

                    if (id != null)
                    {
                        actlist.loginSuccessful(id);
                        frame.dispose();
                    }
                    else
                    {
                        frame.showError("Unknown username/password combination!");
                        frame.clearPW();
                    }
                }
                catch (Exception e2)
                {
                    log.error("There was an error connecting to the server");
                    frame.showError("The server could not be reached.\nPlease try again later.");
                }
            }
        }
    }
}