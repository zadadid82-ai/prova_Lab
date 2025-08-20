package bookrecommender.utili;

/*
 Controller Java per la schermata di benvenuto.
 - Gestisce solo la schermata di benvenuto
 - Aggiunge micro-interazioni (scale su hover) ai pulsanti principali
 - Utilizza ViewsController per navigare tra le view
*/

import bookrecommender.utili.ViewsController;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BenvenutiController {

    private static final Logger logger = LogManager.getLogger(BenvenutiController.class);

    // Root pane
    @FXML private StackPane rootPane;

    // Views principali
    @FXML private VBox mainContainer;

    // Elementi della main view
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Button searchButton;

    // Initialize chiamato automaticamente da FXMLLoader
    @FXML
    public void initialize() {
        logger.debug("Inizializzazione BenvenutiController");
        
        // Aggiungo una micro-interazione (leggera scala) ai pulsanti principali
        addHoverScale(loginButton, 1.04, 120);
        addHoverScale(registerButton, 1.04, 120);
        addHoverScale(searchButton, 1.04, 120);
    }

    /* -------------------------
       Metodi per cambiare view utilizzando ViewsController
       ------------------------- */
    @FXML
    public void showLoginView() {
        logger.debug("Richiesta visualizzazione schermata login");
        ViewsController.mostraLogin();
    }

    @FXML
    public void showRegisterView() {
        logger.debug("Richiesta visualizzazione schermata registrazione");
        ViewsController.mostraRegistrazione();
    }

    @FXML
    public void showSearchView() {
        logger.debug("Richiesta visualizzazione schermata ricerca");
        // Per ora reindirizza alla registrazione (la ricerca richiede autenticazione)
        ViewsController.mostraCercaLibri();
    }

    /* -------------------------
       Utility: aggiunge un piccolo effetto di scala su hover al nodo passato
       - toScale: valore finale (es. 1.04)
       - durationMs: durata in ms della transizione
       ------------------------- */
    private void addHoverScale(javafx.scene.Node node, double toScale, int durationMs) {
        if (node == null) return; // sicurezza

        // Ingresso mouse -> scala verso 'toScale'
        node.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(durationMs), node);
            st.setToX(toScale);
            st.setToY(toScale);
            st.play();
        });

        // Uscita mouse -> ritorna a scala 1.0
        node.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(durationMs), node);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
}
