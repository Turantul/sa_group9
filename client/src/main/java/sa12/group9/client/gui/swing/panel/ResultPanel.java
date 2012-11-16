package sa12.group9.client.gui.swing.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import sa12.group9.client.gui.misc.ActionCommands;
import sa12.group9.common.beans.FoundInformation;

public class ResultPanel extends AbstractWorkingPanel
{
    private FoundInformation information;

    public ResultPanel(ActionListener actlist, FoundInformation information)
    {
        super(actlist);
        this.information = information;
    }

    @Override
    protected void draw()
    {
        JLabel calc = new JLabel("Your song was identified as...");
        calc.setFont(new Font("Arial", Font.BOLD, 30));
        calc.setForeground(Color.WHITE);
        add(calc, "gaptop 100, align center, wrap");

        // TODO: take infos and show them

        JButton button = new JButton("New Request");
        button.setActionCommand(ActionCommands.NEW);
        button.setPreferredSize(new Dimension(125, 30));
        button.addActionListener((ActionListener) actlist);
        add(button, "align center, push");
    }
}
