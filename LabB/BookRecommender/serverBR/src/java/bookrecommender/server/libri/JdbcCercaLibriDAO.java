package bookrecommender.server.libri;

import bookrecommender.condivisi.libri.Libro;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCercaLibriDAO implements LibroDAO {
    private static final Logger logger = LogManager.getLogger(JdbcCercaLibriDAO.class);
    private Connection connection;

    public JdbcCercaLibriDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Libro creaLibro(String titolo, String autore, String descrizione, String categoria, String year, String price) {
        String sql = "INSERT INTO Libri (titolo, autori, anno, descrizione, categorie, editore, prezzo) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING *";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, titolo);
            stmt.setString(2, autore);
            stmt.setString(3, year);
            stmt.setString(4, descrizione);
            stmt.setString(5, categoria);
            stmt.setString(6, ""); // editore vuoto per ora
            stmt.setString(7, price);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToLibro(rs);
            }
        } catch (SQLException e) {
            logger.error("Errore durante la creazione del libro: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Libro getLibroById(int id) {
        String sql = "SELECT * FROM Libri WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToLibro(rs);
            }
        } catch (SQLException e) {
            logger.error("Errore durante la ricerca libro per ID " + id + ": " + e.getMessage(), e);
        }
        return null;
    }
    
    @Override
    public List<Libro> cercaLibriPerTitolo(String titolo) {
        String sql = "SELECT * FROM Libri WHERE LOWER(titolo) LIKE LOWER(?) ORDER BY titolo";
        List<Libro> libri = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + titolo + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                libri.add(mapResultSetToLibro(rs));
            }
            logger.info("Trovati {} libri per titolo '{}'", libri.size(), titolo);
        } catch (SQLException e) {
            logger.error("Errore durante la ricerca libri per titolo '" + titolo + "': " + e.getMessage(), e);
        }
        return libri;
    }
    
    @Override
    public List<Libro> cercaLibriPerAutore(String autore) {
        String sql = "SELECT * FROM Libri WHERE LOWER(autori) LIKE LOWER(?) ORDER BY titolo";
        List<Libro> libri = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + autore + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                libri.add(mapResultSetToLibro(rs));
            }
            logger.info("Trovati {} libri per autore '{}'", libri.size(), autore);
        } catch (SQLException e) {
            logger.error("Errore durante la ricerca libri per autore '" + autore + "': " + e.getMessage(), e);
        }
        return libri;
    }
    
    @Override
    public List<Libro> cercaLibriPerAutoreEAnno(String autore, String anno) {
        String sql = "SELECT * FROM Libri WHERE LOWER(autori) LIKE LOWER(?) AND anno = ? ORDER BY titolo";
        List<Libro> libri = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + autore + "%");
            stmt.setString(2, anno);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                libri.add(mapResultSetToLibro(rs));
            }
            logger.info("Trovati {} libri per autore '{}' e anno '{}'", libri.size(), autore, anno);
        } catch (SQLException e) {
            logger.error("Errore durante la ricerca libri per autore '" + autore + "' e anno '" + anno + "': " + e.getMessage(), e);
        }
        return libri;
    }
    
    /**
     * Mappa un ResultSet a un oggetto Libro
     */
    private Libro mapResultSetToLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setId(rs.getLong("id"));
        libro.setTitolo(rs.getString("titolo"));
        libro.setAutori(rs.getString("autori"));
        libro.setAnno(rs.getString("anno"));
        libro.setDescrizione(rs.getString("descrizione"));
        libro.setCategorie(rs.getString("categorie"));
        libro.setEditore(rs.getString("editore"));
        libro.setPrezzo(rs.getString("prezzo"));
        return libro;
    }
}