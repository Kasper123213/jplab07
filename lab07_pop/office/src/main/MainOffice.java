package main;

import officeGUI.OFrame;
import officeGUI.OPanel;

import javax.swing.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainOffice {
    public static void main(String[] args) {

        try {
            createRegistry();
        } catch (RemoteException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OFrame();
            }
        });

    }

    private static void createRegistry() throws RemoteException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(8000);
        registry.bind("office", new Office());
    }
}
