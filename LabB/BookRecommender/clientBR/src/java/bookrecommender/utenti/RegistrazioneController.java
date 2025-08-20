package bookrecommender.utenti;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bookrecommender.utili.ViewsController;
import bookrecommender.condivisi.utenti.Utenti;
import bookrecommender.condivisi.utenti.UtentiService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.function.UnaryOperator;

import static bookrecommender.utili.ClientUtili.resetTextFields;

public class RegistrazioneController {
    private static final Logger logger = LogManager.getLogger(RegistrazioneController.class);
    
    @FXML
    private TextField textFieldNome;
    @FXML
    private TextField textFieldCognome;
    @FXML
    private TextField textFieldCodiceFiscale;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldIDUtente;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordFieldRipetiPassword;

    // Campo privato per il servizio utenti
    private UtentiService utentiService;

    @FXML
    private void inizializzare() {
        logger.debug("Inizializzazione RegistrazioneController");
        setupInputValidation();
    }

    /**
     * Configura la validazione degli input
     */
    private void setupInputValidation() {
        // Validazione codice fiscale (solo lettere e numeri)
        UnaryOperator<javafx.scene.control.TextFormatter.Change> cfFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[A-Z0-9]*")) {
                return change;
            }
            return null;
        };
        textFieldCodiceFiscale.setTextFormatter(new javafx.scene.control.TextFormatter<>(cfFilter));
        
        // Validazione email (formato base)
        UnaryOperator<javafx.scene.control.TextFormatter.Change> emailFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z0-9@._-]*")) {
                return change;
            }
            return null;
        };
        textFieldEmail.setTextFormatter(new javafx.scene.control.TextFormatter<>(emailFilter));
        
        // Validazione username (solo lettere, numeri e underscore)
        UnaryOperator<javafx.scene.control.TextFormatter.Change> usernameFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z0-9_]*")) {
                return change;
            }
            return null;
        };
        textFieldIDUtente.setTextFormatter(new javafx.scene.control.TextFormatter<>(usernameFilter));
    }

    /**
     * Gestisce la registrazione dell'utente
     */
    @FXML
    private void handleRegister() {
        logger.debug("Tentativo di registrazione nuovo utente");

        // Validazione input
        if (!validateInput()) {
            return;
        }

        try {
            // Creazione oggetto Utenti con userID personalizzato
            Utenti nuovoUtente = new Utenti(
                textFieldNome.getText().trim(),
                textFieldCognome.getText().trim(),
                textFieldCodiceFiscale.getText().trim().toUpperCase(),
                textFieldEmail.getText().trim(),
                textFieldIDUtente.getText().trim(),
                passwordField.getText()
            );

            // Connessione al servizio RMI solo se utentiService non è già inizializzato
            if (utentiService == null) {
                Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                utentiService = (UtentiService) registry.lookup("UtentiService");
            }

            // Tentativo di registrazione
            boolean registrazioneSuccess = utentiService.registerUser(nuovoUtente);
            
            if (registrazioneSuccess) {
                logger.info("Registrazione riuscita per utente: " + nuovoUtente.userID());
                showAlert(Alert.AlertType.INFORMATION, "Successo", "Registrazione completata", 
                         "Account creato con successo! Ora puoi effettuare il login.");
                
                // Pulisce i campi e torna al menu
                resetTextFields(textFieldNome, textFieldCognome, textFieldCodiceFiscale, 
                              textFieldEmail, textFieldIDUtente);
                passwordField.clear();
                passwordFieldRipetiPassword.clear();
                
                ViewsController.mostraBenvenuti();
            } else {
                logger.warn("Registrazione fallita per utente: " + nuovoUtente.userID());
                showAlert(Alert.AlertType.ERROR, "Errore", "Registrazione fallita", 
                         "Username, email o codice fiscale già esistente. Verifica i dati inseriti.");
            }

        } catch (RemoteException e) {
            logger.error("Errore di connessione RMI durante la registrazione", e);
            showAlert(Alert.AlertType.ERROR, "Errore", "Errore di connessione", 
                     "Impossibile connettersi al server. Riprova più tardi.");
        } catch (NotBoundException e) {
            logger.error("Servizio UtentiService non trovato nel registro RMI", e);
            showAlert(Alert.AlertType.ERROR, "Errore", "Servizio non disponibile", 
                     "Il servizio di registrazione non è disponibile.");
        } catch (Exception e) {
            logger.error("Errore imprevisto durante la registrazione", e);
            showAlert(Alert.AlertType.ERROR, "Errore", "Errore imprevisto", 
                     "Si è verificato un errore imprevisto. Riprova più tardi.");
        }
    }

    /**
     * Valida i campi di input
     */
    private boolean validateInput() {
        // Controllo campi vuoti
        if (textFieldNome.getText().trim().isEmpty() || 
            textFieldCognome.getText().trim().isEmpty() ||
            textFieldCodiceFiscale.getText().trim().isEmpty() ||
            textFieldEmail.getText().trim().isEmpty() ||
            textFieldIDUtente.getText().trim().isEmpty() ||
            passwordField.getText().isEmpty() ||
            passwordFieldRipetiPassword.getText().isEmpty()) {
            
            showAlert(Alert.AlertType.ERROR, "Errore", "Campi vuoti", 
                     "Tutti i campi sono obbligatori");
            return false;
        }

        // Controllo lunghezza username
        String username = textFieldIDUtente.getText().trim();
        if (username.length() < 3) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Username troppo corto", 
                     "L'username deve essere di almeno 3 caratteri");
            return false;
        }
        
        if (username.length() > 20) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Username troppo lungo", 
                     "L'username deve essere di massimo 20 caratteri");
            return false;
        }

        // Controllo lunghezza password
        if (passwordField.getText().length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Password troppo corta", 
                     "La password deve essere di almeno 6 caratteri");
            return false;
        }

        // Controllo conferma password
        if (!passwordField.getText().equals(passwordFieldRipetiPassword.getText())) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Password non coincidono", 
                     "Le password inserite non coincidono");
            return false;
        }

        // Controllo formato email
        String email = textFieldEmail.getText().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Email non valida", 
                     "Inserisci un indirizzo email valido");
            return false;
        }

        // Controllo codice fiscale
        String cf = textFieldCodiceFiscale.getText().trim();
        if (cf.length() != 16) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Codice fiscale non valido", 
                     "Il codice fiscale deve essere di 16 caratteri");
            return false;
        }

        return true;
    }

    /**
     * Visualizza il menù principale.
     */
    @FXML
    protected void onBackButtonClick() {
        logger.debug("Richiesta di tornare al menu principale");
        ViewsController.mostraBenvenuti();
    }

    /**
     * Mostra un alert all'utente
     */
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}