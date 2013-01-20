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
    private int count;

    public ResultPanel(ActionListener actlist, FoundInformation information, int count)
    {
        this.information = information;
        this.actlist = actlist;
        this.count = count;
        initialize();
        draw();
    }

    @Override
    protected void draw()
    {
        JLabel calc = new JLabel("Your song was identified by...");
        calc.setFont(new Font("Arial", Font.BOLD, 20));
        calc.setForeground(Color.WHITE);
        add(calc, "gaptop 50, align center, span, wrap 10");

        JLabel peer = new JLabel("Peer who found it:   " + information.getPeerUsername());
        peer.setForeground(Color.WHITE);
        add(peer, "gapleft 100");
        JLabel match = new JLabel("With a match of:   " + information.getMatch());
        match.setForeground(Color.WHITE);
        add(match, "gapright 80, push, wrap 40");

        JLabel text = new JLabel("Your song was identified as...");
        text.setFont(new Font("Arial", Font.BOLD, 20));
        text.setForeground(Color.WHITE);
        add(text, "align center, span, wrap 10");

        JLabel title = new JLabel("Title:   " + information.getTitle());
        title.setForeground(Color.WHITE);
        add(title, "gapleft 100");
        JLabel interpret = new JLabel("Interpret:   " + information.getInterpret());
        interpret.setForeground(Color.WHITE);
        add(interpret, "gapright 80, wrap 10");

        JLabel album = new JLabel("Album:   " + (information.getAlbum() == null || information.getAlbum().equals("") ? "-" : information.getAlbum()));
        album.setForeground(Color.WHITE);
        add(album, "gapleft 100");
        JLabel genre = new JLabel("Genre:   " + (information.getGenre() == null || information.getGenre().equals("") ? "-" : information.getGenre()));
        genre.setForeground(Color.WHITE);
        add(genre, "gapright 80, wrap 10");

        JLabel length = new JLabel("Length:   " + (information.getLength() == 0 ? "-" : (information.getLength() / 60 + ":" + information.getLength() % 60)));
        length.setForeground(Color.WHITE);
        add(length, "gapleft 100, wrap 10");

        JLabel anzahl = new JLabel("Altogether " + count + " answer(s) received");
        anzahl.setForeground(Color.WHITE);
        add(anzahl, "align center, span, wrap 10");

        JButton button = new JButton("New Request");
        button.setActionCommand(ActionCommands.NEW);
        button.setPreferredSize(new Dimension(125, 30));
        button.addActionListener(actlist);
        add(button, "align center, span, push");
    }
}
