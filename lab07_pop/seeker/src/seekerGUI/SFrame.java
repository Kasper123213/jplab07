package seekerGUI;

import Main.MainSeeker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SFrame extends JFrame {

    private JPanel contentPane;
    protected static SPanel sp;


    private final int width = 200;
    private final int height = 200;

    public SFrame() {

        contentPane = new JPanel();
        contentPane.setLayout(null);

        setAlwaysOnTop(true);
        setSize(new Dimension(width + 10, height + 35));
        setContentPane(contentPane);
        setVisible(true);
        setTitle("Seeker");

        sp = new SPanel();
        sp.setBounds(5, 5, width - 13, height - 10);

        contentPane.add(sp);


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    MainSeeker.unregister();
                } catch (MalformedURLException | NotBoundException | RemoteException ex) {
                    throw new RuntimeException(ex);
                }

                System.exit(0);
            }
        });
    }
}
