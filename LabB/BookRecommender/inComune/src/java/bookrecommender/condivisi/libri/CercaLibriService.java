package bookrecommender.condivisi.libri; 

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CercaLibriService extends Remote {

    void cercaLibro_Per_Titolo(String titolo) throws RemoteException;
    void cercaLibro_Per_Autore(String autore) throws RemoteException;
    void cercaLibro_Per_Autore_e_Anno(String Autore, String Anno) throws RemoteException;    

    Libro getTitoloLibroById(int id) throws RemoteException;
   
}