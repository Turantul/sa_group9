package sa12.group9.client.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ac.at.tuwien.infosys.swa.audio.Fingerprint;
import ac.at.tuwien.infosys.swa.audio.FingerprintSystem;

import sa12.group9.client.gui.misc.ActionCommands;
import sa12.group9.client.gui.misc.AudioFilter;
import sa12.group9.client.gui.swing.MainFrame;
import sa12.group9.client.gui.swing.panel.CalculatingPanel;
import sa12.group9.client.gui.swing.panel.MainPanel;

public class MainAction implements ActionListener
{
    private static Log log = LogFactory.getLog(MainAction.class);

    private MainFrame frame;
    private File f;
    private Thread processing;

    public MainAction()
    {
        log.debug("Creating MainAction");

        frame = new MainFrame(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals(ActionCommands.EXIT))
        {
            System.exit(0);
        }
        else if (e.getActionCommand().equals(ActionCommands.LOGIN))
        {
            new LoginAction(this);
        }
        else if (e.getActionCommand().equals(ActionCommands.CHOOSEFILE))
        {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new AudioFilter());
            fc.setAcceptAllFileFilterUsed(false);
            int returnval = fc.showOpenDialog(frame);
            if (returnval == JFileChooser.APPROVE_OPTION)
            {
                f = fc.getSelectedFile();
                frame.swapPanel(new MainPanel(this, fc.getSelectedFile().getName()));
            }
        }
        else if (e.getActionCommand().equals(ActionCommands.SEARCH))
        {
            if (f != null)
            {
                if (processing == null || !processing.isAlive())
                {
                    processing = createSearchThread();
                    processing.start();
                }
                else
                {
                    frame.showError("You already issued a song search request.\nWait until it is finished!", "Already searching");
                }
            }
            else
            {
                frame.showError("You must select an audio file first!", "No file selected");
            }
        }
    }

    public void loginSuccessful()
    {
        log.info("User logged in");

        frame.swapPanel(new MainPanel(this));
    }

    private Thread createSearchThread()
    {
        return new Thread()
        {
            public void run()
            {
                try
                {
                    log.info("Calculating fingerprint");
                    frame.swapPanel(new CalculatingPanel(MainAction.this));
    
                    FingerprintSystem system = new FingerprintSystem(44100);
                    Fingerprint finger = system.fingerprint(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
    
                    Fingerprint finger2 = system.fingerprint(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
    
                    frame.showError("" + finger.match(finger2), "Match");
    
                    // TODO: issue server request
                    log.info("Issuing server request");
    
                    // TODO: send to peers
                    log.info("Sending request to peers");
    
                    // TODO: open server for listening
                    log.info("Waiting for responses...");
                }
                catch (IOException e)
                {
                    frame.showError("There was an error processing the audio file!", "Error processing file");
                }
            }
        };
    }
}