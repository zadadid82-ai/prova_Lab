package bookrecommender.server.utenti;

import bookrecommender.condivisi.utenti.Utenti;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtentiDAO implements UtentiDAO {
    
    private static final Logger logger = LogManager.getLogger(JdbcUtentiDAO.class);
    
    // Aggiornate per utilizzare userID come chiave primaria (compatibili con Java 11)
    private static final String QUERY_SAVE = 
        "INSERT INTO UtentiRegistrati (user_id, password, nome, cognome, codice_fiscale, email) " +
        "VALUES (?, ?, ?, ?, ?, ?)";
        
    private static final String QUERY_FIND_BY_ID = 
        "SELECT user_id, password, nome, cognome, codice_fiscale, email " +
        "FROM UtentiRegistrati " +
        "WHERE user_id = ?";
        
    private static final String QUERY_FIND_BY_USERNAME = 
        "SELECT user_id, password, nome, cognome, codice_fiscale, email " +
        "FROM UtentiRegistrati " +
        "WHERE user_id = ? OR email = ? OR codice_fiscale = ?";
        
    private static final String QUERY_UPDATE = 
        "UPDATE UtentiRegistrati " +
        "SET password = ?, nome = ?, cognome = ?, codice_fiscale = ?, email = ? " +
        "WHERE user_id = ?";
        
    private static final String QUERY_DELETE = 
        "DELETE FROM UtentiRegistrati " +
        "WHERE user_id = ?";
        
    private static final String QUERY_FIND_ALL = 
        "SELECT user_id, password, nome, cognome, codice_fiscale, email " +
        "FROM UtentiRegistrati";

    @Override
    public boolean save(Utenti utente) {
        try (Connection conn = bookrecommender.server.utili.DBConnectionSingleton.openNewConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_SAVE)) {
            
            stmt.setString(1, utente.userID());
            stmt.setString(2, utente.password());
            stmt.setString(3, utente.nome());
            stmt.setString(4, utente.cognome());
            stmt.setString(5, utente.codFiscale());
            stmt.setString(6, utente.email());
            
            int rowsAffected = stmt.executeUpdate();
            boolean success = rowsAffected > 0;
            
            if (success) {
                logger.info("Utente salvato con successo: " + utente.userID());
            } else {
                logger.warn("Nessuna riga aggiornata durante il salvataggio dell'utente: " + utente.userID());
            }
            
            return success;
            
        } catch (SQLException e) {
            logger.error("Errore SQL durante il salvataggio dell'utente: " + utente.userID(), e);
            return false;
        } catch (Exception e) {
            logger.error("Errore imprevisto durante il salvataggio dell'utente: " + utente.userID(), e);
            return false;
        }
    }

    @Override
    public Utenti findByUsername(String username) {
        try (Connection conn = bookrecommender.server.utili.DBConnectionSingleton.openNewConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_FIND_BY_USERNAME)) {
            
            // Cerca per username, email o codice fiscale
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Utenti utente = new Utenti(
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("codice_fiscale"),
                        rs.getString("email"),
                        rs.getString("user_id"),
                        rs.getString("password")
                    );
                    
                    logger.debug("Utente trovato: " + username);
                    return utente;
                } else {
                    logger.debug("Utente non trovato: " + username);
                    return null;
                }
            }
            
        } catch (SQLException e) {
            logger.error("Errore SQL durante la ricerca dell'utente: " + username, e);
            return null;
        } catch (Exception e) {
            logger.error("Errore imprevisto durante la ricerca dell'utente: " + username, e);
            return null;
        }
    }

    @Override
    public boolean update(Utenti utente) {
        try (Connection conn = bookrecommender.server.utili.DBConnectionSingleton.openNewConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_UPDATE)) {
            
            stmt.setString(1, utente.password());
            stmt.setString(2, utente.nome());
            stmt.setString(3, utente.cognome());
            stmt.setString(4, utente.codFiscale());
            stmt.setString(5, utente.email());
            stmt.setString(6, utente.userID());
            
            int rowsAffected = stmt.executeUpdate();
            boolean success = rowsAffected > 0;
            
            if (success) {
                logger.info("Utente aggiornato con successo: " + utente.userID());
            } else {
                logger.warn("Nessuna riga aggiornata durante l'aggiornamento dell'utente: " + utente.userID());
            }
            
            return success;
            
        } catch (SQLException e) {
            logger.error("Errore SQL durante l'aggiornamento dell'utente: " + utente.userID(), e);
            return false;
        } catch (Exception e) {
            logger.error("Errore imprevisto durante l'aggiornamento dell'utente: " + utente.userID(), e);
            return false;
        }
    }

    @Override
    public boolean delete(String username) {
        try (Connection conn = bookrecommender.server.utili.DBConnectionSingleton.openNewConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_DELETE)) {
            
            stmt.setString(1, username);
            
            int rowsAffected = stmt.executeUpdate();
            boolean success = rowsAffected > 0;
            
            if (success) {
                logger.info("Utente eliminato con successo: " + username);
            } else {
                logger.warn("Nessuna riga eliminata durante l'eliminazione dell'utente: " + username);
            }
            
            return success;
            
        } catch (SQLException e) {
            logger.error("Errore SQL durante l'eliminazione dell'utente: " + username, e);
            return false;
        } catch (Exception e) {
            logger.error("Errore imprevisto durante l'eliminazione dell'utente: " + username, e);
            return false;
        }
    }
    
    /**
     * Ottiene tutti gli utenti dal database
     * @return lista di tutti gli utenti
     */
    public List<Utenti> findAll() {
        List<Utenti> utenti = new ArrayList<>();
        
        try (Connection conn = bookrecommender.server.utili.DBConnectionSingleton.openNewConnection();
             PreparedStatement stmt = conn.prepareStatement(QUERY_FIND_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Utenti utente = new Utenti(
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("codice_fiscale"),
                    rs.getString("email"),
                    rs.getString("user_id"),
                    rs.getString("password")
                );
                utenti.add(utente);
            }
            
            logger.debug("Trovati " + utenti.size() + " utenti nel database");
            
        } catch (SQLException e) {
            logger.error("Errore SQL durante il recupero di tutti gli utenti", e);
        } catch (Exception e) {
            logger.error("Errore imprevisto durante il recupero di tutti gli utenti", e);
        }
        
        return utenti;
    }
    
    /**
     * Ottiene una connessione al database
     * @return connessione al database
     * @throws SQLException in caso di errore di connessione
     */
    private Connection getConnection() throws SQLException {
        // Utilizza la classe di connessione esistente nel progetto
        try {
            return bookrecommender.server.utili.DBConnectionSingleton.getConnection();
        } catch (Exception e) {
            logger.error("Connessione DB non disponibile", e);
            throw new SQLException("Connessione al database non disponibile", e);
        }
    }
}
