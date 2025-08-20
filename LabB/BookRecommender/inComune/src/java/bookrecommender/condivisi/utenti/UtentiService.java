package bookrecommender.condivisi.utenti;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UtentiService extends Remote {
    
    /**
     * Autentica un utente con username e password
     * @param username username dell'utente
     * @param password password dell'utente
     * @return true se l'autenticazione è riuscita, false altrimenti
     * @throws RemoteException in caso di errore di comunicazione RMI
     */
    boolean authenticateUser(String username, String password) throws RemoteException;
    
    /**
     * Registra un nuovo utente nel sistema
     * @param utente oggetto Utenti con i dati del nuovo utente
     * @return true se la registrazione è riuscita, false altrimenti
     * @throws RemoteException in caso di errore di comunicazione RMI
     */
    boolean registerUser(Utenti utente) throws RemoteException;
    
    /**
     * Verifica se un username è già presente nel sistema
     * @param username username da verificare
     * @return true se l'username è già presente, false altrimenti
     * @throws RemoteException in caso di errore di comunicazione RMI
     */
    boolean isUsernameExists(String username) throws RemoteException;
    
    /**
     * Ottiene un utente dal sistema tramite username
     * @param username username dell'utente
     * @return oggetto Utenti se trovato, null altrimenti
     * @throws RemoteException in caso di errore di comunicazione RMI
     */
    Utenti getUserByUsername(String username) throws RemoteException;
}
