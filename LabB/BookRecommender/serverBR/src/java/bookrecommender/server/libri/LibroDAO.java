package bookrecommender.server.libri;

import bookrecommender.condivisi.libri.Libro;

public interface LibroDAO {
    
    Libro creaLibro(String titolo, String autore, String descrizione, String categoria, String year, String price);

    Libro getLibroById(int id);
   

    
}