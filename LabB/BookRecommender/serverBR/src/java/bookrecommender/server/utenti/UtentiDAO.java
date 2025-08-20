package bookrecommender.server.utenti;

import bookrecommender.condivisi.utenti.Utenti;
public interface UtentiDAO {
    
    /**
     * Salva un nuovo utente nel database
     * @param utente l'utente da salvare
     * @return true se il salvataggio è riuscito, false altrimenti
     */
    boolean save(Utenti utente);
    
    /**
     * Trova un utente tramite username
     * @param username l'username dell'utente da cercare
     * @return l'utente se trovato, null altrimenti
     */
    Utenti findByUsername(String username);
    
    /**
     * Aggiorna i dati di un utente esistente
     * @param utente l'utente con i dati aggiornati
     * @return true se l'aggiornamento è riuscito, false altrimenti
     */
    boolean update(Utenti utente);
    
    /**
     * Elimina un utente dal database
     * @param username l'username dell'utente da eliminare
     * @return true se l'eliminazione è riuscita, false altrimenti
     */
    boolean delete(String username);
}
