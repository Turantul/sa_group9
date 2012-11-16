package sa12.group9.client.gui.swing.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import sa12.group9.client.gui.misc.ActionCommands;

public class MainPanel extends AbstractWorkingPanel
{
    private JTextField file;
    
    public MainPanel(ActionListener actlist)
    {
        super(actlist);
    }
    
    public MainPanel(ActionListener actlist, String fileName)
    {
        super(actlist);
        file.setText(fileName);
    }

    @Override
    protected void draw()
    {
        JLabel welcome = new JLabel("Welcome at SWAzam!");
        welcome.setFont(new Font("Arial", Font.BOLD, 40));
        welcome.setForeground(Color.WHITE);
        add(welcome, "gaptop 75, align center, span, wrap 10");

        JLabel desc = new JLabel("Please select an audio file from your file system to issue a search request.");
        desc.setFont(new Font("Arial", Font.BOLD, 14));
        desc.setForeground(Color.WHITE);
        add(desc, "align center, span, wrap 100");

        file = new JTextField();
        file.setEnabled(false);
        file.setPreferredSize(new Dimension(500, 20));
        add(file, "gapleft 50");

        JButton fileButton = new JButton("Select file");
        fileButton.setActionCommand(ActionCommands.CHOOSEFILE);
        fileButton.setPreferredSize(new Dimension(150, 30));
        fileButton.addActionListener(actlist);
        add(fileButton, "gapright 50, wrap");

        JButton button = new JButton("Start search");
        button.setActionCommand(ActionCommands.SEARCH);
        button.setPreferredSize(new Dimension(175, 35));
        button.addActionListener(actlist);
        add(button, "span, align center, push");
    }
}
