import java.io.*;
import java.util.*;

/**
 * La classe estende la classe {@code Libreria} e gestisce il processo di valutazione dei libri presenti all'interno di una libreria.
 * Fornisce metodi per impostare valutazioni dettagliate per vari criteri e per visualizzare e aggregare queste valutazioni.
 * I criteri di valutazione includono stile, contenuto, gradevolezza, originalità, edizione e voto finale.
 * Le valutazioni sono memorizzate in un file CSV e possono essere visualizzate e aggregate per analisi.
 *
 * <p>Questa classe utilizza i seguenti file CSV:</p>
 * <ul>
 *   <li>{@code Librerie.dati.csv} - Contiene le informazioni sulle librerie degli utenti.</li>
 *   <li>{@code ValutazioniLibri.dati.csv} - Contiene le valutazioni dei libri.</li>
 * </ul>
 *
 * @author Ali
 * @author Sara
 */


public class Valutazione  {

    private int stile;
    private String stileNote;
    private int contenuto;
    private String contenutoNote;
    private int gradevolezza;
    private String gradevolezzaNote;
    private int originalita;
    private String originalitaNote;
    private int edizione;
    private String edizioneNote;
    private double votofinaly;
    private String votofinalyNote;

    private Libreria libreria;

    private static final String VALUTAZIONE_FILE = "ValutazioniLibri.dati.csv";
    private UtenteRegistrato utenteRegistrato;
    private static final int LUNGHEZZA_LAYOUT = 120;

    public Valutazione() {
        this.libreria = new Libreria();
    }

    public Valutazione(UtenteRegistrato utenteRegistrato) {
        this.utenteRegistrato = utenteRegistrato;
        this.libreria = new Libreria(utenteRegistrato);
    }


    /**
     * Verifica che l'intero passato come parametro sia compreso tra 1 e 5.
     * <p>Se l'intero non è compreso tra 1 e 5, viene lanciata un'eccezione {@link IllegalArgumentException} con un messaggio di errore.</p>
     *
     * @param valutazione intero da controllare.
     * @return int valutazione
     * @throws IllegalArgumentException Se la valutazione non è compresa tra 1 e 5.
     */
    private static int convalidareValutazione(int valutazione) {
        if (valutazione < 1 || valutazione > 5) {
            throw new IllegalArgumentException(LayOut.BORDER_VERTICAL + "Il punteggio deve essere compreso tra 1 e 5");
        }
        return valutazione;
    }

    /**
     * Verifica che la nota passata come parametro non superi i 256 caratteri.
     * <p>Se la nota supera i 256 caratteri o contiene il carattere ';', viene lanciata un'eccezione {@link IllegalArgumentException} con un messaggio di errore.</p>
     * <p>Se la nota è vuota, viene restituito il carattere '/' come valore predefinito.</p>
     *
     * @param nota stringa da controllare.
     * @return nota
     * @throws IllegalArgumentException Se la nota supera i 256 caratteri o contiene il carattere ';'.
     */
    private static String convalidareNota(String nota) {
        if (nota.length() > 256) {
            throw new IllegalArgumentException(LayOut.BORDER_VERTICAL + "La nota non può superare i 256 caratteri");

        } else if (nota.trim().contains(";")) {
            throw new IllegalArgumentException(LayOut.BORDER_VERTICAL + "La nota non può contenere il carattere ;");
        } else if (nota.trim().equals("")) return "/";
        return nota;

    }

    /**
     * Imposta la valutazione dello stile, in modo che il voto e la nota associati siano validi, verificando la loro validità chiamando i metodi  {@link #convalidareValutazione(int valutazione)} e {@link #convalidareNota(String nota)}.
     *
     * @param stile Il voto associato stile (compreso tra 1 e 5).
     * @param note  La nota associata allo stile (fino a 256 caratteri).
     */
    public void setStileValutazione(int stile, String note) {
        this.stile = convalidareValutazione(stile);
        this.stileNote = convalidareNota(note);
    }

    /**
     * Imposta la valutazione del contenuto, in modo che il voto e la nota associati siano validi, verificando la loro validità chiamando i metodi  {@link #convalidareValutazione(int valutazione)} e {@link #convalidareNota(String nota)}.
     *
     * @param contenuto Il voto associato al contenuto (compreso tra 1 e 5).
     * @param note      La nota associata allo contenuto (fino a 256 caratteri).
     */
    public void setContenutoValutazione(int contenuto, String note) {
        this.contenuto = convalidareValutazione(contenuto);
        this.contenutoNote = convalidareNota(note);
    }

    /**
     * Imposta la valutazione alla gradevolezza, in modo che il voto e la nota associati siano validi, verificando la loro validità chiamando i metodi {@link #convalidareValutazione(int valutazione)} e {@link #convalidareNota(String nota)}.
     *
     * @param gradevolezza Il voto associato alla gradevolezza (compreso tra 1 e 5).
     * @param note         La nota associata alla gradevolezza (fino a 256 caratteri).
     */
    public void setGradevolezzaValutazione(int gradevolezza, String note) {
        this.gradevolezza = convalidareValutazione(gradevolezza);
        this.gradevolezzaNote = convalidareNota(note);
    }


    /**
     * Imposta la valutazione dell'originalità del libro.
     * <p>Il metodo verifica la validità del voto e della nota associati all'originalità chiamando i metodi
     * {@link #convalidareValutazione(int)} e {@link #convalidareNota(String)}.</p>
     *
     * @param originalita Il voto associato all'originalità (compreso tra 1 e 5).
     * @param note        La nota associata all'originalità (fino a 256 caratteri).
     */
    public void setOriginalitaValutazione(int originalita, String note) {
        this.originalita = convalidareValutazione(originalita);
        this.originalitaNote = convalidareNota(note);
    }

    /**
     * Imposta la valutazione dell'edizione del libro.
     * <p>Il metodo verifica la validità del voto e della nota associati all'edizione chiamando i metodi
     * {@link #convalidareValutazione(int)} e {@link #convalidareNota(String)}.</p>
     *
     * @param edizione Il voto associato all'edizione(compreso tra 1 e 5).
     * @param note     La nota associata all'edizione (fino a 256 caratteri).
     */
    public void setEdizioneValutazione(int edizione, String note) {
        this.edizione = convalidareValutazione(edizione);
        this.edizioneNote = convalidareNota(note);
    }

    /**
     * Calcola e imposta il voto finale come la media dei punteggi per tutti i criteri.
     * <p>Il voto finale è calcolato come la media dei punteggi per stile, contenuto, gradevolezza, originalità, e edizione.
     * La nota associata al voto finale viene validata chiamando il metodo {@link #convalidareNota(String)} .</p>
     *
     * @param stile        Il voto associato allo stile.
     * @param contenuto    Il voto associato al contenuto.
     * @param gradevolezza Il voto associato alla gradevolezza.
     * @param originalita  Il voto associato all'originalità.
     * @param edizione     Il voto associato all'edizione.
     * @param note         La nota associata al voto finale (fino a 256 caratteri).
     */
    public void setVotofinalyValutazione(int stile, int contenuto, int gradevolezza, int originalita, int edizione, String note) {
        this.votofinaly = ((double) (stile + contenuto + gradevolezza + originalita + edizione) / 5);
        this.votofinalyNote = convalidareNota(note);
    }


    /**
     * Visualizza tutte le valutazioni complete di un libro specifico.
     *
     * <p>Questo metodo legge il file CSV contenente le valutazioni{@code VALUTAZIONE_FILE}, filtra le valutazioni
     * associate all'ID del libro passato come parametro, e le visualizza
     * chiamando il metodo {@link LayOut#printValutazioni(String[])}.</p>
     *
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Inizializza una lista per memorizzare le valutazioni trovate.</li>
     *   <li>Apri un BufferedReader per leggere il file CSV riga per riga.</li>
     *   <li>Per ogni riga letta:
     *     <ul>
     *       <li>Dividi la riga in un array di stringhe utilizzando il punto e virgola come delimitatore.</li>
     *       <li>Converti il secondo elemento dell'array (l'ID del libro) in un numero intero.</li>
     *       <li>Se l'ID corrisponde a quello passato come parametro, aggiungi la riga alla lista
     *       delle valutazioni e stampa la valutazione utilizzando il metodo {@link LayOut#printValutazioni(String[])}.</li>
     *     </ul>
     *   </li>
     *   <li>Dopo aver letto tutte le righe, verifica se la lista delle valutazioni è vuota.
     *       Se è vuota, stampa un messaggio che indica che non sono state trovate valutazioni per il libro specificato.</li>
     * </ul>
     *
     * @param id L'ID del libro di cui si vogliono visualizzare le valutazioni.
     * @throws IOException Se si verifica un errore durante la lettura del file.
     */
    public static void visualizzaValutazioneCompleta(int id) throws IOException {

        List<String> valutazioni = new ArrayList<>();
        //crea un BufferedReader per leggere riga per riga del file
        try (BufferedReader csvReader = new BufferedReader(new FileReader(VALUTAZIONE_FILE))) {
            String riga;
            while ((riga = csvReader.readLine()) != null) {
                String[] data = riga.split(";");
                int Id = Integer.parseInt(data[1]); // l'ID del libro è al secondo posto nel file
                if (Id == id) {
                    valutazioni.add(riga);
                    LayOut.printValutazioni(data);
                }
            }
        }

        if (valutazioni.isEmpty()) {
            System.out.println("Non sono state trovate valutazioni per il libro con ID: " + id);
        }
    }


    /**
     * Calcola le medie delle valutazioni per OGNI CRITERIO di un libro specifico e restituisce un array di stringhe con le medie e i commenti.
     *
     * <p>Questo metodo legge il file CSV contenente le valutazioni, filtra le valutazioni
     * che corrispondono all'ID del libro passato come parametro, e calcola la media
     * delle valutazioni per ogni criterio (stile, contenuto, gradevolezza, originalità, edizione, voto finale).
     * Inoltre, seleziona un commento casuale per ogni criterio.</p>
     *
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Inizializza una lista di oggetti {@link Valutazione} per memorizzare le valutazioni del libro specificato.</li>
     *   <li>Apri un BufferedReader per leggere il file CSV riga per riga.</li>
     *   <li>Per ogni riga letta:
     *     <ul>
     *       <li>Dividi la riga in un array di stringhe utilizzando il punto e virgola come delimitatore.</li>
     *       <li>Converti il secondo elemento dell'array (l'ID del libro) in un numero intero.</li>
     *       <li>Se l'ID corrisponde a quello passato come parametro, crea un nuovo oggetto {@link Valutazione} e
     *       assegna i valori ai campi dell'oggetto (stile, contenuto, gradevolezza, ecc.).</li>
     *       <li>Aggiunge l'oggetto {@link Valutazione} alla lista delle valutazioni.</li>
     *     </ul>
     *   </li>
     *   <li>Dopo aver letto tutte le righe, verifica se la lista delle valutazioni è vuota.
     *       Se è vuota, stampa un messaggio che indica che non sono state trovate valutazioni per il libro specificato e restituisci null.</li>
     *   <li>Calcola la media per ogni criterio (stile, contenuto, gradevolezza, originalità, edizione, voto finale)
     *       sommando i valori per ogni criterio e dividendo per il numero di valutazioni.</li>
     *   <li>Inizializza un array di stringhe per memorizzare le medie e i commenti selezionati casualmente.</li>
     *   <li>Per ogni criterio, seleziona un commento casuale dalla lista delle valutazioni e lo inserisce nell'array insieme alla media calcolata.</li>
     *   <li>Restituisci l'array di stringhe contenente le medie e i commenti.</li>
     * </ul>
     *
     * @param libroId L'ID del libro di cui si vogliono calcolare le medie delle valutazioni.
     * @return Un array di stringhe contenente le medie e i commenti per ogni criterio.
     * Restituisce null se non sono state trovate valutazioni per il libro specificato.
     * @throws IOException Se si verifica un errore durante la lettura del file.
     */
    public static String[] valutazioniAggregate(String libroId) throws IOException {

        List<Valutazione> valutazioniList = new ArrayList<>();
        // creo un BufferedReader per leggere riga per riga il file
        try (BufferedReader csvReader = new BufferedReader(new FileReader(VALUTAZIONE_FILE))) {
            String riga;
            while ((riga = csvReader.readLine()) != null) {
                String[] data = riga.split(";"); //salvo le valutazioni in un array di stringhe
                int id = Integer.parseInt(data[1]); // memorizzo l'id del libro valutato
                if (String.valueOf(id).equals(libroId)) {
                    Valutazione valutazione = new Valutazione();
                    // assegno al oggetto Valutazione i suoi dati letti dal file cvs
                    valutazione.stile = Integer.parseInt(data[2]);
                    valutazione.stileNote = data[3];
                    valutazione.contenuto = Integer.parseInt(data[4]);
                    valutazione.contenutoNote = data[5];
                    valutazione.gradevolezza = Integer.parseInt(data[6]);
                    valutazione.gradevolezzaNote = data[7];
                    valutazione.originalita = Integer.parseInt(data[8]);
                    valutazione.originalitaNote = data[9];
                    valutazione.edizione = Integer.parseInt(data[10]);
                    valutazione.edizioneNote = data[11];
                    data[12] = data[12].replace(',', '.');
                    valutazione.votofinaly = Double.parseDouble(data[12]);
                    valutazione.votofinalyNote = data[13];
                    valutazioniList.add(valutazione); //aggiunge l'oggetto valutazione alla lista.
                }
            }
        }

        if (valutazioniList.isEmpty()) {
            System.out.println(LayOut.centraTesto("Nessuna valutazione trovata per il libro con ID: "+ libroId, LUNGHEZZA_LAYOUT));
            return null;
        }

        // Calcola le medie
        double totalStile = 0;
        double totalContenuto = 0;
        double totalGradevolezza = 0;
        double totalOriginalita = 0;
        double totalEdizione = 0;
        double totalVotoFinale = 0;

        int count = 0;

        for (Valutazione v : valutazioniList) {
            totalStile += v.stile;
            totalContenuto += v.contenuto;
            totalGradevolezza += v.gradevolezza;
            totalOriginalita += v.originalita;
            totalEdizione += v.edizione;
            totalVotoFinale += v.votofinaly;
            count++;
        }

        double mediaStile = count > 0 ? totalStile / count : 0;
        double mediaContenuto = count > 0 ? totalContenuto / count : 0;
        double mediaGradevolezza = count > 0 ? totalGradevolezza / count : 0;
        double mediaOriginalita = count > 0 ? totalOriginalita / count : 0;
        double mediaEdizione = count > 0 ? totalEdizione / count : 0;
        double mediaVotoFinale = count > 0 ? totalVotoFinale / count : 0;


        String[] mediaValutazioni = new String[12];
        mediaValutazioni[0] = LayOut.coloraInBlu("Stile: ") + mediaStile;
        mediaValutazioni[1] = LayOut.coloraInBlu("Contenuto: ") + mediaContenuto;
        mediaValutazioni[2] = LayOut.coloraInBlu("Gradevolezza: ") + mediaGradevolezza;
        mediaValutazioni[3] = LayOut.coloraInBlu("Originalità: ") + mediaOriginalita;
        mediaValutazioni[4] = LayOut.coloraInBlu("Edizione:  ") + mediaEdizione;
        mediaValutazioni[5] = LayOut.coloraInBlu("Voto Finale: ") + mediaVotoFinale;

        // Mostra un'anteprima dei commenti
        Random random = new Random();
        valutazioniList.forEach(v -> {
            // Seleziona un commento casuale per ogni criterio e lo inserisce nell'array
            mediaValutazioni[6] = LayOut.coloraInBlu("Stile: ") + valutazioniList.get(random.nextInt(valutazioniList.size())).stileNote;
            mediaValutazioni[7] = LayOut.coloraInBlu("Contenuto: ") + valutazioniList.get(random.nextInt(valutazioniList.size())).contenutoNote;
            mediaValutazioni[8] = LayOut.coloraInBlu("Gradevolezza: ") + valutazioniList.get(random.nextInt(valutazioniList.size())).gradevolezzaNote;
            mediaValutazioni[9] = LayOut.coloraInBlu("Originalità: ") + valutazioniList.get(random.nextInt(valutazioniList.size())).originalitaNote;
            mediaValutazioni[10] = LayOut.coloraInBlu("Edizione: ") + valutazioniList.get(random.nextInt(valutazioniList.size())).edizioneNote;
            mediaValutazioni[11] = LayOut.coloraInBlu("Voto Finale: ") + valutazioniList.get(random.nextInt(valutazioniList.size())).votofinalyNote;
        });

        return mediaValutazioni;
    }

    /**
     * Gestisce l'input del punteggio di valutazione per un criterio specifico.
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Richiede all'utente di inserire un punteggio per il criterio specificato.</li>
     *   <li>Visualizza un messaggio che chiede un input numerico compreso tra 1 e 5.</li>
     *   <li>Converte l'input in un numero intero.</li>
     *   <li>Invoca il metodo {@link #convalidareValutazione(int)} per validare il punteggio inserito.</li>
     *   <li>Se l'input è valido, ritorna il punteggio.</li>
     *   <li>Se l'input è invalido (ad esempio, non è un numero o è fuori dal range), cattura l'eccezione e visualizza il messaggio di errore.</li>
     *   <li>Ripete il ciclo finché l'utente non inserisce un valore valido.</li>
     * </ul>
     *
     * @param scanner  un oggetto {@link Scanner} per la lettura dell'input dell'utente.
     * @param criterio il nome del criterio per cui si sta inserendo il punteggio (es. "Stile", "Contenuto").
     * @return il punteggio validato inserito dall'utente (un numero intero compreso tra 1 e 5).
     */
    private static int inputValutazione(Scanner scanner, String criterio) {
        while (true) {

            System.out.print(LayOut.BORDER_VERTICAL + "Inserisci il punteggio per " + LayOut.coloraInGiallo(criterio) + " (1-5):");
            try {
                int valutazione = Integer.parseInt(scanner.nextLine());
                return convalidareValutazione(valutazione);
            } catch (IllegalArgumentException e) {
                System.out.println(LayOut.BORDER_VERTICAL +LayOut.coloraErrore("Input non valido, Riprova."));

            }
        }
    }

    /**
     * Gestisce l'input della nota per un criterio specifico.
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Richiede all'utente di inserire una nota per il criterio specificato.</li>
     *   <li>Visualizza un messaggio che chiede un input testuale con un massimo di 256 caratteri.</li>
     *   <li>Legge l'input dell'utente come una stringa.</li>
     *   <li>Invoca il metodo {@link #convalidareNota(String)} per validare la nota inserita.</li>
     *   <li>Se l'input è valido, ritorna la nota.</li>
     *   <li>Se l'input è invalido (ad esempio, supera il limite di caratteri), cattura l'eccezione e visualizza il messaggio di errore.</li>
     *   <li>Ripete il ciclo finché l'utente non inserisce una nota valida.</li>
     * </ul>
     *
     * @param scanner  un oggetto {@link Scanner} per la lettura dell'input dell'utente.
     * @param criterio il nome del criterio per cui si sta inserendo la nota (es. "Stile", "Contenuto").
     * @return la nota validata inserita dall'utente (una stringa con un massimo di 256 caratteri).
     */
    private static String inputNota(Scanner scanner, String criterio) {
        while (true) {
            System.out.print(LayOut.BORDER_VERTICAL + "Inserisci la nota per " + LayOut.coloraInGiallo(criterio) + " (max 256 caratteri):");

            String nota = scanner.nextLine();
            try {
                return convalidareNota(nota);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * <p>Questo metodo richiede all'utente di inserire l'ID del libro che desidera valutare,
     * verifica se il libro è presente in una delle librerie dell'utente e se non è già stato valutato.
     * Se il libro è valido, l'utente può inserire una valutazione per ogni criterio (stile, contenuto,
     * gradevolezza, originalità, edizione e voto finale), che viene poi salvata nel file CSV.</p>
     *
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Richiedi all'utente di inserire l'ID del libro da valutare.</li>
     *   <li>Verifica se il libro è presente in una delle librerie dell'utente chiamando il metodo {@link Libreria#isLibroInLibrerie(int libroId, String userId)}.</li>
     *   <li>Se il libro non è presente, stampa un messaggio e termina l'esecuzione del metodo.</li>
     *   <li>Verifica se il libro è già stato valutato dall'utente chiamando il metodo {@link #libroGiaValutato(int libroId, String userId)}.</li>
     *   <li>Se il libro è già stato valutato, stampa un messaggio e termina l'esecuzione del metodo.</li>
     *   <li>Inizializza un oggetto {@link Valutazione} per memorizzare le valutazioni inserite dall'utente.</li>
     *   <li>Per ogni criterio (stile, contenuto, gradevolezza, originalità, edizione):
     *     <ul>
     *       <li>Richiedi all'utente di inserire un punteggio chiamando il metodo {@link #inputValutazione(Scanner scanner, String criterio)}.</li>
     *       <li>Richiedi all'utente di inserire un commento chiamando il metodo {@link #inputNota(Scanner scanner, String criterio)}.</li>
     *       <li>Assegna il punteggio e il commento all'oggetto {@link Valutazione}.</li>
     *     </ul>
     *   </li>
     *   <li>Calcola il voto finale come media dei punteggi inseriti per ogni criterio e aggiungi un commento finale.</li>
     *   <li>Salva la valutazione nel file CSV chiamando il metodo {@link #salvaValutazioneSuFile(String userId, int libroId, Valutazione valutazione)}.</li>
     *   <li>Stampa un messaggio di conferma che la valutazione è stata aggiunta con successo.</li>
     * </ul>
     *
     * @param userId L'useriddell'utente, associato alle sue librerie.
     * @throws IOException Se si verifica un errore durante la lettura o scrittura del file.
     */
    public void valutazioneLibro(String userId) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci l'ID del libro da valutare: ");
        Libreria libreria = new Libreria(utenteRegistrato);

        int libroId = libreria.leggiSceltaUtente();

        // Controlla se il libro è presente in una delle librerie dell'utente
        if (!libreria.isLibroInLibrerie(libroId, userId)) {
            System.out.println("Il libro non è presente nelle tue librerie. Puoi commentare solo i libri presenti.");
            return;
        }

        if (libroGiaValutato(libroId, userId)) {
            System.out.println("Hai già valutato questo libro.");
            return;
        }

        Valutazione valutazione = new Valutazione();
        System.out.println(LayOut.coloraInGiallo(LayOut.BORDER_TOP_LEFT));
        System.out.println(LayOut.coloraInGiallo(LayOut.BORDER_VERTICAL) + "Inserisci la valutazione per il libro: " + LayOut.coloraInGiallo(BookRecommender.getTitoloLibroById(libroId)));
        System.out.println(LayOut.BORDER_VERTICAL);
        valutazione.setStileValutazione(inputValutazione(scanner, "Stile"), inputNota(scanner, "Stile"));
        valutazione.setContenutoValutazione(inputValutazione(scanner, "Contenuto"), inputNota(scanner, "Contenuto"));
        valutazione.setGradevolezzaValutazione(inputValutazione(scanner, "Gradevolezza"), inputNota(scanner, "Gradevolezza"));
        valutazione.setOriginalitaValutazione(inputValutazione(scanner, "Originalità"), inputNota(scanner, "Originalità"));
        valutazione.setEdizioneValutazione(inputValutazione(scanner, "Edizione"), inputNota(scanner, "Edizione"));
        // calcolo del voto finale come la media dei voti per ogni creiterio
        valutazione.setVotofinalyValutazione(valutazione.stile, valutazione.contenuto, valutazione.gradevolezza, valutazione.originalita, valutazione.edizione, inputNota(scanner, "Commento Finale"));

        // Salva valutazione nel file CSV
        salvaValutazioneSuFile(userId, libroId, valutazione);

        System.out.println(LayOut.BORDER_VERTICAL);
        System.out.println(LayOut.coloraInGiallo(LayOut.BORDER_VERTICAL) + LayOut.coloraInVerde("\tValutazione aggiunta con successo."));
        System.out.println(LayOut.coloraInGiallo(LayOut.BORDER_BOTTOM_LEFT));

    }


    /**
     * Verifica se un libro è già stato valutato da un utente specifico.
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Apre il file di valutazioni specificato da {@code VALUTAZIONE_FILE} in modalità lettura.</li>
     *   <li>Legge ogni riga del file, dove ogni riga rappresenta una valutazione e contiene più campi separati dal punto e virgola (";").</li>
     *   <li>Divide la riga in campi usando {@code String.split(";")}.</li>
     *   <li>Confronta il primo campo (userId) e il secondo campo (libroId) con i valori forniti come parametri.</li>
     *   <li>Se trova una corrispondenza tra l'userId e il libroId, restituisce {@code true}, indicando che il libro è già stato valutato da quell'utente.</li>
     *   <li>Se scorre tutte le righe senza trovare una corrispondenza, restituisce {@code false}, indicando che il libro non è stato valutato dall'utente.</li>
     * </ul>
     *
     * @param libroId l'ID del libro di cui verificare la valutazione.
     * @param userId  l'ID dell'utente di cui verificare se ha già valutato il libro.
     * @return {@code true} se l'utente ha già valutato il libro, altrimenti {@code false}.
     * @throws IOException se si verifica un errore durante la lettura del file.
     */
    private static boolean libroGiaValutato(int libroId, String userId) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(VALUTAZIONE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields[0].equals(userId) && fields[1].equals(String.valueOf(libroId))) return true;
            }
        }
        return false;
    }


    /**
     * Salva una valutazione di un libro nel file CSV <code>VALUTAZIONE_FILE</code>.
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Apre un {@link FileWriter} in modalità append per scrivere nel file le valutazioni contenuti nell'oggetto {@link Valutazione}passato come parametro.</li>
     *   <li>Scrive una nuova riga nel file, contenente l'userId, l'ID del libro, e i valori della valutazione separati da ";".</li>
     *   <li>Ogni campo della valutazione è formattato e convertito in una stringa prima di essere scritto.</li>
     *   <li>Chiude il file dopo aver completato la scrittura.</li>
     * </ul>
     *
     * @param userId      l'userId dell'utente che ha effettuato la valutazione.
     * @param libroId     l'ID del libro valutato.
     * @param valutazione l'oggetto {@link Valutazione} contenente i dettagli della valutazione.
     * @throws IOException se si verifica un errore di I/O durante la scrittura nel file.
     */
    private static void salvaValutazioneSuFile(String userId, int libroId, Valutazione valutazione) throws IOException {
        try (FileWriter csvWriter = new FileWriter(VALUTAZIONE_FILE, true)) {
            csvWriter.write(String.format("%s;%d;%d;%s;%d;%s;%d;%s;%d;%s;%d;%s;%f;%s%n", userId, libroId, valutazione.stile, valutazione.stileNote, valutazione.contenuto, valutazione.contenutoNote, valutazione.gradevolezza, valutazione.gradevolezzaNote, valutazione.originalita, valutazione.originalitaNote, valutazione.edizione, valutazione.edizioneNote, valutazione.votofinaly, valutazione.votofinalyNote));
        }
    }
}
