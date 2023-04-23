package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISeeker extends Remote {
    boolean exploreTask(final String p0) throws RemoteException;

    String getName() throws RemoteException;
}