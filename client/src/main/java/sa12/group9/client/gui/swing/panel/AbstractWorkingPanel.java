package sa12.group9.client.gui.swing.panel;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public abstract class AbstractWorkingPanel extends JPanel
{
    protected ActionListener actlist;
    protected JPanel panel;

    protected AbstractWorkingPanel(ActionListener actlist)
    {
        super();
        this.actlist = actlist;
        initialize();
        draw();
    }

    private void initialize()
    {
        setLayout(new MigLayout());
        setBorder(BorderFactory.createLoweredBevelBorder());
        setBackground(new Color(50, 75, 100));
    }

    protected abstract void draw();
}
