package sa12.group9.client.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.client.gui.ActionCommands;
import sa12.group9.client.gui.swing.MainFrame;

public class MainAction implements ActionListener
{
    private static Log log = LogFactory.getLog(MainAction.class);

    private MainFrame frame;

    public MainAction()
    {
        log.debug("Creating MainAction");

        frame = new MainFrame(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals(ActionCommands.EXIT))
        {
            System.exit(0);
        }
        else if (e.getActionCommand().equals(ActionCommands.LOGIN))
        {
            new LoginAction(this);
        }
    }

    public void loginSuccessful()
    {
        log.info("User logged in");
        
        frame.userLoggedIn();
    }
}