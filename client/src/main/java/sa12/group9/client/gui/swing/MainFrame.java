package sa12.group9.client.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.client.gui.misc.ActionCommands;

public class MainFrame extends JFrame
{
    private static Log log = LogFactory.getLog(MainFrame.class);

    private EventListener actlist;
    private JPanel p;
    private JTextField fileName;

    public MainFrame(EventListener actlist)
    {
        log.debug("Inizializing MainFrame");
        this.actlist = actlist;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);
        setLocation(20, 20);
        setTitle("SWAzam - Client");

        setContentPane(drawContent());
        setVisible(true);
    }

    private JPanel drawContent()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Menü");
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("Beenden", KeyEvent.VK_B);
        menuItem.setActionCommand(ActionCommands.EXIT);
        menuItem.addActionListener((ActionListener) actlist);
        menu.add(menuItem);

        setJMenuBar(menuBar);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(200, 220, 250));
        panel.setBorder(BorderFactory.createLoweredBevelBorder());
        panel.setLayout(new BorderLayout());

        p = new JPanel();
        p.setLayout(new MigLayout());
        p.setBorder(BorderFactory.createLoweredBevelBorder());
        p.setBackground(new Color(50, 75, 100));

        JLabel loading = new JLabel("Welcome at SWAzam. Please log in.");
        loading.setFont(new Font("Arial", Font.BOLD, 40));
        loading.setForeground(Color.WHITE);
        p.add(loading, "gaptop 100, align center, wrap");

        JButton button = new JButton("Login");
        button.setActionCommand(ActionCommands.LOGIN);
        button.setPreferredSize(new Dimension(125, 30));
        button.addActionListener((ActionListener) actlist);
        p.add(button, "align center, push");

        panel.add(p, BorderLayout.CENTER);
        return panel;
    }

    public void userLoggedIn()
    {
        getContentPane().remove(p);

        p = new JPanel();
        p.setLayout(new MigLayout());
        p.setBorder(BorderFactory.createLoweredBevelBorder());
        p.setBackground(new Color(50, 75, 100));

        JLabel welcome = new JLabel("Welcome at SWAzam!");
        welcome.setFont(new Font("Arial", Font.BOLD, 40));
        welcome.setForeground(Color.WHITE);
        p.add(welcome, "gaptop 75, align center, span, wrap 10");

        JLabel desc = new JLabel("Please select an audio file from your file system to issue a search request.");
        desc.setFont(new Font("Arial", Font.BOLD, 14));
        desc.setForeground(Color.WHITE);
        p.add(desc, "align center, span, wrap");

        fileName = new JTextField();
        fileName.setEnabled(false);
        fileName.setPreferredSize(new Dimension(400, 20));
        p.add(fileName);

        JButton file = new JButton("Select file");
        file.setActionCommand(ActionCommands.CHOOSEFILE);
        file.setPreferredSize(new Dimension(150, 30));
        file.addActionListener((ActionListener) actlist);
        p.add(file, "align center, push");

        JButton button = new JButton("Start search");
        button.setActionCommand(ActionCommands.SEARCH);
        button.setPreferredSize(new Dimension(150, 30));
        button.addActionListener((ActionListener) actlist);
        p.add(button, "align center, push");

        getContentPane().add(p, BorderLayout.CENTER);
        p.updateUI();
    }

    public void showError(String message, String title)
    {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void updateFileName(String name)
    {
        if (fileName != null)
        {
            fileName.setText(name);
        }
    }
}