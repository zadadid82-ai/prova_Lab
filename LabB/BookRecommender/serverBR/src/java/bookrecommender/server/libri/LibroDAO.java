package bookrecommender.server.libri;

import bookrecommender.condivisi.libri.Libro;
import java.util.List;

public interface LibroDAO {
    
    Libro creaLibro(String titolo, String autore, String descrizione, String categoria, String year, String price);

    Libro getLibroById(int id);
    
    List<Libro> cercaLibriPerTitolo(String titolo);
    
    List<Libro> cercaLibriPerAutore(String autore);
    
    List<Libro> cercaLibriPerAutoreEAnno(String autore, String anno);
   

    
}