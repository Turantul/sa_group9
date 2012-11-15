package sa12.group9.client.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sa12.group9.client.gui.misc.ActionCommands;
import sa12.group9.client.gui.swing.LoginFrame;

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
                //TODO: login at server using frame.getUsername() and frame.getPassword()

                if (true)
                {
                    actlist.loginSuccessful();
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