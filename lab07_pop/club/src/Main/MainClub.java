package Main;

import clubGUI.CFrame;
import interfaces.IOffice;
import interfaces.ISeeker;
import model.Report;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class MainClub {
    public static String officePort = "8000";
    public static int thisPort = 6000;
    public static String[] sectors = new String[2];
    public static IOffice office;
    public static boolean clubExist = true;
    public static List<Report> reports = new ArrayList<>();

    public static void main(String[] args) {

        try {
            register();
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            throw new RuntimeException(e);
        }

        try {
            createRegistry();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CFrame();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (clubExist) {
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        office.report(reports, String.valueOf(thisPort));
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private static void createRegistry() throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(thisPort);
        registry.rebind("club", new Club());
    }

    private static void register() throws MalformedURLException, NotBoundException, RemoteException {
        office = (IOffice) Naming.lookup("rmi://localhost:" + officePort + "/office");
        String[] response = office.register();
        thisPort = Integer.parseInt(response[0]);
        sectors[0] = response[1];
        sectors[1] = response[2];
    }

    public static void unregister() throws RemoteException {
        office.unregister(thisPort);
    }

    public static void getRequest() throws RemoteException {
        boolean premission;
        int sector = (int) (Math.random() * 2);
        try {
            premission = office.permissionRequest(sectors[sector]);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if (premission && Club.seekerPorts.size() != 0) {
            int seekerPort = Club.seekerPorts.get((int) (Math.random() * Club.seekerPorts.size()));
            ISeeker seeker;
            try {
                seeker = (ISeeker) Naming.lookup("rmi://localhost:" + seekerPort + "/seeker");
            } catch (NotBoundException | MalformedURLException | RemoteException e) {
                throw new RuntimeException(e);
            }

            if (!seeker.exploreTask(sectors[sector])) getRequest();
        }
    }
}
