package main;

import interfaces.IWorld;
import model.Artifact;
import model.Blank;
import model.Junk;
import model.Treasure;
import worldGUI.WFrame;
import worldGUI.WPanel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class World extends UnicastRemoteObject implements IWorld {
    public static ArrayList<String> letters = new ArrayList<>();

    public World() throws RemoteException {
        super();
        letters.addAll(List.of(new String[]{"A", "B", "C", "D", "E", "F", "G", "H"}));
    }

    @Override
    public Artifact explore(String p0, int x, int y) throws RemoteException {
        int positionX = (letters.indexOf(String.valueOf(p0.charAt(0)))) * 10 + x;
        int positionY = (8 - Integer.parseInt(String.valueOf(p0.charAt(1)))) * 10 + y;
        Artifact artifact = (Artifact) MainWorld.world.get(positionX).get(positionY);

        if (artifact instanceof Treasure) {
            MainWorld.world.get(positionX).set(positionY, new Blank(500));
            WPanel.treasuresNumber++;
            WPanel.treasures.setText("Liczba znalezionych skarbow: " + WPanel.treasuresNumber);
            WFrame.wp.repaint();
        } else if (artifact instanceof Junk) {
            MainWorld.world.get(positionX).set(positionY, new Blank(500));
            WPanel.junksNumber++;
            WPanel.junks.setText("Liczba znalezionych smieci: " + WPanel.junksNumber);
            WFrame.wp.repaint();
        }

        return artifact;
    }
}
