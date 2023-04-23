package interfaces;

import model.Artifact;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IWorld extends Remote {
    Artifact explore(String p0, int x, int y) throws RemoteException;
}