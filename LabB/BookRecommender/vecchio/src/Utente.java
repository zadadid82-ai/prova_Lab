import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * La classe gestisce la visualizzazione e l'interazione dell'utente con il programma.
 *
 * <p>Nel dettaglio:</p>
 * <ul>
 *     <li>Fornisce metodi per mostrare il menu principale, consentendo all'utente di accedere, registrarsi o continuare come ospite.</li>
 *     <li>Include funzionalità di ricerca libri per titolo, autore, autore e anno, e ID.</li>
 *     <li>Gestisce le selezioni dell'utente, validando l'input e offrendo la possibilità di visualizzare i dettagli dei libri trovati.</li>
 *     <li>Utilizza una serie di sottomenu per ulteriori dettagli e operazioni sui libri, come la visualizzazione delle valutazioni.</li>
 *     <li>Controlla gli input dell'utente con un massimo di tre tentativi, gestendo eventuali eccezioni legate all'input non valido.</li>
 * </ul>
 *
 * <p>Viene utilizzato un oggetto {@link Scanner} per la lettura dell'input dell'utente, che può essere passato al costruttore o creato internamente.</p>
 *
 * @author Lilia
 * @author Sara
 *
 */
public class Utente {

    /**
     * Scanner utilizzato per leggere l'input dell'utente dalla console.
     */
    Scanner scanner;
    /**
     * Flag che indica se il ciclo del menu principale deve continuare o meno.
     */
    boolean continua = true;

    /**
     * Costruttore della classe Utente che inizializza il campo scanner con un'istanza specificata.
     *
     * @param scanner L'istanza di {@link Scanner} utilizzata per leggere l'input dell'utente.
     */
    public Utente(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Costruttore di default della classe Utente che inizializza il campo scanner con un'istanza di {@link Scanner} legata a {@code System.in}.
     */
    public Utente() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Mostra il menu principale all'utente e gestisce le sue scelte.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Visualizza le opzioni del menu principale: accedi come utente registrato, continua come ospite, registrati o esci.</li>
     *     <li>In base alla scelta dell'utente, esegue l'operazione corrispondente, come l'accesso, la registrazione, o l'uscita dal programma.</li>
     *     <li>Gestisce input non validi{@link #leggiSceltaUtenteConTentativi()} mostrando un messaggio di errore e ripetendo la richiesta fino a una scelta valida, nel caso di un intero non corrispondente a un operazione.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante le operazioni di input/output.
     */
    public void mostraMenuPrincipale() throws IOException {
        while (continua) {
            String[] options = {
                    "1. Accedi come utente registrato",
                    "2. Continua come ospite",
                    "3. Registrati",
                    "4. Esci"
            };
            LayOut.printMenu("MENU PRINCIPALE", options, LayOut.ANSI_CYAN);

            int scelta = leggiSceltaUtenteConTentativi();
            if (scelta == -1) scelta = 4; // chiudi il programma


            switch (scelta) {
                case 1:
                    AutenticazioneUtente autenticazioneUtente = new AutenticazioneUtente();
                    autenticazioneUtente.accedi();
                    break;
                case 2:
                    UtenteOspite utenteOspite = new UtenteOspite(scanner);
                    utenteOspite.mostraMenuUtenteOspite();
                    break;
                case 3:
                    UtenteRegistrato utenteRegistrato = new UtenteRegistrato(scanner);
                    utenteRegistrato.registrazioneUtente(scanner);
                    break;
                case 4:
                    continua = false;
                    LayOut.stampaSaluto(" ".repeat(15) + "Uscita Effettuata" + "\n" + " ".repeat(17) + "Arrivederci!");
                    System.exit(0);
                    break;
                default:
                    LayOut.stampaErrore("Scelta non valida. Riprova.");
            }
        }
    }

    /**
     * Mostra il menu di ricerca dei libri e gestisce le scelte dell'utente.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Visualizza le opzioni per cercare un libro per titolo, autore, autore e anno, o ID.</li>
     *     <li>In base alla scelta, richiama il metodo corrispondente per eseguire la ricerca.</li>
     *     <li>Gestisce input non validi{@link #leggiSceltaUtenteConTentativi()} mostrando un messaggio di errore e ripetendo la richiesta fino a una scelta valida, nel caso di un intero non corrispondente a un operazione.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante le operazioni di input/output.
     */
    public void cercaLibri() throws IOException {
        String[] options = {
                "1. Cerca Libro Per Titolo",
                "2. Cerca Libro Per Autore",
                "3. Cerca Libro Per Autore e Anno",
                "4. Cerca libro per ID"
        };

        LayOut.printMenu("CERCA LIBRI", options, LayOut.ANSI_YELLOW);

        int scelta = leggiSceltaUtenteConTentativi();
        if (scelta == -1) return;

        switch (scelta) {
            case 1:
                cercaLibroPerTitolo();
                break;
            case 2:
                cercaLibroPerAutore();
                break;
            case 3:
                cercaLibroPerAutoreEAnno();
                break;
            case 4:
                visualizzaLibroPerID();
                mostraSottomenuDettagliVisualizzaLibro();
                break;
            default:
                LayOut.stampaErrore("Scelta non valida. Riprova.");
                cercaLibri();
        }
    }

    /**
     * Consente all'utente di cercare un libro per titolo.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Chiede all'utente di inserire il titolo del libro.</li>
     *     <li>Utilizza {@link BookRecommender#cercaLibro_Per_Titolo(String)} per cercare i libri corrispondenti.</li>
     *     <li>Mostra un sottomenu per visualizzare i dettagli del libro trovato (inserendo l'ID del libro){@link #mostraSottomenuVisualizzaLibro()}.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante le operazioni di input/output.
     */
    private void cercaLibroPerTitolo() throws IOException {
        System.out.print("Inserire il Titolo > ");
        String titolo = scanner.nextLine();
        BookRecommender.cercaLibro_Per_Titolo(titolo);
        mostraSottomenuVisualizzaLibro();
    }

    /**
     * Consente all'utente di cercare un libro per autore.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Chiede all'utente di inserire il nome dell'autore.</li>
     *     <li>Utilizza {@link BookRecommender#cercaLibro_Per_Autore(String)} per cercare i libri corrispondenti.</li>
     *     <li>Mostra un sottomenu per visualizzare i dettagli del libro trovato (inserendo l'ID del libro){@link #mostraSottomenuVisualizzaLibro()}.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante le operazioni di input/output.
     */
    private void cercaLibroPerAutore() throws IOException {
        System.out.print("Inserire l'Autore > ");
        String autore = scanner.nextLine();
        BookRecommender.cercaLibro_Per_Autore(autore);
        mostraSottomenuVisualizzaLibro();
    }

    /**
     * Consente all'utente di cercare un libro per autore e anno di pubblicazione.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Chiede all'utente di inserire il nome dell'autore e l'anno di pubblicazione.</li>
     *     <li>Utilizza {@link BookRecommender#cercaLibro_Per_Autore_e_Anno(String, String)} per cercare i libri corrispondenti.</li>
     *     <li>Mostra un sottomenu per visualizzare i dettagli del libro trovato (inserendo l'ID del libro){@link #mostraSottomenuVisualizzaLibro()}.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante le operazioni di input/output.
     */
    private void cercaLibroPerAutoreEAnno() throws IOException {
        System.out.print("Inserire l'Autore > ");
        String autore = scanner.nextLine();
        System.out.print("Inserire l'Anno > ");
        String anno = scanner.nextLine();
        BookRecommender.cercaLibro_Per_Autore_e_Anno(autore, anno);
        mostraSottomenuVisualizzaLibro();
    }

    /**
     * Mostra un sottomenu che consente all'utente di visualizzare i dettagli ci un libro per ID o tornare al menu principale.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Visualizza le opzioni per visualizzare un libro per ID o tornare al menu principale.</li>
     *     <li>In base alla scelta, esegue l'operazione corrispondente.</li>
     *     <li>Gestisce input non validi{@link #leggiSceltaUtenteConTentativi()} mostrando un messaggio di errore e ripetendo la richiesta fino a una scelta valida, nel caso di un intero non corrispondente a un operazione.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante le operazioni di I/O.
     */
    private static void mostraSottomenuVisualizzaLibro() throws IOException {
        String[] options = {
                "1. Visualizza Libro (per ID)",
                "2. Torna al Menu principale"
        };
        LayOut.printMenu("VISUALIZZA LIBRO", options, LayOut.ANSI_BLUE);

        int scelta = leggiSceltaUtenteConTentativi();
        if (scelta == -1) return;

        switch (scelta) {
            case 1:
                visualizzaLibroPerID();
                mostraSottomenuDettagliVisualizzaLibro();
                break;
            case 2:
                // mostraMenuPrincipale(); //non va bene se a chiamarlo è un utenete Registrato
                LayOut.stampaTornaIndietro("Ritorno al menu principale");
                break;
            default:
                LayOut.stampaErrore("Scelta non valida. Riprova.");
                mostraSottomenuVisualizzaLibro();
        }
    }

    /**
     * Mostra un sottomenu per visualizzare le valutazioni di un libro nel dettaglio o tornare al menu principale.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Chiede all'utente di inserire l'ID del libro per visualizzare le valutazioni dettagliate.</li>
     *     <li>Se l'ID è valido, visualizza le valutazioni utilizzando {@link Valutazione#visualizzaValutazioneCompleta(int)}.</li>
     *     <li>Gestisce input non validi{@link #leggiSceltaUtenteConTentativi()} mostrando un messaggio di errore e ripetendo la richiesta fino a una scelta valida, nel caso di un intero non corrispondente a un operazione.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante le operazioni di I/O.
     */
    public static void mostraSottomenuDettagliVisualizzaLibro() throws IOException {
        String[] options = {
                "1. Visualizza le valutazione nel dettaglio",
                "2. Torna al Menu principale"
        };
        LayOut.printMenu("VISUALIZZA LIBRO", options, LayOut.ANSI_BLUE);


        int scelta = leggiSceltaUtenteConTentativi();
        if (scelta == -1) return;

        switch (scelta) {
            case 1:
                LayOut.stampaId("\nInserire ID del libro > ");
                int idLibro = leggiSceltaUtenteConTentativi();
                if (idLibro == -1) {
                    return; // Esce dal metodo se ci sono troppi tentativi falliti
                }
                Valutazione.visualizzaValutazioneCompleta(idLibro);
                break;
            case 2:
                //mostraMenuPrincipale(); //non va bene se a chiamarlo è un utenete Registrato
                LayOut.stampaTornaIndietro("Ritorno al menu peincipale");
                break;
            default:
                LayOut.stampaErrore("Scelta non valida. Riprova.");
                mostraSottomenuVisualizzaLibro();
        }
    }

    /**
     * Visualizza le informazioni di un libro cercato tramite ID.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Chiede all'utente di inserire l'ID del libro da visualizzare.</li>
     *     <li>Recupera il libro tramite {@link BookRecommender#getBookById(int)}.</li>
     *     <li>Se il libro è trovato, ne visualizza i dettagli; altrimenti, mostra un messaggio di errore.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante le operazioni di I/O.
     */
    public static void visualizzaLibroPerID() throws IOException {
        LayOut.stampaId("Inserire ID del libro > ");
        int idLibro = leggiSceltaUtenteConTentativi();
        if (idLibro == -1) return;

        Book book = BookRecommender.getBookById(idLibro);
        if (book != null) {
            book.visualizzaLibro();
        } else {
            LayOut.stampaErrore("Libro non trovato.");
        }

    }

    /**
     * Legge la scelta dell'utente con un massimo di tre tentativi in caso di input non valido.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Richiede all'utente di inserire un numero intero corrispondente alla sua scelta.</li>
     *     <li>Gestisce eventuali {@link InputMismatchException} per input non validi, consentendo fino a tre tentativi.</li>
     *     <li>Se l'utente supera il numero massimo di tentativi, restituisce -1 per indicare il fallimento.</li>
     * </ol>
     *
     * @return La scelta dell'utente come numero intero o -1 se i tentativi sono esauriti.
     */
    public static int leggiSceltaUtenteConTentativi() {
        Scanner scanner = new Scanner(System.in);
        int tentativi = 0;
        while (tentativi < 3) {
            try {
                System.out.print(LayOut.coloraInVerde("Inserisci la tua scelta >> "));
                int scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma il carattere di ritorno a capo
                return scelta;
            } catch (InputMismatchException e) {
                tentativi++;
                LayOut.stampaErrore("Input non valido. Per favore, inserisci un numero intero. Tentativi rimanenti: " + (3 - tentativi));
                scanner.next(); // Consuma l'input non valido
            }
        }
        LayOut.stampaErrore("Hai superato il numero massimo di tentativi. Torna al menu precedente.");
        return -1; // Indica un fallimento
    }

}
