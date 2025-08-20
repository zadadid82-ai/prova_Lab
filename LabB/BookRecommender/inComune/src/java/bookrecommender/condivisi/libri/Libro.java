package bookrecommender.condivisi.libri;

import java.io.Serializable;

/**
 * Classe che rappresenta un libro nel sistema BookRecommender.
 * Corrisponde alla struttura della tabella Libri nel database.
 */
public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String titolo;
    private String autori;
    private String anno;
    private String descrizione;
    private String categorie;
    private String editore;
    private String prezzo;

    // Costruttore vuoto
    public Libro() {}

    // Costruttore con tutti i parametri
    public Libro(Long id, String titolo, String autori, String anno,
                 String descrizione, String categorie,
                 String editore, String prezzo) {
        this.id = id;
        this.titolo = titolo;
        this.autori = autori;
        this.anno = anno;
        this.descrizione = descrizione;
        this.categorie = categorie;
        this.editore = editore;
        this.prezzo = prezzo;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getAutori() { return autori; }
    public void setAutori(String autori) { this.autori = autori; }

    public String getAnno() { return anno; }
    public void setAnno(String anno) { this.anno = anno; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public String getEditore() { return editore; }
    public void setEditore(String editore) { this.editore = editore; }

    public String getPrezzo() { return prezzo; }
    public void setPrezzo(String prezzo) { this.prezzo = prezzo; }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", autori='" + autori + '\'' +
                ", anno='" + anno + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", categorie='" + categorie + '\'' +
                ", editore='" + editore + '\'' +
                ", prezzo='" + prezzo + '\'' +
                '}';
    }
}