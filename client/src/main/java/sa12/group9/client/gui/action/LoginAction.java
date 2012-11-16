package sa12.group9.client.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sa12.group9.client.gui.misc.ActionCommands;
import sa12.group9.client.gui.swing.LoginFrame;
import sa12.group9.client.service.ServiceProvider;

public class LoginAction implements ActionListener
{
    private LoginFrame frame;
    private MainAction actlist;

    public LoginAction(MainAction actlist)
    {
        this.actlist = actlist;
        frame = new LoginFrame(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals(ActionCommands.LOGIN))
        {
            if (frame.checkInput())
            {
                String id = ServiceProvider.loginAtServer(frame.getUsername(), frame.getPassword());

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
        }
    }
}