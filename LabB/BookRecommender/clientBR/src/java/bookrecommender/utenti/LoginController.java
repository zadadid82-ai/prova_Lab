package bookrecommender.utenti;

import bookrecommender.utili.ViewsController;
import bookrecommender.utili.ClientUtili;
import bookrecommender.condivisi.utenti.UtentiService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    // Campo privato per il servizio utenti
    private UtentiService utentiService;

    @FXML
    public void initialize() {
        logger.debug("Inizializzazione LoginController");
        // Aggiorna il prompt text per chiarire che si può usare username, email o codice fiscale
        usernameField.setPromptText("Username, Email o Codice Fiscale");
    }

    /**
     * Gestisce il tentativo di login
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        logger.debug("Tentativo di login per utente: " + username);

        // Validazione input
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Errore", "Campi vuoti", 
                     "Inserisci username e password");
            return;
        }

        try {
            // Connessione al servizio RMI solo se utentiService non è già inizializzato
            if (utentiService == null) {
                Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                utentiService = (UtentiService) registry.lookup("UtentiService");
            }

            // Tentativo di autenticazione
            boolean loginSuccess = utentiService.authenticateUser(username, password);
            
            if (loginSuccess) {
                logger.info("Login riuscito per utente: " + username);
                showAlert(Alert.AlertType.INFORMATION, "Successo", "Login riuscito", 
                         "Benvenuto! Login effettuato con successo.");
                
                // Reindirizza all'area privata
                ViewsController.mostraAreaPrivata();
            } else {
                logger.warn("Login fallito per utente: " + username);
                showAlert(Alert.AlertType.ERROR, "Errore", "Credenziali errate", 
                         "Username o password non corretti");
            }

        } catch (RemoteException e) {
            logger.error("Errore di connessione RMI durante il login", e);
            showAlert(Alert.AlertType.ERROR, "Errore", "Errore di connessione", 
                     "Impossibile connettersi al server. Riprova più tardi.");
        } catch (NotBoundException e) {
            logger.error("Servizio UtentiService non trovato nel registro RMI", e);
            showAlert(Alert.AlertType.ERROR, "Errore", "Servizio non disponibile", 
                     "Il servizio di autenticazione non è disponibile.");
        } catch (Exception e) {
            logger.error("Errore imprevisto durante il login", e);
            showAlert(Alert.AlertType.ERROR, "Errore", "Errore imprevisto", 
                     "Si è verificato un errore imprevisto. Riprova più tardi.");
        }
    }

    /**
     * Torna alla schermata di benvenuto
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