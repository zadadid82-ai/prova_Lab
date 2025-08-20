package bookrecommender.libri;

import bookrecommender.condivisi.libri.CercaLibriService;
import bookrecommender.condivisi.libri.Libro;
import bookrecommender.utili.ViewsController;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller per la schermata di ricerca libri, aggiornato per utilizzare
 * un'interfaccia moderna con ToggleButton e un layout migliorato.
 * Gestisce la logica di visualizzazione dei campi, la validazione dell'input
 * e la comunicazione con il servizio RMI CercaLibriService,
 * includendo un meccanismo di fallback con dati demo.
 */
public class CercaLibriController implements Initializable {

    private static final Logger logger = LogManager.getLogger(CercaLibriController.class);

    private CercaLibriService cercaLibriService;

    // --- CAMPI FXML AGGIORNATI ---

    // ToggleButtons per la selezione della modalità
    @FXML private ToggleGroup searchModeGroup;
    @FXML private ToggleButton tbById;
    @FXML private ToggleButton tbByTitle;
    @FXML private ToggleButton tbByAuthor;
    @FXML private ToggleButton tbByAuthorYear;

    // Contenitori per i campi di input
    @FXML private VBox idBox;
    @FXML private VBox titleBox;
    @FXML private VBox authorBox;
    @FXML private GridPane authorYearBox;

    // Campi di testo per l'input utente
    @FXML private TextField idField;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField authorYearAuthorField;
    @FXML private TextField authorYearField;

    // Elementi UI generali
    @FXML private Label messageLabel;
    @FXML private Button searchButton;

    // Tabella per i risultati
    @FXML private TableView<BookRecord> resultsTable;
    @FXML private TableColumn<BookRecord, String> colId;
    @FXML private TableColumn<BookRecord, String> colTitle;
    @FXML private TableColumn<BookRecord, String> colAuthor;
    @FXML private TableColumn<BookRecord, String> colYear;

    // Dati di fallback per la modalità demo
    private final List<BookRecord> demoData = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initServices();
        logger.debug("Inizializzazione CercaLibriController...");

        setupTableColumns();
        setupToggleListener();
        setupDemoData();

        updateVisibleInputFields(); // Imposta la visibilità iniziale corretta
        if (messageLabel != null) {
            messageLabel.setText("Seleziona una modalità e inizia la ricerca");
        }
        logger.info("CercaLibriController inizializzato correttamente.");
    }

    /**
     * Tenta di connettersi al servizio RMI. Se fallisce, disabilita il pulsante
     * di ricerca e l'app funzionerà in modalità demo.
     */
    private void initServices() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost"); // Assicurati che host e porta siano corretti
            cercaLibriService = (CercaLibriService) registry.lookup("CercaLibriService");
            logger.info("Connessione a CercaLibriService RMI riuscita.");
        } catch (NotBoundException | RemoteException e) {
            logger.error("Impossibile connettersi al servizio RMI CercaLibriService. L'app funzionerà in modalità demo.", e);
            showAlert(Alert.AlertType.WARNING,
                "Servizio Non Disponibile",
                "Il servizio di ricerca libri non è raggiungibile.",
                "L'applicazione funzionerà in modalità offline utilizzando dati di esempio.");
            // Non disabilitiamo il pulsante per permettere l'uso dei dati demo.
            cercaLibriService = null;
        }
    }

    /**
     * Configura le colonne della tabella per mostrare i dati dei BookRecord.
     */
    private void setupTableColumns() {
        colId.setCellValueFactory(cell -> cell.getValue().idProperty());
        colTitle.setCellValueFactory(cell -> cell.getValue().titleProperty());
        colAuthor.setCellValueFactory(cell -> cell.getValue().authorProperty());
        colYear.setCellValueFactory(cell -> cell.getValue().yearProperty());
    }

    /**
     * Aggiunge un listener al ToggleGroup per aggiornare l'interfaccia
     * ogni volta che la modalità di ricerca cambia.
     */
    private void setupToggleListener() {
        searchModeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                updateVisibleInputFields();
            }
        });
    }

    /**
     * Inizializza la lista di libri demo per il fallback.
     */
    private void setupDemoData() {
        demoData.add(new BookRecord("1", "Il nome della rosa", "Umberto Eco", "1980"));
        demoData.add(new BookRecord("2", "La coscienza di Zeno", "Italo Svevo", "1923"));
        demoData.add(new BookRecord("3", "Se questo è un uomo", "Primo Levi", "1947"));
        demoData.add(new BookRecord("4", "Il fu Mattia Pascal", "Luigi Pirandello", "1904"));
    }

    /**
     * Gestisce la visibilità dei pannelli di input in base al ToggleButton selezionato.
     */
    private void updateVisibleInputFields() {
        Toggle selected = searchModeGroup.getSelectedToggle();

        // Nascondi tutti i pannelli
        idBox.setVisible(false); idBox.setManaged(false);
        titleBox.setVisible(false); titleBox.setManaged(false);
        authorBox.setVisible(false); authorBox.setManaged(false);
        authorYearBox.setVisible(false); authorYearBox.setManaged(false);

        // Mostra solo quello selezionato
        if (selected == tbById) {
            idBox.setVisible(true); idBox.setManaged(true);
        } else if (selected == tbByTitle) {
            titleBox.setVisible(true); titleBox.setManaged(true);
        } else if (selected == tbByAuthor) {
            authorBox.setVisible(true); authorBox.setManaged(true);
        } else if (selected == tbByAuthorYear) {
            authorYearBox.setVisible(true); authorYearBox.setManaged(true);
        }
        showMessage(""); // Pulisce il messaggio di stato
    }

    /**
     * Metodo eseguito al click del pulsante "Cerca".
     */
    @FXML
    private void onSearch() {
        Toggle selected = searchModeGroup.getSelectedToggle();
        if (selected == null) {
            showMessage("Seleziona una modalità di ricerca.");
            return;
        }

        try {
            if (selected == tbById)           { searchById(); }
            else if (selected == tbByTitle)   { searchByTitle(); }
            else if (selected == tbByAuthor)  { searchByAuthor(); }
            else if (selected == tbByAuthorYear) { searchByAuthorAndYear(); }
        } catch (RemoteException re) {
            logger.error("Errore di comunicazione remota durante la ricerca.", re);
            showAlert(Alert.AlertType.ERROR, "Errore Remoto", "Errore durante la comunicazione con il server.", re.getMessage());
        } catch (Exception e) {
            logger.error("Errore imprevisto durante la ricerca.", e);
            showAlert(Alert.AlertType.ERROR, "Errore Imprevisto", "Si è verificato un errore generico.", e.getMessage());
        }
    }

    // --- LOGICA DI RICERCA SPECIFICA PER MODALITÀ ---

    private void searchById() throws RemoteException {
        String idText = safeGet(idField);
        if (idText.isEmpty()) { showMessage("L'ID non può essere vuoto."); return; }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            showMessage("L'ID deve essere un numero intero.");
            return;
        }

        if (cercaLibriService != null) {
            Libro libro = cercaLibriService.getTitoloLibroById(id);
            resultsTable.getItems().clear();
            if (libro != null) {
                resultsTable.getItems().add(mapLibroToRecord(libro));
                showMessage("1 risultato trovato.");
            } else {
                showMessage("Nessun libro trovato con ID " + id);
            }
        } else {
            // Fallback Demo
            List<BookRecord> results = demoData.stream()
                .filter(b -> b.getId().equals(idText))
                .collect(Collectors.toList());
            updateTableWithDemoResults(results);
        }
    }

    private void searchByTitle() throws RemoteException {
        String titolo = safeGet(titleField);
        if (titolo.isEmpty()) { showMessage("Il titolo non può essere vuoto."); return; }

        if (cercaLibriService != null) {
            // L'interfaccia RMI ha metodi void, quindi mostriamo solo una notifica.
            cercaLibriService.cercaLibro_Per_Titolo(titolo);
            showRequestSentAlert("Ricerca per titolo inviata al server.");
        } else {
            // Fallback Demo
            List<BookRecord> results = demoData.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(titolo.toLowerCase()))
                .collect(Collectors.toList());
            updateTableWithDemoResults(results);
        }
    }

    private void searchByAuthor() throws RemoteException {
        String autore = safeGet(authorField);
        if (autore.isEmpty()) { showMessage("L'autore non può essere vuoto."); return; }

        if (cercaLibriService != null) {
            cercaLibriService.cercaLibro_Per_Autore(autore);
            showRequestSentAlert("Ricerca per autore inviata al server.");
        } else {
            // Fallback Demo
            List<BookRecord> results = demoData.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(autore.toLowerCase()))
                .collect(Collectors.toList());
            updateTableWithDemoResults(results);
        }
    }

    private void searchByAuthorAndYear() throws RemoteException {
        String autore = safeGet(authorYearAuthorField);
        String anno = safeGet(authorYearField);

        if (autore.isEmpty()) { showMessage("L'autore non può essere vuoto."); return; }
        if (!isValidYear(anno)) { showMessage("Inserisci un anno valido (es. 1984)."); return; }

        if (cercaLibriService != null) {
            cercaLibriService.cercaLibro_Per_Autore_e_Anno(autore, anno);
            showRequestSentAlert("Ricerca per autore e anno inviata al server.");
        } else {
            // Fallback Demo
            List<BookRecord> results = demoData.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(autore.toLowerCase()) && b.getYear().equals(anno))
                .collect(Collectors.toList());
            updateTableWithDemoResults(results);
        }
    }

    /**
     * Pulisce tutti i campi di input e la tabella dei risultati.
     */
    @FXML
    private void onClear() {
        idField.clear();
        titleField.clear();
        authorField.clear();
        authorYearAuthorField.clear();
        authorYearField.clear();
        resultsTable.getItems().clear();
        showMessage("");
    }

    /**
     * Torna alla schermata di benvenuto.
     */
    @FXML
    private void onBack() {
        try {
            ViewsController.mostraBenvenuti();
        } catch (Exception e) {
            logger.error("Impossibile tornare alla schermata di benvenuto.", e);
            showAlert(Alert.AlertType.ERROR, "Errore di Navigazione", "Impossibile caricare la schermata precedente.", e.getMessage());
        }
    }

    // --- METODI HELPER E UTILITY ---

    private void updateTableWithDemoResults(List<BookRecord> results) {
        resultsTable.setItems(FXCollections.observableArrayList(results));
        showMessage(String.format("%d risultato/i trovato/i (modalità demo).", results.size()));
    }
    
    private BookRecord mapLibroToRecord(Libro libro) {
        return null;
       /*  if (libro == null) return null;
        // Assumendo che l'oggetto Libro abbia questi getter. Adattali se necessario.
        String id = String.valueOf(libro.getId());
        String titolo = libro.getTitolo() != null ? libro.getTitolo() : "N/D";
        String autore = libro.getAutore() != null ? libro.getAutore() : "N/D";
        String anno = libro.getAnno() != null ? libro.getAnno() : "N/D";
        return new BookRecord(id, titolo, autore, anno);*/
    }

    private String safeGet(TextField tf) {
        return tf != null && tf.getText() != null ? tf.getText().trim() : "";
    }

    private void showMessage(String msg) {
        if (messageLabel != null) {
            messageLabel.setText(msg);
        }
    }
    
    private void showRequestSentAlert(String header) {
        showAlert(Alert.AlertType.INFORMATION,
            "Richiesta Inviata",
            header,
            "Il servizio RMI non restituisce risultati diretti per questa operazione."
        );
    }
    
    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isValidYear(String s) {
        return s != null && s.matches("\\d{4}");
    }

    // --- CLASSE INTERNA PER LA TABELLA (JavaFX Bean) ---

    public static class BookRecord {
        private final SimpleStringProperty id;
        private final SimpleStringProperty title;
        private final SimpleStringProperty author;
        private final SimpleStringProperty year;

        public BookRecord(String id, String title, String author, String year) {
            this.id = new SimpleStringProperty(id);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.year = new SimpleStringProperty(year);
        }

        public String getId() { return id.get(); }
        public String getTitle() { return title.get(); }
        public String getAuthor() { return author.get(); }
        public String getYear() { return year.get(); }

        public StringProperty idProperty() { return id; }
        public StringProperty titleProperty() { return title; }
        public StringProperty authorProperty() { return author; }
        public StringProperty yearProperty() { return year; }
    }

    

}