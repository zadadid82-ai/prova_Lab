package bookrecommender.condivisi.utenti;

import java.io.Serial;
import java.io.Serializable;

public record Utenti(String nome, String cognome, String codFiscale, String email, String userID, String password) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    

}
