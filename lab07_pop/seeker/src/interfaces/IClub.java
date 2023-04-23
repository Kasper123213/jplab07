package interfaces;

import model.Report;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClub extends Remote
{
    int register() throws RemoteException;

    void unregister(int port) throws RemoteException;

    String getName() throws RemoteException;

    boolean report(final Report p0, final String p1) throws RemoteException;

    void sendPossition(String sector, int possitionX, int possitionY)throws RemoteException;
}