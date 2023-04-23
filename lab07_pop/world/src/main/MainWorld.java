package main;

import model.*;
import worldGUI.WFrame;
import worldGUI.WPanel;

import javax.swing.*;
import java.awt.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class MainWorld {
    public static ArrayList<ArrayList> world = new ArrayList<>();//x

    public static void main(String[] args) {

        for (int i = 0; i < 80; i++) {
            world.add(new ArrayList<Artifact>());//y
            for (int j = 0; j < 80; j++) {
                world.get(i).add(new Blank(500));
            }
        }

        createRegistry();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WFrame();
            }
        });

    }

    private static void createRegistry() {
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(7000);
            registry.bind("world", new World());
        } catch (RemoteException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }

    }


    public static void addArtifact(Point possition, int category) {
        int x = (int) ((possition.x - 20) / WPanel.sectorSize);
        int y = (int) ((possition.y - 60) / WPanel.sectorSize);
        if (category == -1) {
            world.get(x).set(y, new Junk(2000));
            WFrame.wp.repaint();
            return;
        }


        for (Category c : Category.values()) {
            if (category == c.ordinal()) world.get(x).set(y, new Treasure(3000, c));
        }
        WFrame.wp.repaint();

    }

    public static void delete(Point possition) {
        int x = (int) ((possition.x - 20) / WPanel.sectorSize);
        int y = (int) ((possition.y - 60) / WPanel.sectorSize);
        world.get(x).set(y, new Blank(500));
        WFrame.wp.repaint();
    }

}
