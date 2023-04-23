package Main;

import clubGUI.CFrame;
import clubGUI.CPanel;
import interfaces.IClub;
import model.Junk;
import model.Report;
import model.Treasure;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Club extends UnicastRemoteObject implements IClub {

    public static ArrayList<Integer> seekerPorts = new ArrayList<>();

    public Club() throws RemoteException {
        super();
    }

    @Override
    public int register() throws RemoteException {
        for (int i = (MainClub.thisPort - 6000) * 100 + 5000; ; i++) {
            if (!seekerPorts.contains(i)) {
                seekerPorts.add(i);
                return i;
            }
        }
    }


    @Override
    public void unregister(int port) throws RemoteException {
        if (seekerPorts.contains(port)) seekerPorts.remove((Object) port);
    }

    @Override
    public String getName() throws RemoteException {
        return null;
    }

    @Override
    public boolean report(Report p0, String p1) throws RemoteException {
        if (p0.getArtifact() instanceof Treasure) {
            CPanel.treasuresNumber++;
            CPanel.treasures.setText("Liczba znalezionych skarbow: " + CPanel.treasuresNumber);
        } else if (p0.getArtifact() instanceof Junk) {
            CPanel.junksNumber++;
            CPanel.junks.setText("Liczna znalezionych rupieci: " + CPanel.junksNumber);
        }
        MainClub.reports.add(p0);
        return false;
    }

    @Override
    public void sendPossition(String sector, int possitionX, int possitionY) {
        int x;
        int y;

        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 80; j++) {
                CPanel.seekers.get(i).set(j, 0);
            }
        }
        x = (CPanel.letters.indexOf(String.valueOf(sector.charAt(0)))) * 10 + possitionX;


        y = (8 - Integer.parseInt(String.valueOf(sector.charAt(1)))) * 10 + possitionY;


        CPanel.seekers.get(x).set(y, 1);

        CFrame.cp.repaint();
    }
}
