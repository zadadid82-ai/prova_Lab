package bookrecommender.utili;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class ClientUtili {

     public static void resetTextFields(TextField... textFields) {
        for (TextField textField : textFields)
            textField.clear();
    }




    
}
