package sa12.group9.client.gui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import sa12.group9.client.gui.action.LoginAction;
import sa12.group9.client.gui.misc.ActionCommands;

public class LoginFrame extends JFrame
{
    private LoginAction actlist;

    private JTextField username = null;
    private JPasswordField password = null;

    public LoginFrame(LoginAction actlist)
    {
        this.actlist = actlist;
        initialize();
    }

    private void initialize()
    {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(300, 150);
        setResizable(false);
        setLocation(450, 300);
        setTitle("Login");

        setContentPane(drawContent());

        setVisible(true);
    }

    private JPanel drawContent()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("insets 20"));
        panel.setBackground(new Color(175, 200, 225));

        /*
         * Creation of username field
         */
        JLabel lbl_username = new JLabel("Username:");
        lbl_username.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl_username, "gapright 10");
        username = new JTextField();
        username.setPreferredSize(new Dimension(200, 20));
        panel.add(username, "wrap");

        /*
         * Creation of password field
         */
        JLabel lbl_password = new JLabel("Password:");
        lbl_password.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(lbl_password);
        password = new JPasswordField();
        password.setPreferredSize(new Dimension(200, 20));
        password.setActionCommand(ActionCommands.LOGIN);
        password.addActionListener(actlist);
        panel.add(password, "wrap 20");

        /*
         * Creation of login button
         */
        JButton btn_login = new JButton("Login");
        btn_login.setPreferredSize(new Dimension(150, 30));
        btn_login.setActionCommand(ActionCommands.LOGIN);
        btn_login.addActionListener(actlist);
        panel.add(btn_login, "span 2, gapleft 50");

        return panel;
    }

    public boolean checkInput()
    {
        if (username.getText().equals(""))
        {
            showError("Please enter a username.");
            return false;
        }
        if (new String(password.getPassword()).equals(""))
        {
            showError("Please enter your password.");
            return false;
        }
        return true;
    }

    public String getUsername()
    {
        return username.getText();
    }

    public String getPassword()
    {
        return new String(password.getPassword());
    }

    public void showError(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void clearPW()
    {
        password.setText("");
    }
}