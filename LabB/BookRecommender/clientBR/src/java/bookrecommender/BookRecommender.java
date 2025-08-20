package bookrecommender;
public class BookRecommender {
    public static void main(String[] args) {
        try {
            // Avvia l'applicazione JavaFX
            GUI.launch(GUI.class, args);
        } catch (Exception e) {
            System.err.println("Errore nell'avvio dell'applicazione: " + e.getMessage());
            e.printStackTrace();
        }
    }
}