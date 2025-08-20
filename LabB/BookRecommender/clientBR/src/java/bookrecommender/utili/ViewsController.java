package bookrecommender.utili;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bookrecommender.BookRecommender;

import java.io.IOException;
import java.util.Objects;


public class ViewsController {
    private static Scene primaryScene;
    private static Stage primaryStage;
    private static final Logger logger = LogManager.getLogger(ViewsController.class);

    /**
     * Inizializza il controller con lo stage principale
     */
    public static void initialize(Stage stage) {
        primaryStage = stage;
        logger.info("ViewsController inizializzato con stage principale");
    }

    private static void setRoot(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(BookRecommender.class.getResource(fxmlPath)));
            if (primaryScene == null) {
                primaryStage.setScene(new Scene(root));
                primaryScene = primaryStage.getScene();
                primaryStage.show();
            } else {
                primaryScene.setRoot(root);
            }
            logger.debug("Cambiata view a: " + fxmlPath);
        } catch (IOException e) {
            logger.error("Errore nel caricamento della view: " + fxmlPath, e);
            showErrorView();
        }
    }

    /**
     * Mostra la schermata di benvenuto
     */
    public static void mostraBenvenuti() {
        setRoot("/bookrecommender/benvenuti-view.fxml");
    }

    /**
     * Mostra la schermata di login
     */
    public static void mostraLogin() {
        setRoot("/bookrecommender/login-view.fxml");
    }

    /**
     * Mostra la schermata di registrazione
     */
    public static void mostraRegistrazione() {
        setRoot("/bookrecommender/registrazione-view.fxml");
    }

    /**
     * Mostra la schermata dell'area privata
     */
    public static void mostraAreaPrivata() {
        setRoot("/bookrecommender/areaprivata-view.fxml");
    }

    /**
     * Mostra la schermata di ricerca libri
     */
    public static void mostraCercaLibri() {
        setRoot("/bookrecommender/cercaLibri-view.fxml");
    }

    /**
     * Mostra la schermata di errore in caso di problemi
     */
    private static void showErrorView() {
        try {
            // Creo una schermata di errore semplice
            javafx.scene.control.Label errorLabel = new javafx.scene.control.Label("Errore nel caricamento dell'interfaccia");
            errorLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: red;");
            
            if (primaryScene == null) {
                primaryStage.setScene(new Scene(errorLabel));
                primaryScene = primaryStage.getScene();
            } else {
                primaryScene.setRoot(errorLabel);
            }
            logger.error("Mostrata schermata di errore");
        } catch (Exception e) {
            logger.error("Errore critico nell'applicazione", e);
            System.exit(1);
        }
    }
}