import java.io.*;
import java.util.*;

/**
 * La classe estende la classe {@code Libreria} e gestisce la funzionalità
 * di suggerimento di libri correlati a un libro specifico presente nella libreria di un utente.
 * Gli utenti possono consigliare fino a tre libri per ciascun libro che possiedono nelle loro librerie, e tali suggerimenti
 * vengono memorizzati in un file CSV.
 *
 * <p>Le principali funzionalità offerte da questa classe includono:</p>
 * <ul>
 *   <li>Aggiunta di suggerimenti per un libro specifico.</li>
 *   <li>Verifica del numero di suggerimenti già inseriti per un determinato libro.</li>
 *   <li>Salvataggio dei suggerimenti su file CSV.</li>
 *   <li>Conteggio e visualizzazione dei suggerimenti effettuati per un libro specifico.</li>
 * </ul>
 *
 * <p>Il file di dati utilizzato per memorizzare i suggerimenti è {@value #CONSIGLI_FILE}.
 * Gli utenti possono consigliare un massimo di {@value #MAX_SUGGERIMENTI} libri per ciascun libro.</p>
 *
 * @author Ali
 * @author Sara
 */
public class ConsigliaLibri  {
    /**
     * Il nome del file CSV in cui vengono salvati i suggerimenti di libri.
     */
    private static final String CONSIGLI_FILE = "ConsigliLibri.dati.csv";
    private static final int MAX_SUGGERIMENTI = 3;
    private static final int LUNGHEZZA_LAYOUT = 120;
    private  Libreria libreria ;

    /**
     * L'utente registrato che sta effettuando l'operazione di suggerimento di libri.
     */
    private  UtenteRegistrato utenteRegistrato;

    /**
     * Costruttore della classe {@code ConsigliaLibri}.
     *
     * @param utenteRegistrato L'utente registrato per cui viene creata l'istanza della classe.
     */
    public ConsigliaLibri(UtenteRegistrato utenteRegistrato) {
        this.utenteRegistrato = utenteRegistrato;
        this.libreria = new Libreria(utenteRegistrato);
    }

    /**
     * Consiglia libri per un libro specifico e salva i suggerimenti nel file.
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>
     *         Chiede all'utente di inserire l'ID del libro per il quale
     *         desidera consigliare altri libri.
     *     </li>
     *     <li>
     *         Utilizza {@link Libreria#isLibroInLibrerie(int, String)} per assicurarsi
     *         che il libro sia presente nelle librerie dell'utente. Se non è presente, il metodo termina
     *         con un avviso.
     *     </li>
     *     <li>
     *         Verifica il numero di suggerimenti già inseriti
     *         con {@link #haGiaSuggeritoDeiLibri(String, String)}. Se l'utente ha raggiunto il massimo
     *         di suggerimenti, il metodo termina con un avviso.
     *     </li>
     *     <li>
     *         Permette all'utente di aggiungere fino a tre libri consigliati,
     *         evitando duplicati. Usa {@link Libreria#leggiSceltaUtente()} per la lettura dell'input.
     *     </li>
     *     <li>
     *         Salva i suggerimenti nel file usando {@link #salvaSuggerimenti(String, List)}
     *         e mostra i titoli dei libri consigliati.
     *     </li>
     * </ol>
     *
     *
     * @param utente L'utente che sta consigliando i libri.
     * @throws IOException Se si verifica un errore durante la lettura o scrittura dei file.
     */

    public void consigliaLibri(UtenteRegistrato utente) throws IOException {

        String userId = utente.getUserId();
        Scanner scanner = new Scanner(System.in);
        List<String> libriConsigliati = new ArrayList<>();

        String margine = " ".repeat(4); // margine per il LayOut


        System.out.println(LayOut.coloraInViola(LayOut.BORDER_TOP_LEFT + LayOut.BORDER_HORIZONTAL.repeat(100) + LayOut.BORDER_TOP_RIGHT));
        System.out.print(LayOut.BORDER_VERTICAL + "Inserisci l'ID del libro per il quale vuoi consigliare altri libri: ");

        // Per assicurarsi che l'utente inserisca un id valido
        int sceltaUtente = libreria.leggiSceltaUtente();
        // trasforma l'intero id in una stringa
        String libroId = String.valueOf(sceltaUtente);

        // Verifica se il libro è presente nelle librerie dell'utente
        int id = Integer.parseInt(libroId);
        if (!libreria.isLibroInLibrerie(id, userId)) {
            System.out.println(LayOut.BORDER_VERTICAL + margine + "Il libro con ID " + libroId + " non è presente nelle tue librerie.");
            System.out.println(LayOut.BORDER_BOTTOM_LEFT + LayOut.BORDER_HORIZONTAL.repeat(60));
            return; // se il libro non è presente esce dal metodo.
        }

        // Verifica quanti suggerimenti l'utente ha già inserito
        int suggerimentiEsistenti = haGiaSuggeritoDeiLibri(userId, libroId);
        if (suggerimentiEsistenti == MAX_SUGGERIMENTI) {
            System.out.println(LayOut.BORDER_VERTICAL + margine + "Hai già suggerito tre libri per il libro con ID " + libroId + ".");
            System.out.println(LayOut.BORDER_BOTTOM_LEFT + LayOut.BORDER_HORIZONTAL.repeat(60));
            return;
        }

        System.out.println(LayOut.BORDER_VERTICAL);
        System.out.println(LayOut.BORDER_VERTICAL + LayOut.centraTesto("Consiglia libri per il libro: " + LayOut.coloraInViola(BookRecommender.getTitoloLibroById(id)), 102));

        for (int i = suggerimentiEsistenti; i < MAX_SUGGERIMENTI; i++) {
            System.out.print(LayOut.BORDER_VERTICAL + margine + "Inserisci l'ID del libro consigliato (" + (i + 1) + "/" + MAX_SUGGERIMENTI + "): ");
            int idLibro = libreria.leggiSceltaUtente();
            String idConsigliato = String.valueOf(idLibro);

            if (libriConsigliati.contains(idConsigliato)) {
                System.out.println(LayOut.BORDER_VERTICAL + margine + LayOut.coloraErrore("Hai già consigliato il libro con ID " + idConsigliato + " per questo libro."));
                i--;
                continue;
            }

            if (idConsigliato.equals(libroId)) {
                System.out.println(LayOut.BORDER_VERTICAL + margine + LayOut.coloraErrore("Scelta non valida "));
                i--;
                continue;
            }

            if (!BookRecommender.getTitoloLibroById(Integer.parseInt(idConsigliato)).trim().equals("ID non trovato")) {
                libriConsigliati.add(idConsigliato);

            }

            if (libriConsigliati.size() + suggerimentiEsistenti >= MAX_SUGGERIMENTI) {
                System.out.println(LayOut.BORDER_VERTICAL + margine + "Hai raggiunto il numero massimo di suggerimenti.");
                break;
            }

            System.out.print(LayOut.BORDER_VERTICAL + margine + "Vuoi aggiungere un altro consiglio? (1 = Sì, 0 = No): ");
            String scelta = scanner.nextLine().trim();
            if (scelta.equals("0")) {
                break;
            }
            if (!scelta.equals("1") && !scelta.equals("0")) {
                System.out.println(LayOut.BORDER_VERTICAL + LayOut.coloraErrore("\tInput non valido. Chiusura dell'operazione."));
                break;
            }
        }

        // Salva i suggerimenti nel file
        salvaSuggerimenti(libroId, libriConsigliati);

        // Stampa i titoli dei libri suggeriti
        System.out.println(LayOut.BORDER_VERTICAL);
        System.out.println(LayOut.BORDER_VERTICAL + LayOut.centraTesto(LayOut.coloraInViola("Libri Consigliati:"), 102));
        for (String idConsigliato : libriConsigliati) {
            if (BookRecommender.getTitoloLibroById(Integer.parseInt(idConsigliato)).trim().equals("ID non trovato")) {
                System.out.println("Libro con ID " + idConsigliato + " non trovato.");
            } else
                System.out.println(LayOut.BORDER_VERTICAL + margine + LayOut.coloraId("ID: ") + idConsigliato + ",\t" + LayOut.coloraTitolo("TITOLO: ") + BookRecommender.getTitoloLibroById(Integer.parseInt(idConsigliato)));


        }
        System.out.println(LayOut.BORDER_BOTTOM_LEFT + LayOut.BORDER_HORIZONTAL.repeat(90));

    }

    /**
     * Conta quanti suggerimenti sono stati inseriti per un libro specifico da un utente.
     * <p>
     * Questo metodo legge il file di cui nome è memorizzato nella costante CONSIGLI_FILE e conta quante volte l'utente con
     * ha suggerito libri per  il libro specificato. </p>
     * <ul>
     *     <li>Utilizza un BufferedReader per leggere il file riga per riga.</li>
     *     <li>Ogni riga è divisa in campi usando ";". </li>
     *     <li>Conta quante volte l'userId e l'id del libro compaiono in determinate posizioni all'interno del file</li>
     * </ul>
     *
     *
     * @param userId  L'userId dell'utente per il quale contare i suggerimenti.
     * @param libroId L'ID del libro per il quale contare i suggerimenti.
     * @return Il numero di suggerimenti fatti dall'utente per il libro specificato.
     * @throws IOException Se si verifica un errore durante la lettura del file.
     */
    private static int haGiaSuggeritoDeiLibri(String userId, String libroId) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(CONSIGLI_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields[0].trim().equals(userId) && fields[1].trim().equals(libroId)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Salva i suggerimenti di libri per un libro specifico nel file CSV.
     * <p>
     * Questo metodo apre il file {@value #CONSIGLI_FILE} in modalità di scrittura in append e scrive ogni suggerimento fornito dall'utente
     * come una nuova riga nel file. Ogni riga contiene l'ID dell'utente, l'ID del libro per cui vengono aggiunti i suggerimenti, e l'ID del libro consigliato.
     * </p>
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Apre il file {@value #CONSIGLI_FILE} in modalità di scrittura in append.</li>
     *   <li>Per ogni ID libro consigliato nella lista {@code libriConsigliati}, scrive una riga nel file con i seguenti dati:
     *       <ul>
     *         <li>L'userId dell'utente che ha effettuato il commento.</li>
     *         <li>L'ID del libro per cui vengono forniti i suggerimenti, passato come parametro {@code libroId}.</li>
     *         <li>L'ID del libro consigliato, ottenuto dalla lista {@code libriConsigliati}.</li>
     *       </ul>
     *   </li>
     * </ul>
     *
     * @param libroId          L'ID del libro per il quale vengono aggiunti i suggerimenti.
     * @param libriConsigliati La lista degli ID dei libri consigliati.
     * @throws IOException Se si verifica un errore durante la scrittura nel file.
     */
    private void salvaSuggerimenti(String libroId, List<String> libriConsigliati) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONSIGLI_FILE, true))) {
            for (String idConsigliato : libriConsigliati) {
                writer.write(utenteRegistrato.getUserId() + ";" + libroId + ";" + idConsigliato);
                writer.newLine();
            }
        }
    }

    /**
     * Recupera e restituisce i suggerimenti di libri associati a un libro specifico, inclusi i conteggi di quanto ciascun libro è stato suggerito.
     * <p>
     * Questo metodo legge il file di suggerimenti {@link #CONSIGLI_FILE} per determinare quali libri sono stati suggeriti per un libro con ID specificato.
     * Utilizza una {@link HashMap} per tenere traccia del numero di volte che ogni libro è stato suggerito.
     * Se non ci sono suggerimenti per il libro specificato, il metodo stampa un messaggio informativo e restituisce un array vuoto.
     * Altrimenti, restituisce un array di stringhe formattate che includono l'ID e il titolo di ciascun libro suggerito,
     * insieme al numero di volte che è stato suggerito.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *   <li>Apre il file <code>CONSIGLI_FILE</code> per la lettura.</li>
     *   <li> Legge ogni riga del file e verifica se l'ID del libro suggerito corrisponde al parametro libroId.
     *       Se sì, aggiorna il conteggio di suggerimenti per ciascun libro suggerito nella {@link HashMap}.</li>
     *   <li> Se ci sono suggerimenti, itera sulla {@link HashMap} per formattare ciascun suggerimento
     *       con l'ID del libro suggerito, il titolo ottenuto tramite il metodo {@link BookRecommender#getTitoloLibroById}, e il numero di volte che è stato suggerito.</li>
     *   <li>Converte la lista di suggerimenti formattati in un array di stringhe per il ritorno.</li>
     * </ol>
     *
     *
     * @param libroId ID del libro per il quale si desidera ottenere i suggerimenti.
     * @return Un array di stringhe dove ogni elemento rappresenta un suggerimento formattato con l'ID del libro, il titolo,
     * e il numero di volte che il libro è stato suggerito. Se non ci sono suggerimenti, viene restituito un array vuoto.
     */
    public static String[] suggerimentiPerLibro(String libroId) {
        ArrayList<String> suggerimenti = new ArrayList<>();
        String[] libriSuggeriti = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(CONSIGLI_FILE))) {
            // Mappa per contare quanti suggerimenti sono stati dati per ciascun libro suggerito
            Map<String, Integer> conteggioSuggerimenti = new HashMap<>();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                // Controlla se il suggerimento è per il libro specificato
                if (parts[1].trim().equals(libroId)) {
                    String idSuggerito = parts[2].trim();
                    // Incrementa il conteggio dei suggerimenti per l'ID suggerito
                    conteggioSuggerimenti.put(idSuggerito, conteggioSuggerimenti.getOrDefault(idSuggerito, 0) + 1);
                }
            }

            if (conteggioSuggerimenti.isEmpty()) {
                System.out.println(LayOut.centraTesto("Nessun libro è stato suggerito per il libro con ID: " + libroId, LUNGHEZZA_LAYOUT));
            } else {
                // Elenca e formatta i suggerimenti raccolti
                for (Map.Entry<String, Integer> entry : conteggioSuggerimenti.entrySet()) {

                    String idSuggerito = entry.getKey();
                    int count = entry.getValue();
                    int id = Integer.parseInt(idSuggerito);
                    suggerimenti.add(LayOut.coloraId("ID: ") + id + "\t" + LayOut.coloraTitolo("Titolo: ") + BookRecommender.getTitoloLibroById(Integer.parseInt(idSuggerito)) + "\t" + LayOut.coloraInGiallo(" N° di volte suggerito:  ") + count);
                }
                // Converte la lista dei suggerimenti in un array di stringhe
                libriSuggeriti = new String[suggerimenti.size()];
                for (int i = 0; i < suggerimenti.size(); i++) {
                    libriSuggeriti[i] = suggerimenti.get(i);
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella scrittura del file " + CONSIGLI_FILE);
        }
        return libriSuggeriti;
    }
}
