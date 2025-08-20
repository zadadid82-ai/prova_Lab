import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * La classe gestisce il processo di autenticazione degli utenti, verificando le loro credenziali (userId e password) confrontandole con i dati memorizzati in un file CSV.
 * Il nome del file CSV è memorizzato nella variabile costante DATI_UTENTI.
 * La classe fornisce metodi per richiedere e validare le credenziali degli utenti.
 * <p>
 * Il nome del file CSV utilizzato da questa classe è "UtentiRegistrati.dati.csv".
 * Ogni riga del file CSV rappresenta un utente e contiene le seguenti informazioni in ordine: Nome;Cognome;CodiceFiscale;Email;UserId;Password.
 * </p>
 *
 * <p>
 * La classe utilizza vari simboli per creare un'interfaccia utente basata su testo
 * con bordi e cornici, migliorando l'aspetto della console.
 * </p>
 *
 * @author Ali
 * @author Sara
 */


public class AutenticazioneUtente {

    /**
     * Nome del file CSV contenente i dati degli utenti registrati.
     */
    private static final String DATI_UTENTI = "UtentiRegistrati.dati.csv";

    /**
     * Costruttore vuoto della classe.
     * <p>
     * Viene utilizzato per creare un'istanza della classe.
     * </p>
     */
    public AutenticazioneUtente() {
    }

    /**
     * Verifica le credenziali dell'utente confrontando l'userId e la password forniti come parametri
     * con quelli presenti nel file CSV "UtentiRegistrati.dati.csv".
     * <p>Nel dettaglio:</p>
     * <p>
     * Utilizza un BufferedReader per leggere riga per riga del file CSV, definito nel seguente modo: Nome;Cognome;CodiceFiscale;Email;UserId;Password.
     * Salva man mano i dati di una riga in un array di stringhe.
     * Controlla che le stringhe in posizione 4 e 5 dell'array  siano uguali al parametri forniti.
     * </p>
     *
     * @param userId   L'ID utente da autenticare.
     * @param password La password associata all'ID utente.
     * @return true se l'autenticazione è riuscita, false altrimenti.
     */
    public boolean autentica(String userId, String password) {
        boolean userTrovato = false;
        String correctPassword = "";

        try (BufferedReader br = new BufferedReader(new FileReader(DATI_UTENTI))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values[4].equals(userId)) {
                    userTrovato = true;
                    correctPassword = values[5];
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Errore durante la lettura del file");
        }

        if (userTrovato) {
            return correctPassword.equals(password);
        } else {
            return false;
        }
    }

    /**
     * Gestisce il processo di autenticazione dell'utente.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *   <li>Chiede all'utente di inserire lo userId e la password.</li>
     *   <li>Verifica le credenziali tramite il metodo {@link #autentica(String, String)}.</li>
     *   <li>Se l'autenticazione ha successo, carica i dati dell'utente da un file CSV con {@link UtenteRegistrato#caricaDaCSV(String)}
     *       e mostra il menu utente registrato con {@link UtenteRegistrato#mostraMenuUtenteRegistrato()}.</li>
     *   <li>In caso di autenticazione fallita, mostra un messaggio di errore e ritorna al menu principale con {@link Utente#mostraMenuPrincipale()}.</li>
     * </ol>
     *
     * @throws IOException se si verifica un errore durante l'input/output.
     */
    public void accedi() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println(LayOut.BORDER_TOP_LEFT + LayOut.coloraInGiallo(LayOut.BORDER_HORIZONTAL.repeat(50)) + LayOut.coloraInGiallo(LayOut.BORDER_TOP_RIGHT));
        System.out.println(LayOut.BORDER_VERTICAL + LayOut.centraTesto("ACCEDI", 50) + LayOut.coloraInGiallo(LayOut.BORDER_VERTICAL));

        System.out.print(LayOut.BORDER_VERTICAL + " Inserisci il tuo userId: ");
        String userId = scanner.nextLine().trim();

        System.out.print(LayOut.BORDER_VERTICAL + " Inserisci la tua password: ");
        String password = scanner.nextLine().trim();


        boolean isAuthenticated = autentica(userId, password);

        if (isAuthenticated) {
            System.out.println(LayOut.BORDER_VERTICAL + LayOut.coloraInGiallo(LayOut.centraTesto("Autenticazione riuscita! Benvenuto/a " + userId, 50)));
            System.out.println(LayOut.BORDER_BOTTOM_LEFT + LayOut.BORDER_HORIZONTAL.repeat(50));
            UtenteRegistrato utenteRegistrato = UtenteRegistrato.caricaDaCSV(userId);
            assert utenteRegistrato != null;
            utenteRegistrato.mostraMenuUtenteRegistrato();

        } else {
            System.out.println(LayOut.BORDER_VERTICAL + LayOut.coloraErrore(LayOut.centraTesto("UserId o password errati. Riprova.", 50)));
            System.out.println(LayOut.BORDER_BOTTOM_LEFT + LayOut.BORDER_HORIZONTAL.repeat(50));
            Utente utente = new Utente();
            utente.mostraMenuPrincipale();
        }
    }

}
