package seekerGUI;

import Main.MainSeeker;
import Main.Seeker;
import interfaces.IOffice;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SPanel extends JPanel {
    ArrayList<Integer> clubsPorts = new ArrayList<>();

    JLabel clubName = new JLabel("Nazwa klubu: ");
    JComboBox clubList = new JComboBox();
    JButton registry = new JButton("AKCEPTUJ");
    public static JLabel newMission = new JLabel("Nowa misja: ");
    JButton accept = new JButton("PRZYJMIJ");
    JButton reject = new JButton("ODRZUC");

    public SPanel() {


        setLayout(new GridLayout(6, 1));
        clubList.addItem("Brak Klubu");

        add(clubName);
        add(clubList);
        add(registry);
        add(newMission);
        add(accept);
        add(reject);

        clubList.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

                try {
                    IOffice office = (IOffice) Naming.lookup("rmi://localhost:" + MainSeeker.officePort + "/office");
                    clubsPorts = office.getClubs();
                } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                    throw new RuntimeException(ex);
                }

                clubList.removeAllItems();
                clubList.addItem("Brak Klubu");
                for (int i : clubsPorts) {
                    clubList.addItem(i);
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        registry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clubList.getSelectedIndex() == 0) {
                    try {
                        MainSeeker.unregister();
                    } catch (MalformedURLException | NotBoundException | RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                    MainSeeker.clubPort = 0;
                    return;
                }
                int newClubPort = clubsPorts.get(clubList.getSelectedIndex() - 1);

                if (newClubPort == MainSeeker.clubPort) return;
                try {
                    MainSeeker.unregister();
                } catch (MalformedURLException | NotBoundException | RemoteException ex) {
                    throw new RuntimeException(ex);
                }

                MainSeeker.clubPort = newClubPort;
                try {
                    MainSeeker.register();
                } catch (MalformedURLException | NotBoundException | RemoteException | AlreadyBoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Seeker.missionActepted = 1;
            }
        });

        reject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Seeker.missionActepted = 0;
            }
        });
    }

}
