import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * La classe gestisce il funzionamento principale dell'applicazione Book Recommender.
 *
 * <p>Questa classe contiene il punto di ingresso dell'applicazione e diversi metodi di utilità per accedere ai libri
 * caricati da un file CSV. L'applicazione permette agli utenti registrati di visualizzare, selezionare, suggerire, valutare e commentare libri.
 * Tutti gli utenti possono cercare e visualizzate i libri, inclusi i suggerimenti e le valutazione effettuate dai utenti registrati. </p>
 * Inoltre, la classe contiene i principali metodi di ricerca.
 *
 * @author Sara
 * @author Taha
 */
public class BookRecommender {

    /**
     * Il nome del file CSV contenente i dati sui libri.
     */
    private static final String LIBRI_FILE = "Libri.dati.csv";
    private static final int LUNGHEZZA_LAYOUT = 120;


    /**
     * La lista di oggetti {@link Book} che rappresentano i libri caricati dal file CSV.
     */
    public static List<Book> books;

    /**
     * Punto di ingresso dell'applicazione Book Recommender.
     *
     * <p>Nel dettaglio:</p>
     *
     * <ol>
     *     <li>Legge i dati sui libri da un file CSV specificato utilizzando il metodo {@link BookReader#readBooks} e li carica nella lista {@link #books}.</li>
     *     <li>Inizializza uno scanner per leggere l'input dell'utente.</li>
     *     <li>Crea un'istanza della classe {@link Utente}, passando lo scanner per la gestione dell'input.</li>
     *     <li>Mostra un messaggio di benvenuto all'utente utilizzando il metodo {@link LayOut#stampaBenvenuto}.</li>
     *     <li>Chiama il metodo {@link Utente#mostraMenuPrincipale} per visualizzare e gestire il menu principale dell'applicazione.</li>
     *     <li>Gestisce eventuali eccezioni di I/O che possono verificarsi durante l'esecuzione del programma.</li>
     *     <li>Chiude lo scanner una volta terminato l'uso.</li>
     * </ol>
     *
     * @param args Parametri della riga di comando (non utilizzati in questo caso).
     * @throws IOException Se si verifica un errore durante la lettura del file o altre operazioni di I/O.
     */
    public static void main(String[] args) throws IOException {

        books = BookReader.readBooks(String.valueOf(new File(LIBRI_FILE)));

        Scanner scanner = new Scanner(System.in);
        Utente utente = new Utente(scanner);

        // Benvenuto all'utente
        LayOut.stampaBenvenuto("Benvenuti nel Book Recommender!");


        // Visualizza il menu principale
        try {
            utente.mostraMenuPrincipale();
        } catch (IOException e) {
            System.out.println("Si è verificato un errore di I/O: " + e.getMessage());
        }

        scanner.close();
    }

    /**
     * Recupera un oggetto {@link Book} dalla lista {@link #books} in base al suo ID.
     *
     * <p>Nel dettaglio:</p>
     *
     * <ol>
     *     <li>Itera attraverso la lista {@link #books}.</li>
     *     <li>Verifica se l'ID del libro corrente corrisponde all'ID specificato.</li>
     *     <li>Se trova una corrispondenza, restituisce l'oggetto {@link Book} corrispondente.</li>
     *     <li>Se non trova alcuna corrispondenza, restituisce {@code null}.</li>
     * </ol>
     *
     * @param id L'ID del libro da cercare.
     * @return L'oggetto {@link Book} corrispondente all'ID specificato, o {@code null} se non trovato.
     */
    public static Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    /**
     * Recupera il titolo di un libro in base al suo ID.
     *
     * <p>Nel dettaglio:</p>
     *
     * <ol>
     *     <li>Itera attraverso la lista {@link #books}.</li>
     *     <li>Verifica se l'ID del libro corrente corrisponde all'ID specificato.</li>
     *     <li>Se trova una corrispondenza, restituisce il titolo del libro come stringa.</li>
     *     <li>Se non trova alcuna corrispondenza, restituisce la stringa "ID non trovato".</li>
     * </ol>
     *
     * @param id L'ID del libro da cercare.
     * @return Il titolo del libro corrispondente all'ID specificato, o la stringa "ID non trovato" se non trovato.
     */
    public static String getTitoloLibroById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book.getTitle();
            }
        }
        return "ID non trovato";
    }

    /**
     * Il metodo cerca e stampa i libri che contengono una determinata sottostringa nel nome dell'autore.
     *
     * <p>Nel dettaglio:</p>
     *
     * <ol>
     *     <li>Converte la stringa "Autore" in minuscolo per rendere la ricerca case-insensitive.</li>
     *     <li>Chiama il metodo {@link BookReader#readBooks}  per leggere tutti i libri dal file specificato, ottenendo una lista di oggetti {@link Book}.</li>
     *     <li>Crea una mappa {@link TreeMap} vuota chiamata "libri", con il titolo del libro come chiave e l'oggetto {@link Book} come valore.</li>
     *     <li>Popola la mappa "libri" con i titoli dei libri come chiave e i corrispondenti oggetti {@link Book} come valore.</li>
     *     <li>Itera attraverso i libri nella mappa:
     *         <ul>
     *             <li>Verifica se il campo "authors" del libro contiene la sottostringa di ricerca (in minuscolo).</li>
     *             <li>Se il campo "authors" contiene la sottostringa, aggiunge il libro alla lista "risultati".</li>
     *         </ul>
     *     </li>
     *     <li>Se la lista "risultati" è vuota, stampa un messaggio di errore indicante che nessun libro è stato trovato con l'autore specificato.</li>
     *     <li>Se ci sono risultati, utilizza il metodo {@link LayOut#printBook_ID_TITOLO} per stampare i risultati in un layout specifico.</li>
     * </ol>
     *
     * @param Autore La sottostringa da cercare nei nomi degli autori dei libri.
     */
    public static void cercaLibro_Per_Autore(String Autore) {

        String atlc = Autore.toLowerCase();

        List<Book> risultati = new ArrayList<>();

        TreeMap<String, Book> libri = new TreeMap<>();

        for (Book libro : books) {
            libri.put(libro.getTitle(), libro);
        }

        for (Book libro : libri.values()) {
            if (libro.getAuthors().toLowerCase().contains(atlc)) {
                risultati.add(libro);
            }
        }

        if (risultati.isEmpty()) {
            System.out.println(LayOut.coloraErrore("Nessun libro trovato con l'autore '") + Autore + "'.");
        } else {
            LayOut.printBook_ID_TITOLO("Ricerca per autore: " + Autore, risultati, LayOut.ANSI_GREEN, 120);

        }

    }
    /**
     * Il metodo cerca e stampa i libri che contengono una determinata sottostringa nel titolo.
     *
     * <p>Nel dettaglio: </p>
     * <ol>
     *     <li>Converte la stringa di ricerca "S" in minuscolo per rendere la ricerca case-insensitive.</li>
     *     <li>Crea una mappa {@link TreeMap} vuota chiamata "libri" per memorizzare i libri, con il titolo come chiave e l'oggetto {@link Book} come valore.</li>
     *     <li>Chiama il metodo {@link BookReader#readBooks} per leggere tutti i libri dal file specificato, ottenendo una lista di oggetti {@link Book}.</li>
     *     <li>Popola la mappa "libri" con i titoli dei libri come chiave e i corrispondenti oggetti {@link Book} come valore.</li>
     *     <li>Itera attraverso i titoli nella mappa:
     *         <ul>
     *             <li>Converte ogni titolo in minuscolo e verifica se contiene la sottostringa di ricerca.</li>
     *             <li>Se un titolo contiene la sottostringa, aggiunge l'oggetto {@link Book} corrispondente alla lista "risultati".</li>
     *         </ul>
     *     </li>
     *     <li>Se la lista "risultati" è vuota, stampa un messaggio di errore indicante che nessun libro è stato trovato.</li>
     *     <li>Se ci sono risultati, utilizza il metodo {@link LayOut#printBook_ID_TITOLO} per stampare i risultati in un layout specifico.</li>
     * </ol>
     *
     * @param S La sottostringa da cercare nei titoli dei libri.
     */
    public static void cercaLibro_Per_Titolo(String S) {
        List<Book> risultati = new ArrayList<>();
        String slo = S.toLowerCase();

        TreeMap<String, Book> libri = new TreeMap<>(); // Creazione di una mappa per memorizzare i libri con il titolo come chiave

        for (Book libro : books) {
            libri.put(libro.getTitle(), libro); //aggiunge le coppie valore-chiave
        }

        for (String titolo : libri.keySet()) { // keySet() restituisce l'insieme delle chiavi
            if (titolo.toLowerCase().contains(slo)) { // veridica se il titolo contiene una sottostringa di sol
                risultati.add(libri.get(titolo));
            }
        }

        if (risultati.isEmpty()) {
            System.out.println(LayOut.coloraErrore("Nessun libro trovato con il titolo '" + S + "'."));
        } else {
            LayOut.printBook_ID_TITOLO("Ricerca per titolo: " + S, risultati, LayOut.ANSI_BLUE, LUNGHEZZA_LAYOUT); //metodo per stampare i risultati usando un layout specifico

        }

    }

    /**
     * Il metodo cerca e stampa i libri che contengono una determinata sottostringa nel nome dell'autore e che sono stati pubblicati in un determinato anno.
     *
     * <p>Nel dettaglio:</p>
     *
     * <ol>
     *     <li>Converte la stringa "Autore" in minuscolo per rendere la ricerca case-insensitive.</li>
     *     <li>Chiama il metodo {@link BookReader#readBooks} per leggere tutti i libri dal file specificato, ottenendo una lista di oggetti {@link Book}.</li>
     *     <li>Crea una mappa {@link TreeMap} vuota chiamata "libri", con il titolo del libro come chiave e l'oggetto {@link Book} come valore.</li>
     *     <li>Popola la mappa "libri" con i titoli dei libri come chiave e i corrispondenti oggetti {@link Book} come valore.</li>
     *     <li>Itera attraverso i libri nella mappa:
     *         <ul>
     *             <li>Verifica se il campo "authors" del libro contiene la sottostringa di ricerca (in minuscolo) e se l'anno di pubblicazione del libro corrisponde all'anno specificato.</li>
     *             <li>Se entrambe le condizioni sono soddisfatte, aggiunge il libro alla lista "risultati".</li>
     *         </ul>
     *     </li>
     *     <li>Se la lista "risultati" è vuota, stampa un messaggio di errore indicante che nessun libro è stato trovato con l'autore e l'anno specificati.</li>
     *     <li>Se ci sono risultati, utilizza il metodo {@link LayOut#printBook_ID_TITOLO} per stampare i risultati in un layout specifico.</li>
     * </ol>
     *
     * @param Autore La sottostringa da cercare nei nomi degli autori dei libri.
     * @param Anno   L'anno di pubblicazione da cercare nei libri.
     */
    public static void cercaLibro_Per_Autore_e_Anno(String Autore, String Anno) {
        List<Book> risultati = new ArrayList<>();
        String atlc = Autore.toLowerCase();

        TreeMap<String, Book> libri = new TreeMap<>();

        for (Book libro : books) {
            libri.put(libro.getTitle(), libro);
        }

        for (Book libro : libri.values()) {
            if (libro.getAuthors().toLowerCase().contains(atlc) && libro.getPublishYear().contains(Anno)) {
                risultati.add(libro);
            }
        }

        if (risultati.isEmpty()) {
            System.out.println(LayOut.coloraErrore("Nessun libro trovato con l'autore '" + Autore + "' e anno '" + Anno + "'."));
        } else {

            LayOut.printBook_ID_TITOLO("Ricerca per autore: " + Autore + " , e Anno: " + Anno, risultati, LayOut.ANSI_YELLOW, LUNGHEZZA_LAYOUT);


        }

    }

}
