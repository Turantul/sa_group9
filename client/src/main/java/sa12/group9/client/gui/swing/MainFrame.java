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
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.client.gui.ActionCommands;

public class MainFrame extends JFrame
{
	private static Log log = LogFactory.getLog(MainFrame.class);
	
	private EventListener actlist;
	private JPanel p;
	
	public MainFrame(EventListener actlist)
	{
		log.debug("Inizializing MainFrame");
		this.actlist = actlist;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 700);
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
	    p.add(loading, "align center, push");
		
		JButton button = new JButton("Login");
        button.setActionCommand(ActionCommands.LOGIN);
        button.setPreferredSize(new Dimension(125, 30));
        button.addActionListener((ActionListener) actlist);
        p.add(button, "newline, align center, push");

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
        
        JLabel loading = new JLabel("Welcome at SWAzam!");
        loading.setFont(new Font("Arial", Font.BOLD, 40));
        loading.setForeground(Color.WHITE);
        p.add(loading, "align center, push");

        getContentPane().add(p, BorderLayout.CENTER);
        p.updateUI();
	}
}