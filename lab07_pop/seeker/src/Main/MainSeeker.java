package Main;

import interfaces.IClub;
import interfaces.IOffice;
import seekerGUI.SFrame;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class MainSeeker {
    public static IClub club;
    public static int seekerPort = 0;
    public static String officePort = "8000";
    public static String worldPort = "7000";
    public static int clubPort = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SFrame();
            }
        });
    }


    public static ArrayList<Integer> getClubs() {
        try {
            IOffice office = (IOffice) Naming.lookup("rmi://localhost:" + MainSeeker.officePort + "/office");
            return office.getClubs();
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void register() throws MalformedURLException, NotBoundException, RemoteException, AlreadyBoundException {

        club = (IClub) Naming.lookup("rmi://localhost:" + clubPort + "/club");
        seekerPort = club.register();

        Registry registry = LocateRegistry.createRegistry(seekerPort);
        registry.bind("seeker", new Seeker());

    }

    public static void unregister() throws MalformedURLException, NotBoundException, RemoteException {
        if (clubPort == 0) return;
        IClub club;
        club = (IClub) Naming.lookup("rmi://localhost:" + clubPort + "/club");
        club.unregister(seekerPort);
    }
}
