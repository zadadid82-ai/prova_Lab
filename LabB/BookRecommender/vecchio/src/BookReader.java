import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * La classe fornisce metodi per leggere i dati di un libro da un file CSV.
 * I dati letti servono per creare un oggetto  {@link Book}, e il file CSV deve avere una struttura specifica. (id,titolo,"autori","descrizione","categoria","editore",prezzo,dataDiPubblicazione,annodiPubblicazione)
 *
 * @author Taha
 */

public class BookReader {

    /**
     * Legge un file CSV contenente informazioni sui libri e restituisce una lista di oggetti {@link Book}.
     * <p>
     * Nel dettaglio:
     * <ul>
     *   <li>Crea una lista vuota "books" per memorizzare gli oggetti {@link Book} che verranno creati.</li>
     *   <li>Utilizza un {@link BufferedReader} per aprire e leggere il file CSV specificato dal parametro "filePath".</li>
     *   <li>Legge il file riga per riga all'interno di un ciclo "while":</li>
     *     <li>Per ogni riga, chiama il metodo {@link #splitLine} per dividere la riga nei rispettivi campi, tenendo conto delle virgole che possono essere racchiuse tra virgolette.</li>
     *     <li>Controlla se la riga ha esattamente 9 campi per creare un oggetto {@link Book} valido.</li>
     *     <li>Verifica se l'oggetto {@link Book}  creato è valido (non "null"). Se valido, aggiunge il libro alla lista di {@link Book} .</li>
     *   <li>Gestisce eventuali eccezioni di tipo IOException che potrebbero verificarsi durante la lettura del file, stampando un messaggio di errore appropriato.</li>
     *   <li>Restituisce la lista "books" contenente tutti gli oggetti {@link Book}  creati dal file CSV.</li>
     * </ul>
     *
     * @param filePath Il percorso del file CSV da leggere.
     * @return Una lista di oggetti {@link Book}  creati dal file CSV.
     */
    public static List<Book> readBooks(String filePath) {
        List<Book> books = new ArrayList<>();//Crea un List per archiviare i libri

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {   // Legge ogni riga del file
                String[] fields = splitLine(line);
                // Divide la riga in campi tenendo conto delle virgole tra virgolette

                if (fields.length == 9) {
                    Book book = createBookFromFields(fields);// Crea un oggetto Libro dai campi
                    if (book != null) {// Verifica che il libro sia valido
                        books.add(book);// Aggiungi il libro alla lista1
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nella lettura del file: " + e.getMessage());
        }

        return books;
    }

    /**
     * Divide una riga di testo in un array di stringhe, tenendo conto delle virgole all'interno delle virgolette.
     * <p>
     * Nel dettaglio:
     * <ol>
     *     <li>Crea una lista vuota per memorizzare i campi estratti dalla riga.</li>
     *     <li>Utilizza un {@link StringBuilder} per costruire i campi carattere per carattere.</li>
     *     <li>Imposta un booleano a false per tracciare se ci si trova all'interno di virgolette.</li>
     *     <li>Itera attraverso ciascun carattere della riga:</li>
     *
     *         <li>Se il carattere è una virgoletta ("), inverte il valore del booleano per indicare l'inizio o la fine di un campo racchiuso tra virgolette.</li>
     *         <li>Se il carattere è una virgola (,) e il booleano è false, aggiunge il contenuto dello {@link StringBuilder} alla lista, lo resetta, e inizia a costruire il prossimo campo.</li>
     *         <li>Altrimenti, aggiunge il carattere corrente allo {@link StringBuilder}.</li>
     *
     *     <li>Dopo aver iterato attraverso tutti i caratteri della riga, aggiunge l'ultimo campo costruito alla lista.</li>
     *     <li>Converte la lista in un array di stringhe e lo restituisce.</li>
     * </ol>
     *
     * @param line La riga di testo da dividere.
     * @return Un array di stringhe contenente i campi estratti dalla riga.
     */
    private static String[] splitLine(String line) {
        List<String> fields = new ArrayList<>(); // creare un List  per memorizzare i campi
        StringBuilder field = new StringBuilder();
        boolean virgolette = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                virgolette = !virgolette;
            } else if (c == ',' && !virgolette) { // quando si incontra una virgola fuori dalle virgolette
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(c);
            }
        }
        fields.add(field.toString()); // aggiunge tutti i campi della riga line in una lista di stringhe
        return fields.toArray(new String[0]);
    }

    /**
     * Crea un oggetto {@link Book} utilizzando un array di stringhe che rappresentano i campi del libro.
     *
     * <p>Nel dettaglio: </p>
     *
     * <ol>
     *     <li>Estrae l'ID del libro convertendo il primo campo (indice 0) in un intero.</li>
     *     <li>Estrae il titolo del libro dal secondo campo (indice 1).</li>
     *     <li>Estrae gli autori del libro dal terzo campo (indice 2).</li>
     *     <li>Inizializza le variabili "description", "category", "publisher", "price", "publishDateMonth", e "publishDateYear" con valori vuoti o null.</li>
     *    <li> Crea e restituisce un nuovo oggetto "Book" utilizzando i campi estratti.</li>
     *</ol>
     *
     * @param fields Un array di stringhe che rappresentano i campi del libro.
     * @return Un oggetto Book creato dai campi forniti, oppure "null" se si verifica un errore durante la lettura del file.
     */
    private static Book createBookFromFields(String[] fields) {//Metodo per creare un oggetto Book dai campi
        if (fields.length == 9) {
            int id = Integer.parseInt(fields[0]);
            String title = fields[1];// Il secondo campo è il titolo
            String authors = fields[2];//Il terzo campo è per gli autori
            String description = fields[3];
            String category = fields[4];
            String publisher = fields[5];
            String price = fields[6];
            String publishDateMonth = fields[7];
            String publishDateYear = fields[8];
            return new Book(id, title, authors, description, category, publisher, price, publishDateMonth, publishDateYear);
        } else {
            System.out.println(LayOut.coloraErrore(" Errore nella lettura del file alla riga " + fields[0]));
            return null;
        }
    }


}

