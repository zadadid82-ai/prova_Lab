import java.io.*;
import java.util.*;
import java.util.List;

/**
 * La classe gestisce la creazione e la memorizzazione delle Librerie.
 * Solo gli utenti registrati possono creare librerie.
 * <p>Per creare una libreria è neccessario avere:</p>
 * <ul>
 * <li> il nome della libreria </li>
 * <li> gli ID dei libri che si desidera aggiungere alla libreria </li>
 * </ul>
 * <p>
 * Le librerie create dell'utente vengono salvate in un file CSV.
 * Il nome del file CSV è memorizzato nella variabile costante <code>LIBRERIE_FILE</code>.
 * </p>
 *
 * <p>
 * La classe include anche un menu per la gestione delle librerie personali,
 * che consente all'utente di valutare o suggerire solo i libri persenti all'interno delle sue librerie, oppure di visualizzare un libro tramite il suo id.
 * </p>
 *
 * @author Ameur
 * @author Sara
 */

public class Libreria {

    //CAMPI
    /**
     * Nome del file CSV contenente i dati delle librerie.
     */
    private static final String LIBRERIE_FILE = "Librerie.dati.csv";
    /**
     * L'utente registrato che sta effettuando l'operazione di creare uan libreria.
     */
    private UtenteRegistrato utenteRegistrato;
    private String nome; // Nome della libreria
    private List<Book> libri; // Lista dei libri presenti nella libreria
    boolean continua = true;

    //COSTRUTTORI

    /**
     * Costruttore che inizializza una libreria con il nome passato come parametro.
     *
     * @param nome Il nome della libreria.
     */
    public Libreria(String nome) {
        this.nome = nome;
        this.libri = new ArrayList<>();
    }

    /**
     * Costruttore vuoto della classe.
     */
    public Libreria() {
        this.libri = new ArrayList<>();
    }

    /**
     * Costruisce un'istanza della classe associato all'utente registrato specificato.
     *
     * <p>Parametri:</p>
     * <ul>
     *     <li>{@code utenteRegistrato} - un oggetto {@link UtenteRegistrato} che rappresenta l'utente autenticato </li>
     * </ul>
     *
     * <p>Eccezioni:</p>
     * <ul>
     *     <li>{@code NullPointerException} - se {@code utenteRegistrato} è {@code null}.</li>
     * </ul>
     *
     * @param utenteRegistrato l'oggetto {@link UtenteRegistrato} associato a questa libreria.
     * @throws NullPointerException se {@code utenteRegistrato} è {@code null}.
     */
    public Libreria(UtenteRegistrato utenteRegistrato) {
        this.utenteRegistrato = utenteRegistrato;
    }

    //METODI

    /**
     * Metodo per ottenere lista dei libri presenti nella libreria.
     *
     * @return Una lista di oggetti {@link Book} presenti nella libreria.
     */
    public List<Book> getLibri() {
        return libri;
    }

    /**
     * Metodo per aggiungere un libro alla libreria tramite il suo ID.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     * <li>Ottiene il libro tramite il suo ID usando il metodo {@link BookRecommender#getBookById(int)}.</li>
     * <li>Verifica se il libro esiste. Se non esiste, stampa un messaggio di errore.</li>
     * <li>Verifica se il libro è già presente nella libreria. Se è già presente, stampa un messaggio di avviso.</li>
     * <li>Se il libro non è già presente, lo aggiunge alla libreria e stampa un messaggio di conferma.</li>
     * </ol>
     *
     *
     * @param id L'ID del libro da aggiungere alla libreria.
     */
    public void addLibroById(int id) {

        // Ottieni il libro tramite l'ID
        Book book = BookRecommender.getBookById(id);

        // Verifica se il libro esiste
        if (book == null) {
            System.out.println(LayOut.coloraErrore("Libro con ID " + id + " non trovato."));
            return;
        }

        // Verifica se il libro è già presente nella libreria
        if (libri.contains(book)) {
            System.out.println(LayOut.coloraInGiallo("Il libro è già presente nella libreria."));
        } else {
            // Aggiungi il libro alla libreria
            libri.add(book);
            System.out.println(LayOut.coloraInVerde("Libro aggiunto con successo."));
        }
    }


    /**
     * Metodo per registrare una nuova libreria e permette all'utente di aggiungere libri ad essa.
     * Per aggiungere i libri desiderati alla libreria è neccessario essere a conoscenza prima dei loro rispettivi ID.
     * <p>Nel dettaglio:</p>
     * <ol>
     * <li>Crea una nuova libreria con il nome passato come parametro.</li>
     * <li>Chiede all'utente di inserire l'ID dei libri da aggiungere alla libreria.</li>
     * <li>Controlla se input inserito dell'utente è valido chiamando il metodo {@link #leggiSceltaUtente()}  </li>
     * <li>Utilizza il metodo {@link #addLibroById(int)} per aggiungere i libri alla libreria.</li>
     * <li>Una volta terminato, visualizza i libri aggiunti alla libreria chiamando {@link LayOut#printBook_ID_TITOLO(String, List, String, int)} </li>
     * </ol>
     *
     * @param nomeLibreria Il nome della libreria da registrare.
     * @throws IOException Se si verifica un errore durante la lettura dell'input.
     */
    public void registraLibreria(String nomeLibreria) throws IOException {

        // Crea una nuova libreria con il nome specificato
        Libreria libreria = new Libreria(nomeLibreria);

        // Ciclo per inserire i libri nella Libreria tramite ID
        while (true) {
            System.out.println("Inserisci l'ID del Libro (oppure -1 per terminare) >");
            int id = leggiSceltaUtente();
            if (id == -1) {
                break; // Termina il ciclo se l'utente inserisce -1
            }
            libreria.addLibroById(id); // Aggiungi il libro alla libreria
        }


        // Mostra i libri nella libreria appena registrata
        if (!libreria.getLibri().isEmpty()) {
            System.out.println();
            System.out.println("\tLibreria salvata con successo.");

            LayOut.printBook_ID_TITOLO("Nella libreria " + LayOut.coloraInBlu(nome) + " sono stati aggiunti i seguenti libri: ", libreria.getLibri(), LayOut.ANSI_BLUE, 120);
        }

        if (libreria.getLibri().isEmpty()){
        System.out.println(LayOut.coloraErrore("Impossiblie creare una libreria vuota."));
        }
        salvaLibrerieSuFile(utenteRegistrato, libreria);
    }

    /**
     * Salva i dettagli della libreria sul un file CSV di cui il nome è memorizzato nella variabile costante LIBRERIE_FILE.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     * <li>Ottiene la lista di libri presenti nella libreria chiamando il metodo{@link #getLibri()} .</li>
     * <li>Apre il file CSV in modalità append usando {@link BufferedWriter}.</li>
     * <li>Salva gli ID dei libri presenti nella libreria in un ArrayList </li>
     * <li>Scrive nel file l'ID dell'utente, il nome della libreria e gli ID dei libri salvati nell ArrayList (utilizzando il metodo {@link ArrayList#toString()})</li>
     * <li>Chiude il file una volta completata l'operazione.</li>
     * <li>Nel file CSV sarà memorizzata una stringa del tipo: "userId;nomeLibreria;[ID1, ID2, ID3]"</li>
     * </ol>
     *
     * @param utente   L'utente registrato che possiede la libreria.
     * @param libreria La libreria da salvare.
     * @throws IOException Se si verifica un errore durante la scrittura del file.
     */
    public void salvaLibrerieSuFile(UtenteRegistrato utente, Libreria libreria) throws IOException {
        if (libreria.getLibri().isEmpty()){
            return;
        }
        List<Book> libri = libreria.getLibri();


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LIBRERIE_FILE, true))) {
            ArrayList<Integer> listaId = new ArrayList<>();
            for (Book book : libri) {
                listaId.add(book.getId());
            }
            writer.write(utente.getUserId() + ";" + nome + ";" + listaId.toString());
            writer.newLine();
        }


    }


    /**
     * Visualizza le librerie salvate per un utente registrato.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Recupera le librerie associate all'utente dal file CSV utilizzando {@link #leggiLibreriePerUtente(UtenteRegistrato)}.</li>
     *     <li>Se non sono presenti librerie, informa l'utente con un messaggio e termina l'esecuzione.</li>
     *     <li>Mostra le librerie disponibili utilizzando {@link LayOut#mostraLibrerie(String, List)}.</li>
     *     <li>Permette all'utente di selezionare una libreria.</li>
     *     <li>Visualizza i dettagli della libreria selezionata tramite {@link #mostraDettagliLibreria(String)}.</li>
     * </ol>
     *
     * @param utente L'utente registrato per il quale visualizzare le librerie.
     * @throws IOException Se si verifica un errore durante la lettura del file CSV.
     */
    public void visualizzaLibrerieSalvate(UtenteRegistrato utente) throws IOException {
        // Leggi e mostra le librerie per l'utente
        List<String> librerie = leggiLibreriePerUtente(utente);
        if (librerie.isEmpty()) {
            System.out.println("Non hai ancora alcuna libreria.");
            return;
        }
        LayOut.mostraLibrerie("Scegli il numero della Libreria da visualizzare:",librerie);

        // L'utende deve scegliere una libreria
        int scelta = leggiSceltaUtente();
        if (scelta < 1 || scelta > librerie.size()) {
            System.out.println(LayOut.coloraErrore("Scelta non valida."));
            utenteRegistrato.mostraMenuLibrerie();
            return;
        }
        mostraDettagliLibreria(librerie.get(scelta - 1));
    }


    /**
     * Legge le librerie salvate per un determinato utente dal file CSV.
     *
     * <p>Nel dettaglio:</p>
     * <ol>
     *     <li>Legge il file CSV specificato nella costante  LIBRERIE_FILE.</li>
     *     <li>Filtra le righe in base all'userId ottenuto tramite {@link UtenteRegistrato#getUserId()}.</li>
     *     <li>Verifica che ogni riga contenga esattamente tre campi separati da punto e virgola.</li>
     *     <li>Se l'useriId della riga corrisponde all'userId fornito, aggiunge il nome della libreria e gli ID dei libri alla lista.</li>
     *     <li>Se una riga ha un formato non valido, viene visualizzato un messaggio informativo.</li>
     * </ol>
     *
     * @param utente L'utente registrato per il quale cercare le librerie.
     * @return Una lista di stringhe che rappresentano le librerie salvate per l'utente, nel formato "nomeLibreria: [ID1, ID2, ID3]".
     * @throws IOException Se si verifica un errore durante la lettura del file CSV.
     */
    private static List<String> leggiLibreriePerUtente(UtenteRegistrato utente) throws IOException {
        String userId = utente.getUserId();
        File fileCSV = new File(LIBRERIE_FILE);
        List<String> librerie = new ArrayList<>();

        try (Scanner scanner = new Scanner(fileCSV)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                if (data.length == 3) {
                    String fileUserId = data[0].trim();
                    if (fileUserId.equals(userId)) {
                        String nomeLibreria = data[1].trim();
                        String libriNellaLibreria = data[2];
                        librerie.add(nomeLibreria + ": " + libriNellaLibreria);
                    }
                } else {
                    System.out.println("Formato riga non valido: " + line);
                }
            }
        }

        return librerie;
    }


    /**
     * Metodo per mostrare i libri all'interno della libreria selezionata.
     * <p>Nel dettagli:</p>
     * <ol>
     *  <li>Analizza la stringa che rappresenta una libreria nel formato "nomeLibreria: [ID1, ID2, ID3]"</li>
     * <li>Estrae il nome della Libreria e salva in un array di stringhe l'Id dei libri presenti nella libreria, </li>
     * <li>Utilizza il metodo {@link BookRecommender#getBookById(int)} per recuperare le informazioni sui libri tramite il loro ID.</li>
     * </ol>
     *
     * @param libreriaSelezionata La libreria selezionata da visualizzare, nel formato "nomeLibreria: [ID1, ID2, ID3]".
     */
    private void mostraDettagliLibreria(String libreriaSelezionata) {
        String[] dettagliLibreria = libreriaSelezionata.split(":", 2);
        if (dettagliLibreria.length != 2) {
            System.out.println("Formato dei dati della libreria non valido.");
            return;
        }

        String nomeLibreria = dettagliLibreria[0].trim();
        String libriNellaLibreria = dettagliLibreria[1].trim();

        String[] libri = libriNellaLibreria.split(",");
        List<Book> books = new ArrayList<>();

        for (String idlibro : libri) {
            idlibro = idlibro.replaceAll("\\[", "").replaceAll("]", "").trim();
            int id = Integer.parseInt(idlibro);
            Book book = BookRecommender.getBookById(id);
            if (book != null) {
                books.add(book);
            }
        }

        LayOut.printBook_ID_TITOLO("Libri presenti nella libreria " + LayOut.ANSI_BLUE + nomeLibreria + LayOut.ANSI_RESET, books, LayOut.ANSI_BLUE, 120);
    }


    /**
     * Metodo per chiedere all'utente di inserire un nome per la libreria e verifica che non contenga i caratteri non ammessi
     * e che non sia una stringa vuota.
     *
     * <p>Se il nome inserito contiene i caratteri ":" o ";", oppure se è vuoto, viene stampato un messaggio di errore
     * e l'utente ha la possibilità di riprovare.</p>
     *
     * @return Il nome della libreria valido inserito dall'utente.
     */
    public String inserisciNomeLibreria(UtenteRegistrato utente) {
        Scanner scanner = new Scanner(System.in);
        String nomeLibreria = "";
        boolean nomeValido = false;

        while (!nomeValido) {
            System.out.print("Inserisci il nome della libreria: ");
            nomeLibreria = scanner.nextLine().trim();

            // Verifica se la stringa è vuota o contiene caratteri non ammessi
            if (nomeLibreria.isEmpty()) {
                System.out.println("Errore: il nome della libreria non può essere vuoto.");
            } else if (nomeLibreria.contains(":") || nomeLibreria.contains(";")) {
                System.out.println("Errore: il nome della libreria non può contenere i caratteri ':' o ';'.");
            } else {
                // Il nome è valido
                nome = nomeLibreria;
                nomeValido = true;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(LIBRERIE_FILE))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] fields = linea.split(";");
                    // Aggiungi tutte le righe tranne quella da cancellare
                    if (fields[0].equals(utente.getUserId()) && fields[1].equals(nomeLibreria) ) {
                        System.out.println("\tLa libreria " + LayOut.coloraErrore(nomeLibreria) + " è già esistente.");
                        System.out.println("\tRiprova con un nome diverso.");
                        System.out.println();
                        nomeValido = false;
                    }
                }
            } catch (IOException e) {
                //Stampa lo stack trace dell'eccezione
                e.printStackTrace();
            }

        }

        return nomeLibreria;
    }

    /**
     * Metodo per mostrare un menu per la gestione delle librerie e dei libri, permettendo all'utente di scegliere
     * tra diverse opzioni come visualizzare, valutare o suggerire libri.
     *
     * <p>Nel dettaglio: </p>
     * <ol>
     * <li>Stampa un menu con le opzioni disponibili.</li>
     * <li>Attende la scelta dell'utente tramite il metodo {@link #leggiSceltaUtente()}.</li>
     * <li>Esegue l'azione corrispondente alla scelta effettuata dall'utente.</li>
     * <li>Gestisce errori di input o scelte non valide.</li>
     * </ol>
     * @param utenteRegistrato l'utente registrato che visualizza il menu.
     * @throws IOException Se si verifica un errore durante la lettura dell'input.
     */

    public void mostraMenuLibroInLibreria(UtenteRegistrato utenteRegistrato) throws IOException {

        while (continua) {
            String[] options = {
                    "Scegli come vuoi procedere:",
                    "",
                    "1. Visualizza libro",
                    "2. Valuta Libro",
                    "3. Suggerisci Libro",
                    "4. Torna indietro"
            };
            LayOut.printMenu("LE MIE LIBRERIE", options, LayOut.ANSI_GREEN);
            //mostraMenuLibreria();
            int scelta = leggiSceltaUtente();
            ConsigliaLibri cl = new ConsigliaLibri(utenteRegistrato);

            switch (scelta) {
                case 1:
                    System.out.println("\nInserire ID del libro > ");
                    int idLibro = leggiSceltaUtente();
                    if (idLibro == -1) {
                        return; // Esce dal metodo se ci sono troppi tentativi falliti
                    }
                    if (BookRecommender.getBookById(idLibro) != null) {
                        BookRecommender.getBookById(idLibro).visualizzaLibro();
                        Utente utente = new Utente();
                        utente.mostraSottomenuDettagliVisualizzaLibro();
                        break;
                    } else {
                        System.out.println(LayOut.coloraErrore("libro con id " + idLibro + " non trovato"));
                        return;
                    }


                case 2:
                    Valutazione  valutazione = new Valutazione(utenteRegistrato);
                    valutazione.valutazioneLibro(utenteRegistrato.getUserId());
                    break;
                case 3:
                    cl.consigliaLibri(utenteRegistrato);
                    break;
                case 4:
                    utenteRegistrato.mostraMenuLibrerie();
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
                    break;
            }
        }

    }


    /**
     * Legge la scelta dell'utente da input, gestendo possibili errori di input e limitando i tentativi a 3 nel caso venga inserita una stringa al posto di un intero.
     *
     * <p> Nel dettaglio:</p>
     * <ol>
     * <li>Chiede all'utente di inserire un numero intero.</li>
     * <li>Verifica che l'input sia valido e, in caso contrario, gestisce l'errore e decrementa il numero di tentativi rimanenti.</li>
     * <li>Ritorna la scelta dell'utente se valida, altrimenti ritorna -1 in caso di fallimento.</li>
     * </ol>
     *
     *
     * @return La scelta dell'utente come numero intero, o -1 in caso di errore.
     * @throws IOException Se si verifica un errore durante la lettura dell'input.
     */
    public int leggiSceltaUtente() throws IOException {
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

    /**
     * Verifica se un libro è presente in almeno una delle librerie dell'utente.
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Legge il file delle librerie riga per riga utilizzando un {@link BufferedReader}.</li>
     *   <li>Per ogni riga, divide i campi utilizzando il separatore ";".</li>
     *   <li>Se l'userId corrisponde a quello del utente corrente, estrae l'elenco dei libri presenti nella libreria.</li>
     *   <li>Confronta l'ID del libro fornito con gli ID dei libri presenti nella libreria.</li>
     *   <li>Se trova una corrispondenza, ritorna <code>true</code>, indicando che il libro è presente in una libreria dell'utente.</li>
     *   <li>Se non trova alcuna corrispondenza, ritorna <code>false</code>.</li>
     * </ul>
     *
     * @param libroId l'ID del libro da verificare.
     * @param userId  l'userId dell'utente proprietario delle librerie.
     * @return <code>true</code> se il libro è presente in almeno una libreria dell'utente, altrimenti <code>false</code>.
     * @throws IOException se si verifica un errore di I/O durante la lettura del file.
     */
    public boolean isLibroInLibrerie(int libroId, String userId) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(LIBRERIE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length > 1 && fields[0].trim().equals(userId)) {
                    String libriNellaLibreria = fields[2];
                    String[] libri = libriNellaLibreria.split(",");

                    for (String idLibro : libri) {
                        idLibro = idLibro.replaceAll("\\[", "").replaceAll("]", "").trim();
                        int id = Integer.parseInt(idLibro);
                        if (id == libroId) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }



    /**
     * Elimina una libreria associata a un utente registrato.
     * <p>
     * Questo metodo permette a un utente registrato di eliminare una delle sue librerie personali.
     * Nel dettaglio:
     * </p>
     * <ol>
     *     <li>Viene recuperata la lista delle librerie associate all'utente tramite il metodo {@link #leggiLibreriePerUtente(UtenteRegistrato)}.</li>
     *     <li>Se l'utente non ha librerie, viene visualizzato un messaggio che informa l'utente dell'assenza di librerie, e il metodo termina.</li>
     *     <li>Se sono presenti librerie, viene visualizzata una lista numerata di tutte le librerie dell'utente, permettendo di scegliere quale eliminare.</li>
     *     <li>Il metodo legge l'input dell'utente per determinare la libreria da eliminare.</li>
     *     <li>Viene validata la scelta dell'utente: se l'indice scelto non è valido, viene visualizzato un messaggio di errore e l'utente viene riportato al menu principale delle librerie.</li>
     *     <li>Se la scelta è valida, la libreria corrispondente viene identificata e rimossa dal file CSV che memorizza le librerie degli utenti.</li>
     *     <li>Il file CSV viene aggiornato, eliminando la riga corrispondente alla libreria selezionata dall'utente.</li>
     *     <li>Alla fine del processo, viene visualizzato un messaggio che conferma l'avvenuta eliminazione della libreria.</li>
     * </ol>
     *
     * @param utente l'utente registrato che desidera eliminare una libreria.
     *               Deve essere un'istanza valida di {@link UtenteRegistrato}.
     * @throws IOException se si verifica un errore durante la lettura o la scrittura del file delle librerie.
     *                     Il metodo gestisce l'eccezione stampando lo stack trace, ma l'eccezione viene comunque propagata.
     */
    public void eliminaLibreria(UtenteRegistrato utente) throws IOException {
        List<String> librerie = leggiLibreriePerUtente(utente);
        if (librerie.isEmpty()) {
            System.out.println("Non hai ancora alcuna libreria.");
            return;
        }
        LayOut.mostraLibrerie("Scegli il numero della Libreria da eliminare", librerie);

        // L'utente deve scegliere una libreria
        int scelta = leggiSceltaUtente();
        if (scelta < 1 || scelta > librerie.size()) {
            System.out.println(LayOut.coloraErrore("Scelta non valida."));
            utente.mostraMenuLibrerie();
            return;
        }


        String[] dettagliLibreria = librerie.get(scelta - 1).split(":", 2);
        String nomeLibreria = dettagliLibreria[0];


        List<String> librerieAggiornate = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(LIBRERIE_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] fields = linea.split(";");

                // Aggiungi tutte le righe tranne quella da cancellare
                if (!fields[0].equals(utente.getUserId()) || !fields[1].equals(nomeLibreria) ) {
                    librerieAggiornate.add(linea);
                }
            }
        } catch (IOException e) {
            //Stampa lo stack trace dell'eccezione
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LIBRERIE_FILE))) {
            for (String libreria : librerieAggiornate) {
                writer.write(libreria);
                writer.newLine();
            }
            System.out.println();
            System.out.println(LayOut.coloraInVerde("\tLibreria eliminata con successo."));
            System.out.println();
        } catch (IOException e) {
            //Stampa lo stack trace dell'eccezione
            e.printStackTrace();
        }
    }

}









