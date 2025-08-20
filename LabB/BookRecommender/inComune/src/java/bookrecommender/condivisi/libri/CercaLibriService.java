package bookrecommender.condivisi.libri; 

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CercaLibriService extends Remote {

    List<Libro> cercaLibro_Per_Titolo(String titolo) throws RemoteException;
    List<Libro> cercaLibro_Per_Autore(String autore) throws RemoteException;
    List<Libro> cercaLibro_Per_Autore_e_Anno(String Autore, String Anno) throws RemoteException;    

    Libro getTitoloLibroById(int id) throws RemoteException;
   
}