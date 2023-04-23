package clubGUI;


import Main.MainClub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

public class CFrame extends JFrame {

    private JPanel contentPane;
    public static CPanel cp;


    private final int width = 600;
    private final int height = 650;

    public CFrame() {

        contentPane = new JPanel();
        contentPane.setLayout(null);

        setAlwaysOnTop(true);
        setSize(new Dimension(width + 10, height + 35));
        setContentPane(contentPane);
        setVisible(true);
        setTitle("Club");

        cp = new CPanel();
        cp.setBounds(5, 5, width - 13, height - 10);

        contentPane.add(cp);


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    MainClub.unregister();
                    MainClub.clubExist = false;
                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
                System.exit(0);
            }
        });
    }
}
