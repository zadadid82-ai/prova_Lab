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
        // Implementazione per creare un libro nel database
        return null; // Placeholder
    }

    @Override
    public Libro getLibroById(int id) {
        // Implementazione per ottenere un libro per ID dal database
        return null; // Placeholder
    }
}