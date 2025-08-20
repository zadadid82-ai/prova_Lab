import java.util.List;

/**
 * La classe fornisce metodi per la formattazione e la visualizzazione di testo con stili e bordi specifici.
 * <p>
 * I metodi di questa classe permettono di stampare messaggi colorati, formattare e centrare testo, gestire bordi e decorazioni,
 * e visualizzare dettagli di libri e menu.
 * <p>Utilizza codici di escape ANSI per applicare colori e stili al testo stampato sulla console.</p>
 *
 * @author lila
 * @author sara
 */
public class LayOut {

    //Costanti ANSI per applicare e resettare i colori
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN = "\u001B[32m";

    // Caratteri speciali per bordi e decorazioni
    public static final String BORDER_HORIZONTAL = "─";
    public static final String BORDER_VERTICAL = "│";
    public static final String BORDER_TOP_LEFT = "┌";
    public static final String BORDER_TOP_RIGHT = "┐";
    public static final String BORDER_BOTTOM_LEFT = "└";
    public static final String BORDER_BOTTOM_RIGHT = "┘";

    public String stringalunga;

    /**
     * Stampa un messaggio di benvenuto centrato e colorato in azzurro e grassetto.
     *
     * @param messaggio Il messaggio di benvenuto da stampare.
     */
    public static void stampaBenvenuto(String messaggio) {
        System.out.println(ANSI_CYAN + ANSI_BOLD + centraTesto(messaggio, 52) + ANSI_RESET);
    }


    /**
     * Restituisce una stringa colorata in giallo.
     *
     * @param messaggio Il messaggio da colorare.
     * @return Il messaggio colorato in giallo.
     */
    public static String coloraInGiallo(String messaggio) {
        return (ANSI_YELLOW + messaggio + ANSI_RESET);
    }

    /**
     * Restituisce una stringa colorata in viola.
     *
     * @param messaggio Il messaggio da colorare.
     * @return Il messaggio colorato in viola.
     */
    public static String coloraInViola(String messaggio) {
        return (ANSI_PURPLE + messaggio + ANSI_RESET);
    }

    /**
     * Restituisce una stringa colorata in blu.
     *
     * @param messaggio Il messaggio da colorare.
     * @return Il messaggio colorato in blu.
     */
    public static String coloraInBlu(String messaggio) {
        return (ANSI_BLUE + messaggio + ANSI_RESET);
    }

    /**
     * Restituisce una stringa colorata in verde.
     *
     * @param messaggio Il messaggio da colorare.
     * @return Il messaggio colorato in verde.
     */
    public static String coloraInVerde(String messaggio) {
        return (ANSI_GREEN + messaggio + ANSI_RESET);
    }

    /**
     * Stampa un messaggio di saluto colorato in ciano e grassetto.
     *
     * @param messaggio Il messaggio di saluto da stampare.
     */
    public static void stampaSaluto(String messaggio) {
        System.out.println(ANSI_CYAN + ANSI_BOLD + messaggio + ANSI_RESET);
    }

    /**
     * Stampa un messaggio di errore colorato in rosso.
     *
     * @param messaggio Il messaggio di errore da stampare.
     */
    public static void stampaErrore(String messaggio) {
        System.out.println(ANSI_RED + messaggio + ANSI_RESET);
    }

    /**
     * Restituisce una stringa colorata in rosso per indicare un errore.
     *
     * @param messaggio Il messaggio da colorare.
     * @return Il messaggio colorato in rosso.
     */
    public static String coloraErrore(String messaggio) {
        return (ANSI_RED + messaggio + ANSI_RESET);
    }

    /**
     * Restituisce una stringa colorata in blu per indicare un titolo.
     *
     * @param messaggio Il messaggio da colorare.
     * @return Il messaggio colorato in blu.
     */
    public static String coloraTitolo(String messaggio) {
        return (ANSI_BLUE + messaggio + ANSI_RESET);
    }

    /**
     * Stampa un ID colorato in azzurro.
     *
     * @param messaggio L'ID da stampare.
     */
    public static void stampaId(String messaggio) {
        System.out.print(ANSI_CYAN + messaggio + ANSI_RESET);
    }

    /**
     * Restituisce una stringa colorata in ciano per indicare un ID.
     *
     * @param messaggio Il messaggio da colorare.
     * @return Il messaggio colorato in ciano.
     */
    public static String coloraId(String messaggio) {
        return (ANSI_CYAN + messaggio + ANSI_RESET);
    }

    /**
     * Stampa un messaggio centrato e colorato in ciano per indicare un ritorno indietro.
     *
     * @param messaggio Il messaggio di ritorno indietro da stampare.
     */
    public static void stampaTornaIndietro(String messaggio) {
        System.out.println(ANSI_CYAN + centraTesto(messaggio, 52) + ANSI_RESET);
    }


    /**
     * Centra un testo all'interno di una larghezza definita.
     * <p>
     * Calcola gli spazi necessari su entrambi i lati del testo per centrarlo
     * all'interno di una larghezza totale specificata. Se la larghezza totale è
     * inferiore alla lunghezza del testo, il testo verrà restituito senza modifiche.
     * </p>
     *
     * @param testo        Il testo da centrare.
     * @param larghezzaTot La larghezza totale nella quale centrare il testo.
     * @return Il testo centrato con spazi ai lati per raggiungere la
     * larghezza totale. Se la larghezza totale è insufficiente,
     * il testo viene restituito senza modifiche.
     */

    public static String centraTesto(String testo, int larghezzaTot) {  // Per centrare il testo all'interno di una larghezza definita.
        int margine = (larghezzaTot - testo.length()) / 2;
        if (margine < 0) margine = 0;
        if (larghezzaTot - testo.length() - margine < 0) {
            return " ".repeat(margine) + testo;
        }

        return " ".repeat(margine) + testo + " ".repeat(larghezzaTot - testo.length() - margine);
    }


    /**
     * Stampa una rappresentazione i deti di un libro in una cornice.
     * <p>
     * Questo metodo crea una cornice attorno ai dettagli di un libro e li stampa in console.
     * La cornice è definita dalla larghezza specificata e dal colore fornito. Il titolo passato come parametro
     * viene centrato all'interno della cornice, e i dettagli del libro sono stampati all'interno
     * della cornice, uno per riga.
     * </p>
     *
     * @param title         una stringa per informatico per l'utente. Questo testo viene centrato
     *                      all'interno della larghezza specificata, escludendo i bordi.
     * @param dettagliLibro Un array di stringhe contenente i dettagli del libro. Ogni elemento
     *                      dell'array viene stampato su una nuova riga all'interno della cornice.
     *                      Se  <code>dettagliLibro</code> è null, nessun dettaglio sarà stampato.
     * @param color         Il colore ANSI utilizzato per stampare la cornice e il titolo del libro.
     *                      I colori sono applicati utilizzando codici ANSI.
     * @param larghezza     La larghezza totale della cornice, inclusi i bordi. Determina quanto
     *                      spazio c'è per il testo.
     * @see #centraTesto(String, int)
     */
    public static void printVisualizzaLibro(String title, String[] dettagliLibro, String color, int larghezza) {
        // Crea il bordo superiore
        String borderTop = BORDER_TOP_LEFT + BORDER_HORIZONTAL.repeat(larghezza) + BORDER_TOP_RIGHT;
        // Crea il bordo inferiore
        String borderBottom = BORDER_BOTTOM_LEFT + BORDER_HORIZONTAL.repeat(larghezza) + BORDER_BOTTOM_RIGHT;
        // Centra il titolo all'interno della larghezza specificata escludendo i bordi
        String titleLine = ANSI_BOLD + ANSI_WHITE + LayOut.centraTesto(title, larghezza - 2) + ANSI_RESET;

        System.out.println(color + borderTop);
        System.out.println(titleLine);
        System.out.println(" " + " "); // Riga vuota


        if (dettagliLibro != null) {
            for (String s : dettagliLibro) {
                System.out.println("    " + s + " ");
            }
        }

        System.out.println(borderBottom + ANSI_RESET);
    }

    /**
     * Gestisce una stringa lunga spezzandola in più righe, rispettando una lunghezza massima
     * e aggiungendo un margine di spazi a sinistra per ogni riga successiva alla prima.
     * <p> Nel Dettaglio:</p>
     * <ul>
     * <li>Questo metodo prende in input una stringa e la divide in righe, ciascuna con una lunghezza massima specificata.</li>
     * <li>Se una riga eccede la lunghezza massima, viene spostata alla riga successiva.</li>
     * <li>Per tutte le righe tranne la prima, viene aggiunto un margine di spazi a sinistra.</li>
     * </ul>
     *
     *
     * @param input        La stringa da gestire, che potrebbe essere troppo lunga per essere visualizzata
     *                     su una sola riga all'interno di una cornice.
     * @param lunghezzaMAX La lunghezza massima che ogni riga può avere prima di essere spezzata.
     * @param margine      Il numero di spazi da aggiungere a sinistra per tutte le righe tranne la prima.
     * @return Una stringa formattata, spezzata su più righe secondo la lunghezza massima e con i margini applicati.
     */
    public static String gestisciStringaLunga(String input, int lunghezzaMAX, int margine) {
        StringBuilder risultato = new StringBuilder();

        // Divide la stringa di input in parole
        String[] words = input.split(" ");

        StringBuilder line = new StringBuilder();

        // Booleano per indicare se si sta gestendo la prima riga.
        boolean firstLine = true;

        for (String word : words) {
            if (line.length() + word.length() + 1 > lunghezzaMAX) {
                if (!firstLine) {
                    for (int i = 0; i < margine; i++) {
                        risultato.append(" ");
                    }
                }
                risultato.append(line.toString().trim()).append("\n");
                line = new StringBuilder();
                firstLine = false;
            }
            line.append(word).append(" ");
        }

        // Aggiunge l'ultima linea rimasta
        if (!line.isEmpty()) {
            if (!firstLine) {
                for (int i = 0; i < margine; i++) {
                    risultato.append(" ");
                }
            }
            risultato.append(line.toString().trim());
        }

        return risultato.toString();
    }

    /**
     * Stampa una lista di libri con i loro ID e titoli formattati, all'interno di una cornice colorata.
     *
     * <p>
     * Questo metodo mostra un elenco di libri con il loro ID e titolo, formattati in modo che il titolo sia allineato
     * correttamente anche se supera una certa lunghezza massima. Viene utilizzata una cornice per racchiudere
     * l'elenco e un colore specificato per la visualizzazione.
     * </p>
     *
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Viene creato un bordo superiore  concatenando i simboli dei bordi definiti dalle costanti. </li>
     *   <li>Il titolo della sezione è centrato all'interno della cornice e visualizzato in grassetto e bianco.</li>
     *   <li>Per ogni libro nella lista, il titolo viene gestito per assicurare che non superi la lunghezza massima, e viene aggiunta un'intestazione ID e Titolo colorata.</li>
     *   <li>Il metodo {@link #gestisciStringaLunga} viene utilizzato per suddividere i titoli che superano la lunghezza massima in linee multiple, con un margine di 25 spazi aggiunti per l'allineamento.</li>
     *   <li>Infine, viene creato un bordo inferiore per chiudere la cornice.</li>
     * </ul>
     *
     * @param title        Il titolo informatico per l'utente.
     * @param books        La lista dei libri (oggetti {@link Book}) da stampare.
     * @param color        Il colore ANSI da utilizzare per la cornice e il titolo.
     * @param lunghezzaMAX La larghezza massima per la visualizzazione della cornice e per la gestione dei titoli lunghi.
     */
    public static void printBook_ID_TITOLO(String title, List<Book> books, String color, int lunghezzaMAX) {
        String ID = ANSI_CYAN + "ID: " + ANSI_RESET;
        String TITOLO = ANSI_BLUE + "Titolo: " + ANSI_RESET;


        String borderTop = BORDER_TOP_LEFT + BORDER_HORIZONTAL.repeat(lunghezzaMAX) + BORDER_TOP_RIGHT;      //il metodo repeat(int i) serve a ripetere una stringa i volte.
        String borderBottom = BORDER_BOTTOM_LEFT + BORDER_HORIZONTAL.repeat(lunghezzaMAX) + BORDER_BOTTOM_RIGHT;
        String titleLine = ANSI_BOLD + ANSI_WHITE + LayOut.centraTesto(title, lunghezzaMAX - 2) + ANSI_RESET; // il -2 è per i bordi dx e sx

        System.out.println(color + borderTop);
        System.out.println(titleLine);

        for (Book book : books) {
            String titolo = LayOut.gestisciStringaLunga(book.getTitle(), 93, 25);
            String bookDetails = ID + book.getId() + ", \t " + TITOLO + titolo;
            System.out.println("    " + bookDetails + " ");
        }
        System.out.println(borderBottom + ANSI_RESET);

    }


    /**
     * Stampa le valutazioni dettagliate di un libro specifico all'interno di una cornice colorata.
     *
     * <p>Nel dettaglio:</p>
     * <ul>
     *  <li>Viene creato un bordo superiore  concatenando i simboli dei bordi definiti dalle costanti. </li>
     *  <li>Viene recuperato l'oggetto {@link Book} corrispondente all'ID specificato nella seconda posizione dell'array <code>valutazioni</code> tramite il metodo {@link BookRecommender#getBookById(int id)}.</li>
     *  <li>Le valutazioni vengono stampate categoria per categoria, con i punteggi e le note colorati in viola.</li>
     *  <li>Infine, viene creato un bordo inferiore per chiudere la cornice.</li>
     *  </ul>
     *
     * @param valutazioni L'array di stringhe contenente le valutazioni del libro, ordinate per categoria.
     */
    public static void printValutazioni(String[] valutazioni) {
        // Creazione della cornice superiore
        String borderTop = BORDER_TOP_LEFT + BORDER_HORIZONTAL;
        String borderBottom = BORDER_BOTTOM_LEFT + BORDER_HORIZONTAL;

        // Recupero del libro tramite ID
        int id = Integer.parseInt(valutazioni[1]);
        Book book = BookRecommender.getBookById(id);

        // Stampa della cornice superiore e del titolo del libro

        System.out.println(LayOut.coloraInGiallo(borderTop));
        System.out.println(LayOut.coloraInGiallo(BORDER_VERTICAL) + "\tDettagli delle valutazioni del libro: " + LayOut.coloraInGiallo(book.getTitle()));

        // Stampa dei dettagli delle valutazioni
        System.out.println(BORDER_VERTICAL);
        System.out.println(BORDER_VERTICAL + LayOut.coloraInViola("\tUser ID: ") + valutazioni[0] + ",\t" + LayOut.coloraInViola("ID libro: ") + valutazioni[1]);
        System.out.println(BORDER_VERTICAL + LayOut.coloraInViola("\tStile score: ") + valutazioni[2] + ",\t" + LayOut.coloraInViola("Stile note: ") + valutazioni[3]);
        System.out.println(BORDER_VERTICAL + LayOut.coloraInViola("\tContenuto score: ") + valutazioni[4] + ",\t" + LayOut.coloraInViola("Contenuto note: ") + valutazioni[5]);
        System.out.println(BORDER_VERTICAL + LayOut.coloraInViola("\tGradevolezza score: ") + valutazioni[6] + ",\t" + LayOut.coloraInViola("Gradevolezza note: ") + valutazioni[7]);
        System.out.println(BORDER_VERTICAL + LayOut.coloraInViola("\tOriginalità score: ") + valutazioni[8] + ",\t" + LayOut.coloraInViola("Originalità note: ") + valutazioni[9]);
        System.out.println(BORDER_VERTICAL + LayOut.coloraInViola("\tEdizione score: ") + valutazioni[10] + ",\t" + LayOut.coloraInViola("Edizione note: ") + valutazioni[11]);
        System.out.println(BORDER_VERTICAL + LayOut.coloraInViola("\tVoto Finale score: ") + valutazioni[12] + ",\t" + LayOut.coloraInViola("Voto Finale note: ") + valutazioni[13]);

        // Stampa della cornice inferiore
        System.out.println(ANSI_YELLOW + BORDER_VERTICAL + ANSI_RESET);
        System.out.println(ANSI_YELLOW + borderBottom + ANSI_RESET);
    }


    /**
     * Stampa un menu, che simula la forma di un libro, con un titolo centrato e un elenco di opzioni all'interno di una cornice.
     * <p>Nel dettaglio:</p>
     * <ul>
     * <li>Viene calcolata la larghezza del menu e viene creata una cornice superiore. </li>
     * <li>Il titolo viene centrato all'interno della cornice superiore utilizzando il metodo {@link LayOut#centraTesto(String testo, int larghezzaTot)}, e formattato in grassetto bianco.</li>
     * <li>Le opzioni del menu vengono stampate una sotto l'altra.</li>
     * <li>Viene stampata la cornice inferiore per chiudere il menu.</li>
     * </ul>
     *
     * @param title   Il titolo del menu.
     * @param options Un array di opzioni da visualizzare nel menu.
     * @param color   Il colore ANSI per la cornice e il testo del menu.
     *
     *
     */
    public static void printMenu(String title, String[] options, String color) {
        int width = 50; // Larghezza del menu
        String borderTop = BORDER_TOP_LEFT + BORDER_HORIZONTAL.repeat(width) + BORDER_TOP_RIGHT;
        String borderBottom = BORDER_BOTTOM_LEFT + BORDER_HORIZONTAL.repeat(width) + BORDER_BOTTOM_RIGHT;
        String titleLine = BORDER_VERTICAL + " " + ANSI_BOLD + ANSI_WHITE + LayOut.centraTesto(title, width - 2) + ANSI_RESET + " " + BORDER_VERTICAL;

        System.out.println(color + borderTop);
        System.out.println(titleLine);
        System.out.println(BORDER_VERTICAL + " " + " ".repeat(width - 2) + BORDER_VERTICAL); // Riga vuota
        for (String option : options) {
            System.out.println(BORDER_VERTICAL + " " + option + " ".repeat(width - option.length() - 2) + BORDER_VERTICAL);
        }
        System.out.println(BORDER_VERTICAL + " ".repeat(width) + BORDER_VERTICAL); // Riga vuota
        System.out.println(borderBottom + ANSI_RESET);
    }

    /**
     * Visualizza un elenco di librerie con un numero corrispondente per ciascuna, all'interno di una cornice che simula la forma di un libro,
     * consentendo di scegliere una libreria da visualizzare.
     *
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Il metodo scorre la lista di librerie, creando per ciascuna una cornice con un numero identificativo e ricavando il nome della libreria.</li>
     *   <li>Il nome di ogni libreria deve essere separato da un ":", e deve trovarsi nella parte prima del ":".</li>
     *   <li>Le cornici sono composte utilizzando i caratteri di bordo definiti dalle costanti.</li>
     *   <li>Il numero della libreria viene visualizzato in grassetto e con un colore viola.</li>
     * </ul>
     *
     * @param librerie La lista di librerie da visualizzare.
     */
    public static void mostraLibrerie(String intestazione,List<String> librerie) {
        System.out.println();
        System.out.println(intestazione);
        for (int i = 0; i < librerie.size(); i++) {
            String[] dettagliLibreria = librerie.get(i).split(":", 2);
            if (dettagliLibreria.length > 0) {
                String borderTop = BORDER_TOP_LEFT + BORDER_HORIZONTAL + BORDER_TOP_RIGHT + ANSI_RESET;
                System.out.println(coloraInViola(ANSI_BOLD + borderTop));
                System.out.println(BORDER_VERTICAL + coloraInViola(ANSI_BOLD + (i + 1) + "-\t ") + dettagliLibreria[0].trim());
                String borderBottom = BORDER_BOTTOM_LEFT + BORDER_HORIZONTAL + BORDER_BOTTOM_RIGHT;
                System.out.println(borderBottom + ANSI_RESET);
            }
        }
    }

    /**
     * Gestisce una stringa vuota sostituendola con un simbolo "/".
     *
     * <p>Nel dettaglio:</p>
     * <ul>
     *   <li>Se la stringa è composta solo da spazi, viene restituito il simbolo "/".</li>
     *   <li>In caso contrario, viene restituita la stringa originale.</li>
     * </ul>
     *
     * @param s La stringa da controllare.
     * @return La stringa originale o "/" se vuota.
     */
    public static String gestisciStringaVuota(String s) {
        if (s.trim().equals("")) return "/";
        return s;
    }

}
