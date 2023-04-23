package Main;

import interfaces.ISeeker;
import interfaces.IWorld;
import model.Artifact;
import model.Report;
import seekerGUI.SPanel;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Seeker extends UnicastRemoteObject implements ISeeker {
    int possitionX;
    int possitionY;
    int direction;
    public static int missionActepted = 2;
    String sector;

    public Seeker() throws RemoteException {
        super();
    }

    @Override
    public boolean exploreTask(String p0) throws RemoteException {
        missionActepted = 2;
        sector = p0;
        SPanel.newMission.setText("Nowa misja: " + p0);
        while (missionActepted == 2) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (missionActepted == 1) {
            starteExploring();
            return true;
        } else return false;
    }

    @Override
    public String getName() throws RemoteException {
        return null;
    }

    public void starteExploring() {

        possitionX = (int) (Math.random() * 10);
        possitionY = (int) (Math.random() * 10);

        new Thread(new Runnable() {
            boolean canmove() {
                if (direction == 0 && possitionY - 1 < 0) return false;
                else if (direction == 1 && possitionX + 1 > 9) return false;
                else if (direction == 2 && possitionY + 1 > 9) return false;
                else if (direction == 3 && possitionX - 1 < 0) return false;
                return true;
            }

            int explore() {
                Artifact artifact;
                try {
                    IWorld world = (IWorld) Naming.lookup("rmi://localhost:" + MainSeeker.worldPort + "/world");
                    artifact = world.explore(sector, possitionX, possitionY);
                } catch (NotBoundException | MalformedURLException | RemoteException e) {
                    throw new RuntimeException(e);
                }

                try {
                    MainSeeker.club.report(new Report(possitionX, possitionY, artifact), sector);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }


                return artifact.getExcavationTime();

            }

            @Override
            public void run() {

                while (missionActepted != 0) {

                    do {
                        direction = (int) (Math.random() * 4);
                    } while (!canmove());

                    try {
                        MainSeeker.club.sendPossition(sector, possitionX, possitionY);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }


                    if (direction == 0) possitionY--;
                    else if (direction == 1) possitionX++;
                    else if (direction == 2) possitionY++;
                    else if (direction == 3) possitionX--;


                    try {
                        Thread.sleep(explore());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
}
