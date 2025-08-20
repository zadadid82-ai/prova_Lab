import java.io.IOException;

/**
 * La classe rappresenta un libro con i seguenti campi: id, titolo, autori, descrizione, categoria, editore, prezzo, mese di pubblicazione, anno di pubblicazione.
 * Fornisce metodi per ottenere le informazioni del libro, visualizzare i dettagli del libro e
 * gestire la formattazione di tali dettagli.
 *
 * @author Taha
 * @author Sara
 */


public class Book {
    int id;
    String title;
    String authors;
    String description;
    String category;
    String publisher;
    String price;
    String publishMonth;
    String publishYear;

    private static final int LUNGHEZZA_LAYOUT = 120;

    /**
     * Costruttore della classe "Book".
     *
     * @param id           Identificativo univoco del libro.
     * @param title        Titolo del libro.
     * @param authors      Autori del libro.
     * @param description  Descrizione del libro.
     * @param category     Categoria del libro.
     * @param publisher    Editore del libro.
     * @param price        Prezzo del libro.
     * @param publishMonth Mese di pubblicazione del libro.
     * @param publishYear  Anno di pubblicazione del libro.
     */
    public Book(int id, String title, String authors, String description, String category, String publisher, String price, String publishMonth, String publishYear) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.category = category;
        this.publisher = publisher;
        this.price = price;
        this.publishMonth = publishMonth;
        this.publishYear = publishYear;
    }

    /**
     * Costruttore vuoto della classe "Book".
     */
    public Book() {
    }

    /**
     * Restituisce il titolo del libro.
     *
     * @return Il titolo del libro.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Restituisce gli autori del libro.
     *
     * @return Gli autori del libro.
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * Restituisce l'anno di pubblicazione del libro.
     *
     * @return L'anno di pubblicazione del libro.
     */
    public String getPublishYear() {
        return publishYear;
    }

    /**
     * Restituisce l'ID del libro.
     *
     * @return L'ID del libro.
     */
    public int getId() {
        return id;
    }

    /**
     * Restituisce una rappresentazione testuale del libro, includendo tutti i dettagli rilevanti.
     *
     * @return Una stringa che rappresenta il libro.
     */
    @Override
    public String toString() {
        return "book{id=" + id + ", title=" + title + ", authors=" + authors + ", description=" + description + ", category=" + category + ", publisher=" + publisher + ", price=" + price + ", publishMonth=" + publishMonth + ", publishYear=" + publishYear + '}';
    }

    /**
     * Visualizza i dettagli del libro, inclusi eventuali suggerimenti per libri correlati e valutazioni aggregate.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Formatta e gestisce la lunghezza della descrizione e del titolo del libro utilizzando il metodo {@link LayOut#gestisciStringaLunga}.</li>
     *     <li>Crea un array di stringhe contenente i dettagli del libro, ciascuno formattato con codici ANSI per colore e stile.</li>
     *     <li>Utilizza il metodo {@link LayOut#printVisualizzaLibro} per stampare i dettagli del libro in un layout specifico.</li>
     *     <li>Verifica se esistono suggerimenti per libri correlati utilizzando il metodo {@link ConsigliaLibri#suggerimentiPerLibro}, e li stampa se presenti.</li>
     *     <li>Verifica se esistono valutazioni aggregate per il libro utilizzando il metodo {@link Valutazione#valutazioniAggregate}, e le stampa se presenti, insieme a un'anteprima dei commenti.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante la gestione delle stringhe o la visualizzazione dei dettagli del libro.
     */
    public void visualizzaLibro() throws IOException {
       String copiaDescription = LayOut.gestisciStringaLunga(description, 95, 22);
       String copiaTitle = LayOut.gestisciStringaLunga(title, 95, 22);

        String[] dettagliLibro = {
                LayOut.coloraInBlu(LayOut.ANSI_BOLD + "Book ID:\t ") + id,
                LayOut.coloraInBlu(LayOut.ANSI_BOLD + "TITOLO:\t ") + copiaTitle,
                LayOut.coloraInBlu(LayOut.ANSI_BOLD + "AUTORE:\t ") + authors,
                LayOut.coloraInBlu(LayOut.ANSI_BOLD + "ANNO:\t ") + publishYear,
                LayOut.coloraInBlu(LayOut.ANSI_BOLD + "DESCRIZIONE:\t ") + LayOut.gestisciStringaVuota(copiaDescription),
                LayOut.coloraInBlu(LayOut.ANSI_BOLD + "CATEGORIA:\t ") + LayOut.gestisciStringaVuota(category),
                LayOut.coloraInBlu(LayOut.ANSI_BOLD + "EDITORE:\t ") + publisher,
                LayOut.coloraInBlu(LayOut.ANSI_BOLD + "PREZZO:\t ") + price ,
        };

        LayOut.printVisualizzaLibro("VISUALIZZA LIBRO", dettagliLibro, LayOut.ANSI_BLUE, LUNGHEZZA_LAYOUT);

        String idString = String.valueOf(id);

        String[] suggerimenti = ConsigliaLibri.suggerimentiPerLibro(idString);
        if (suggerimenti != null) {
            LayOut.printVisualizzaLibro("LIBRI CONSIGLIATI: ", suggerimenti, LayOut.ANSI_BLUE, LUNGHEZZA_LAYOUT);
        }

        String[] valutazioniTOT = Valutazione.valutazioniAggregate(idString);
        String[] mediaValutazioni = new String[6];
        if (valutazioniTOT != null) {
            System.arraycopy(valutazioniTOT, 0, mediaValutazioni, 0, 6);
            String[] anteprimaCommenti = new String[6];
            System.arraycopy(valutazioniTOT, 6, anteprimaCommenti, 0, 6);

            LayOut.printVisualizzaLibro("MEDIA DELLE VALUTAZIONI: ", mediaValutazioni, LayOut.ANSI_BLUE, LUNGHEZZA_LAYOUT);
            LayOut.printVisualizzaLibro("ANTEPRIMA DEI COMMENTI: ", anteprimaCommenti, LayOut.ANSI_BLUE, LUNGHEZZA_LAYOUT);
        }
    }

}