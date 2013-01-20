package sa12.group9.client.gui.swing.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import sa12.group9.common.beans.SearchIssueResponse;

public class IssuingSearchRequestPanel extends AbstractWorkingPanel
{
    private SearchIssueResponse response;
    
    public IssuingSearchRequestPanel(ActionListener actlist, SearchIssueResponse response)
    {
        this.actlist = actlist;
        this.response = response;
        initialize();
        draw();
    }

    @Override
    protected void draw()
    {
        JLabel calc = new JLabel("Issuing search request...");
        calc.setFont(new Font("Arial", Font.BOLD, 30));
        calc.setForeground(Color.WHITE);
        add(calc, "gaptop 100, align center, wrap");
        
        JLabel desc = new JLabel("Sending to " + response.getMaxPeersForForwarding() + " peers, using a TTL of " + response.getTtl() + " and waiting for " + response.getSecondsToWait() + " seconds.");
        desc.setFont(new Font("Arial", Font.BOLD, 14));
        desc.setForeground(Color.WHITE);
        add(desc, "align center, push, wrap 20");
    }
}
