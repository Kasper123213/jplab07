package main;

import interfaces.IOffice;
import model.Report;
import officeGUI.OFrame;
import officeGUI.OPanel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Office extends UnicastRemoteObject implements IOffice {
    public static ArrayList<Report> reports = new ArrayList<>();
    public ArrayList<Integer> clubPorts = new ArrayList<>();
    ArrayList<String> sectors = new ArrayList<String>();
    ArrayList<String> letters = new ArrayList<String>(List.of(new String[]{"A", "B", "C", "D", "E", "F", "G", "H"}));

    public Office() throws RemoteException {
        super();
    }

    @Override
    public String[] register() throws RemoteException {
        String[] toReturn = new String[3];
        for (int i = 6000; ; i++) {
            if (!clubPorts.contains(i)) {
                clubPorts.add(i);
                toReturn[0] = String.valueOf(i);
                break;
            }
        }

        String newSector;

        for (int i = 1; i <= 2; i++) {
            while (toReturn[i] == null) {
                int collumn = (int) (Math.random() * 7 + 1);
                int row = (int) (Math.random() * 7 + 1);
                newSector = letters.get(collumn) + row;
                if (!sectors.contains(newSector)) {
                    OPanel.clubs[collumn + (8 - row) * 8] = toReturn[0];
                    sectors.add(newSector);
                    toReturn[i] = newSector;
                }
            }
        }
        OFrame.op.repaint();
        return toReturn;

    }

    @Override
    public void unregister(int port) throws RemoteException {
        String sectorName1 = sectors.get(clubPorts.indexOf(port) * 2);
        String sectorName2 = sectors.get(clubPorts.indexOf(port) * 2 + 1);

        int a1 = letters.indexOf(String.valueOf(sectorName1.charAt(0)));
        int a2 = letters.indexOf(String.valueOf(sectorName2.charAt(0)));

        int b1 = Integer.parseInt(String.valueOf(sectorName1.charAt(1)));
        int b2 = Integer.parseInt(String.valueOf(sectorName2.charAt(1)));


        OPanel.clubs[a1 + (8 - b1) * 8] = null;
        OPanel.clubs[a2 + (8 - b2) * 8] = null;

        sectors.remove(2 * clubPorts.indexOf(port));
        sectors.remove(2 * clubPorts.indexOf(port));
        clubPorts.remove((Object) port);
        OFrame.op.repaint();
    }

    @Override
    public boolean permissionRequest(String p0) throws RemoteException {
        return true;
    }

    @Override
    public boolean permissionEnd(String p0, String p1) throws RemoteException {
        return false;
    }

    @Override
    public boolean report(List<Report> p0, String p1) throws RemoteException {
        reports.addAll(p0);
        return false;
    }

    @Override
    public ArrayList<Integer> getClubs() throws RemoteException {
        return clubPorts;
    }
}
