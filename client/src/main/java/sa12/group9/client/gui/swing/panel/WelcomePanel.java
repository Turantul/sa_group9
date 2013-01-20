package sa12.group9.client.gui.swing.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import sa12.group9.client.gui.misc.ActionCommands;

public class WelcomePanel extends AbstractWorkingPanel
{
    public WelcomePanel(ActionListener actlist)
    {
        super(actlist);
    }

    @Override
    protected void draw()
    {
        JLabel loading = new JLabel("Welcome at SWAzam. Please log in.");
        loading.setFont(new Font("Arial", Font.BOLD, 40));
        loading.setForeground(Color.WHITE);
        add(loading, "gaptop 100, align center, wrap");

        JButton button = new JButton("Login");
        button.setActionCommand(ActionCommands.LOGIN);
        button.setPreferredSize(new Dimension(125, 30));
        button.addActionListener((ActionListener) actlist);
        add(button, "align center, push");
    }
}
