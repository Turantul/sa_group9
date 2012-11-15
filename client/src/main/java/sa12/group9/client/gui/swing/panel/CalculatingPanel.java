package sa12.group9.client.gui.swing.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class CalculatingPanel extends AbstractWorkingPanel
{
    public CalculatingPanel(ActionListener actlist)
    {
        super(actlist);
    }

    @Override
    protected void draw()
    {
        JLabel calc = new JLabel("Calculating fingerprint...");
        calc.setFont(new Font("Arial", Font.BOLD, 20));
        calc.setForeground(Color.WHITE);
        add(calc, "align center, push");
    }
}
