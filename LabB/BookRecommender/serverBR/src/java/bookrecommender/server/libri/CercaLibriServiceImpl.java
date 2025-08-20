package bookrecommender.server.libri;

import bookrecommender.condivisi.libri.Libro;
import bookrecommender.condivisi.libri.CercaLibriService;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CercaLibriServiceImpl extends UnicastRemoteObject implements CercaLibriService {

     public CercaLibriServiceImpl() throws RemoteException {
        super(); // la chiamata a super() può sollevare RemoteException
        // init se necessario
    }
	

	@Override
	public void cercaLibro_Per_Titolo(String titolo) throws RemoteException {
		// TODO: Implement method logic
		
	}

	@Override
	public Libro getTitoloLibroById(int id) throws RemoteException {
		return null;
		
	}

	@Override
	public void cercaLibro_Per_Autore(String autore) throws RemoteException {
		// TODO: Implement method logic
		
	}

	@Override
	public void cercaLibro_Per_Autore_e_Anno(String autore, String anno) throws RemoteException {
		// TODO: Implement method logic
    }
}
   