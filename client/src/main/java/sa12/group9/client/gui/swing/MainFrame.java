package sa12.group9.client.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sa12.group9.client.gui.misc.ActionCommands;
import sa12.group9.client.gui.swing.panel.AbstractWorkingPanel;
import sa12.group9.client.gui.swing.panel.WelcomePanel;

public class MainFrame extends JFrame
{
    private static Log log = LogFactory.getLog(MainFrame.class);

    private ActionListener actlist;
    private AbstractWorkingPanel panel;

    public MainFrame(ActionListener actlist)
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
        menuItem.addActionListener(actlist);
        menu.add(menuItem);

        setJMenuBar(menuBar);

        JPanel pan = new JPanel();
        pan.setBackground(new Color(200, 220, 250));
        pan.setBorder(BorderFactory.createLoweredBevelBorder());
        pan.setLayout(new BorderLayout());

        panel = new WelcomePanel(actlist);

        pan.add(panel, BorderLayout.CENTER);
        return pan;
    }

    public void swapPanel(AbstractWorkingPanel panel)
    {
        getContentPane().remove(this.panel);
        
        this.panel = panel;
        
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.updateUI();
    }

    public void showError(String message, String title)
    {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
}