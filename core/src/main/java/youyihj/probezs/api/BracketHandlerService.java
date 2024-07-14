package youyihj.probezs.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BracketHandlerService extends Remote {
    @Nullable
    String getLocalizedName(String expr) throws RemoteException;

    @Nullable
    String getIcon(String expr) throws RemoteException;

    @NotNull
    String getTypeName(String expr) throws RemoteException;
}
