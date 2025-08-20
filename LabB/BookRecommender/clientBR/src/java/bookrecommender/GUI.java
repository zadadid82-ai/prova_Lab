package bookrecommender;

import bookrecommender.utili.ViewsController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class GUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Inizializza il ViewsController con lo stage principale
            ViewsController.initialize(primaryStage);
            
            // Carica il file FXML di benvenuto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/bookrecommender/benvenuti-view.fxml"));
            if (loader.getLocation() == null) {
                throw new IOException("Impossibile trovare il file FXML: /bookrecommender/benvenuti-view.fxml");
            }
            Parent root = loader.load();
            
            // Crea la scena
            Scene scene = new Scene(root);
            
            // Configura la finestra principale
            primaryStage.setTitle("BookRecommender - Scopri i tuoi prossimi libri");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.setResizable(true);
            
            // Centra la finestra sullo schermo
            primaryStage.centerOnScreen();
            
            // Mostra la finestra
            primaryStage.show();
            
        } catch (IOException e) {
            System.err.println("Errore nel caricamento dell'interfaccia: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}