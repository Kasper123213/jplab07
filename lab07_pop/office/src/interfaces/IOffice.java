package interfaces;

import model.Report;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IOffice extends Remote {
    String[] register() throws RemoteException;

    void unregister(int port) throws RemoteException;

    boolean permissionRequest(final String p0) throws RemoteException;

    boolean permissionEnd(final String p0, final String p1) throws RemoteException;

    boolean report(final List<Report> p0, final String p1) throws RemoteException;

    ArrayList<Integer> getClubs() throws RemoteException;
}