import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Estende la classe {@link Utente} per fornire funzionalità specifiche come la registrazione,
 * il salvataggio delle informazioni utente su un file CVS e l'accesso alle librerie personali.
 * @author Lilia
 * @author Sara
 */
public class UtenteRegistrato extends Utente {

    //CAMBI
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String email;
    private String userId;
    private String password;

    /**
     * Nome del file CSV contenente i dati degli utenti registrati.
     */
    private static final String DATI_UTENTI = "UtentiRegistrati.dati.csv";

    /**
     * Costruttore completo per l'inizializzazione di un nuovo utente registrato con tutte le informazioni.
     *
     * @param nome Il nome dell'utente.
     * @param cognome Il cognome dell'utente.
     * @param codiceFiscale Il codice fiscale dell'utente.
     * @param email L'indirizzo email dell'utente.
     * @param userId L'identificativo univoco dell'utente.
     * @param password La password dell'utente.
     */
    public UtenteRegistrato(String nome, String cognome, String codiceFiscale, String email, String userId, String password) {
        super(new Scanner(System.in));
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.userId = userId;
        this.password = password;
    }

    /**
     * Costruttore per l'inizializzazione di un utente registrato con uno scanner esistente.
     *
     * @param scanner Lo scanner da utilizzare per l'input dell'utente.
     */
    public UtenteRegistrato(Scanner scanner) {
        super();
        this.scanner = scanner;
    }

    /**
     * Costruttore predefinito senza parametri.
     * Utilizzato per creare un'istanza vuota di {@code UtenteRegistrato}.
     */
    public UtenteRegistrato() {
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Verifica se l'input fornito come parametro corrisponde a un determinato pattern anche esso fornito come parametro.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Compila il pattern fornito in un oggetto {@link Pattern}.</li>
     *     <li>Confronta l'input con il pattern utilizzando un {@link Matcher}.</li>
     *     <li>Restituisce true se l'input è valido, altrimenti false.</li>
     * </ol>
     *
     * @param input L'input da verificare.
     * @param pattern Il pattern al quale l'input deve corrispondere.
     * @return true se l'input è valido, false altrimenti.
     */
    private boolean isValidInput(String input, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * Richiede un input all'utente e lo convalida in base a un pattern specifico.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Richiede l'input all'utente tramite uno scanner.</li>
     *     <li>Verifica l'input in base al pattern fornito, uttilizzando il metodo {@link #isValidInput(String, String)}.</li>
     *     <li>Ripete il processo fino a tre tentativi se l'input non è valido.</li>
     *     <li>Se l'input è valido, viene restituito, altrimenti il programma termina.</li>
     * </ol>
     *
     * @param scanner Lo scanner per l'input dell'utente.
     * @param prompt Il messaggio da mostrare all'utente.
     * @param pattern Il pattern per la convalida dell'input.
     * @return L'input validato dell'utente o termina il programma se i tentativi falliscono.
     */
    private String convalidaInput(Scanner scanner, String prompt, String pattern) throws IOException {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (isValidInput(input, pattern)) {
                return input;
            }
            attempts++;
            System.out.println(LayOut.BORDER_VERTICAL + LayOut.coloraErrore("\tInput non valido. Riprova.") + "Tentativi rimanenti: " + (3 - attempts));
        }
        System.out.println(LayOut.BORDER_VERTICAL + LayOut.coloraErrore("\tNumero massimo di tentativi superato. Ritorno al Menu Principale."));
        System.out.println(LayOut.coloraInGiallo(LayOut.BORDER_BOTTOM_LEFT + LayOut.BORDER_HORIZONTAL));

       Utente utente = new Utente(scanner);
       utente.mostraMenuPrincipale();

        return null;
    }

    /**
     * Verifica se l'userId fornito è già in uso nel file CSV {@link #DATI_UTENTI}.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Legge il file CSV contenente i dati degli utenti registrati.</li>
     *     <li>Controlla se l'userId fornito è già presente nel file.</li>
     *     <li>Restituisce true se l'userId è già in uso, false altrimenti.</li>
     * </ol>
     *
     * @param userId L'userId da verificare.
     * @return true se l'userId è già in uso, false se è disponibile.
     */
    private boolean userIdNonUtilizzabile(String userId) {
        try (BufferedReader br = new BufferedReader(new FileReader(DATI_UTENTI))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);
                if (values.length > 4 && values[4].equals(userId)) {
                    return true; // userId già in uso
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
        return false; // userId disponibile
    }

    /**
     * Esegue la registrazione di un nuovo utente acquisendo le informazioni necessarie tramite input da scanner.
     * Le informazioni includono nome, cognome, codice fiscale, email, userId e password.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Acquisisce i dati dell'utente tramite input da scanner {@link #convalidaInput(Scanner, String, String)}. </li>
     *     <li>Verifica che l'userId scelto non sia già in uso {@link #userIdNonUtilizzabile(String)}.</li>
     *     <li>Salva i dati dell'utente in un file CSV {@link #salvaUtenteSuFile(UtenteRegistrato)}.</li>
     *     <li>Mostra un messaggio di conferma e reindirizza l'utente al menu principale per effettuare l'accesso.</li>
     * </ol>
     *
     * @param scanner Scanner utilizzato per l'input dell'utente.
     * @throws IOException Se si verifica un errore durante la scrittura su file.
     */
    public void registrazioneUtente(Scanner scanner) throws IOException {
        System.out.println(LayOut.coloraInGiallo(LayOut.BORDER_TOP_LEFT + LayOut.BORDER_HORIZONTAL.repeat(2)));
        System.out.println(LayOut.coloraInGiallo(LayOut.BORDER_VERTICAL + "\tRegistrazione Utente"));
        System.out.println(LayOut.BORDER_VERTICAL);

        String nome = convalidaInput(scanner, LayOut.BORDER_VERTICAL + "\tInserisci nome: ", "[A-Za-z]{2,50}");
        String cognome = convalidaInput(scanner, LayOut.BORDER_VERTICAL + "\tInserisci cognome: ", "[A-Za-z]{2,50}");
        String codiceFiscale = convalidaInput(scanner, LayOut.BORDER_VERTICAL + "\tInserisci codice fiscale: ", "[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]");
        String email = convalidaInput(scanner, LayOut.BORDER_VERTICAL + "\tInserisci email: ", "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

        String userId;
        do {
            userId = convalidaInput(scanner, LayOut.BORDER_VERTICAL + "\tScegli un nome utente (userId): ", "^[^;]+$");
            if (userIdNonUtilizzabile(userId)) {
                System.out.println(LayOut.BORDER_VERTICAL + LayOut.coloraErrore("\tQuesto userId è già in uso. Per favore, scegline un altro."));
            }
        } while (userIdNonUtilizzabile(userId));

        String password = convalidaInput(scanner, LayOut.BORDER_VERTICAL + "\tInserisci password (almeno 8 caratteri): ", "^[^;]{8,}$");

        UtenteRegistrato utenteRegistrato = new UtenteRegistrato(nome, cognome, codiceFiscale, email, userId, password);
        salvaUtenteSuFile(utenteRegistrato);
        System.out.println(LayOut.BORDER_VERTICAL);
        System.out.println(LayOut.coloraInVerde(LayOut.BORDER_VERTICAL + "\t>> Registrazione Effettuata con Successo!<<"));
        System.out.println(LayOut.coloraInGiallo(LayOut.BORDER_BOTTOM_LEFT + LayOut.BORDER_HORIZONTAL.repeat(2)));

        Utente utente = new Utente();
        utente.mostraMenuPrincipale();

    }


    /**
     * Salva i dati di un utente registrato su un file CSV.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Scrive i dati dell'utente fornito su un file CSV.</li>
     *     <li>Aggiunge una nuova linea al file per ogni utente registrato.</li>
     * </ol>
     *
     * @param utente L'utente registrato da salvare su file.
     * @throws IOException Se si verifica un errore durante la scrittura su file.
     */
    private void salvaUtenteSuFile(UtenteRegistrato utente) throws IOException {
        try (FileWriter csvWriter = new FileWriter(DATI_UTENTI , true)) {
            csvWriter.write(String.format("%s;%s;%s;%s;%s;%s%n",
                    utente.getNome(),
                    utente.getCognome(),
                    utente.getCodiceFiscale(),
                    utente.getEmail(),
                    utente.getUserId(),
                    utente.getPassword()));
        }
    }

    /**
     * Visualizza il menu principale per l'utente registrato e gestisce le scelte effettuate.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Mostra un menu con opzioni specifiche per gli utenti registrati.</li>
     *     <li>Gestisce le scelte dell'utente, come cercare libri, gestire librerie, valutare e consigliare libri, o effettuare il logout.</li>
     *     <li>Gestisce input non validi{@link #leggiSceltaUtenteConTentativi()} mostrando un messaggio di errore e ripetendo la richiesta fino a una scelta valida, nel caso di un intero non corrispondente a un operazione.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante l'interazione con i file.
     */
    public void mostraMenuUtenteRegistrato() throws IOException {
        while (continua) {
            String[] options = {
                    "1. Cerca libri",
                    "2. Le mie Librerie",
                    "3. Valuta Libro",
                    "4. Suggerisci Libro",
                    "0. Logout"
            };

            LayOut.printMenu("MENU UTENTE REGISTRATO", options, LayOut.ANSI_BLUE);
            int scelta = leggiSceltaUtenteConTentativi();
            UtenteRegistrato utenteRegistrato = UtenteRegistrato.caricaDaCSV(userId);


            if (scelta == -1) {
                return;
            }

            switch (scelta) {
                case 1:
                    cercaLibri();
                    mostraMenuUtenteRegistrato();
                    break;
                case 2:
                    mostraMenuLibrerie();
                    break;
                case 3:
                    Valutazione val = new Valutazione(utenteRegistrato);
                    val.valutazioneLibro(this.userId);
                    break;
                case 4:
                    ConsigliaLibri cl = new ConsigliaLibri(utenteRegistrato);
                    cl.consigliaLibri(utenteRegistrato);
                    break;
                case 0:
                    Utente utente = new Utente();
                    utente.mostraMenuPrincipale();
                    break;
                default:
                    System.out.println(LayOut.coloraErrore("Scelta non valida. Riprova."));
            }
        }
    }

    /**
     * Gestisce le opzioni relative alle librerie dell'utente, come visualizzare le librerie salvate, crearne di nuove, o tornare indietro.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Mostra un menu con opzioni per la gestione delle librerie.</li>
     *     <li>Permette di visualizzare, creare o gestire le librerie dell'utente.</li>
     *     <li>Gestisce input non validi{@link #leggiSceltaUtenteConTentativi()} mostrando un messaggio di errore e ripetendo la richiesta fino a una scelta valida, nel caso di un intero non corrispondente a un operazione.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante l'interazione con i file.
     */
    void mostraMenuLibrerie() throws IOException {
        String[] options = {
                "1. Visualizza Librerie",
                "2. Crea una nuova Libreria",
                "3. Elimina Libreria",
                "4. Torna Indietro"

        };

        UtenteRegistrato utenteRegistrato = UtenteRegistrato.caricaDaCSV(userId);

        Libreria libreria = new Libreria(utenteRegistrato);
        LayOut.printMenu("MENU UTENTE REGISTRATO", options, LayOut.ANSI_CYAN);
        while (continua) {


            int scelta = leggiSceltaUtenteConTentativi();
            if (scelta == -1) {
                return;
            }

            switch (scelta) {
                case 1:
                    libreria.visualizzaLibrerieSalvate(utenteRegistrato);
                    libreria.mostraMenuLibroInLibreria(utenteRegistrato);
                    break;
                case 2:
                    String nomeLib = libreria.inserisciNomeLibreria(utenteRegistrato);
                    libreria.registraLibreria(nomeLib);
                    libreria.mostraMenuLibroInLibreria(utenteRegistrato);
                    break;
                case 3:
                    libreria.eliminaLibreria(utenteRegistrato);
                case 4:
                    mostraMenuUtenteRegistrato();
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        }
    }

    /**
     * Carica i campi di un utente registrato dal file CSV in base all'userId.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Legge il file CSV contenente i dati degli utenti registrati {@link #DATI_UTENTI}.</li>
     *     <li>Ricerca l'utente in base all'userId fornito.</li>
     *     <li>Restituisce un'istanza di {@code UtenteRegistrato} con i dati trovati, o null se l'userId non esiste.</li>
     * </ol>
     *
     * @param userId L'identificativo univoco dell'utente da caricare.
     * @return Un'istanza di {@code UtenteRegistrato} con i dati caricati, o null se non trovato.
     */
    public static UtenteRegistrato caricaDaCSV(String userId) {
        try (BufferedReader br = new BufferedReader(new FileReader(DATI_UTENTI))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = parseCSVLine(line);
                if (values.length > 4 && values[4].equals(userId)) {
                    return new UtenteRegistrato(values[0], values[1], values[2], values[3], values[4], values[5]);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }
        return null;
    }

    /**
     * Analizza una linea CSV e la suddivide in un array di stringhe.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Divide la linea CSV in base al separatore ';'.</li>
     *     <li>Restituisce un array di stringhe contenenti i valori separati.</li>
     * </ol>
     *
     * @param line La linea CSV da analizzare.
     * @return Un array di stringhe con i valori della linea CSV.
     */
    public static String[] parseCSVLine(String line) {
        return line.split(";");
    }


}

