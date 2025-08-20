import java.io.IOException;
import java.util.Scanner;

/**
 * La classe gestisce l'interazione dell'utente ospite con il sistema.
 * Estende la classe {@link Utente} per fornire funzionalità specifiche agli utenti non registrati,
 * come la possibilità di cercare libri o registrarsi.
 * @author Amuer
 * @author Sara
 */
public class UtenteOspite extends Utente {

    /**
     * Scanner utilizzato per leggere l'input dell'utente dalla console.
     * Questo campo è final e viene inizializzato nel costruttore.
     */
    private final Scanner scanner;

    /**
     * Costruttore della classe  che inizializza il campo scanner con un'istanza specificata.
     * Estende la classe {@link Utente} e permette all'utente ospite di interagire con il sistema.
     *
     * @param scanner L'istanza di {@link Scanner} utilizzata per leggere l'input dell'utente ospite.
     */
    public UtenteOspite(Scanner scanner) {
        super();
        this.scanner = scanner;
    }


    /**
     * Visualizza il menu principale per l'utente ospite e gestisce le scelte effettuate.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Mostra un menu con opzioni specifiche per gli utenti ospiti.</li>
     *     <li>Gestisce le scelte dell'utente, consentendogli di cercare libri, registrarsi, o tornare al menu principale.</li>
     *     <li>Gestisce input non validi{@link #leggiSceltaUtenteConTentativi()} mostrando un messaggio di errore e ripetendo la richiesta fino a una scelta valida, nel caso di un intero non corrispondente a un operazione.</li>
     * </ol>
     *
     * @throws IOException Se si verifica un errore durante l'interazione con i file.
     */
    public void mostraMenuUtenteOspite() throws IOException {
        while (continua) {
            String[] options = {
                    "1. Cerca libri",
                    "2. Registrati",
                    "0. Torna al menu principale"
            }
            ;
            LayOut.printMenu("MENU UTENTE OSPITE", options, LayOut.ANSI_CYAN);
            int scelta = leggiSceltaUtenteConTentativi();

            if (scelta == -1) {
                return; // Esce dal metodo se ci sono troppi tentativi falliti
            }

            switch (scelta) {
                case 1:
                    cercaLibri();
                    mostraMenuUtenteOspite();
                    break;
                case 2:
                    UtenteRegistrato utenteRegistrato = new UtenteRegistrato();
                    utenteRegistrato.registrazioneUtente(scanner);
                    break;
                case 0:
                    Utente utente = new Utente(scanner);
                    utente.mostraMenuPrincipale();
                    break;
                default:
                    System.out.println(LayOut.ANSI_RED + LayOut.ANSI_BOLD + "Scelta non valida. Riprova." + LayOut.ANSI_RESET);
            }
        }
    }

}























