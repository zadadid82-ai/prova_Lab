# BookRecommender

Sistema di raccomandazione libri con architettura client-server basata su JavaFX e RMI.

## Struttura del Progetto

```
BookRecommender/
├── clientBR/                 # Client JavaFX
│   ├── src/java/bookrecommender/
│   │   ├── utili/           # Controller e utility
│   │   ├── utenti/          # Controller per utenti
│   │   └── GUI.java         # Classe principale client
│   └── src/risorse/         # File FXML e CSS
├── serverBR/                 # Server RMI
│   ├── src/java/bookrecommender/
│   │   ├── utenti/          # Implementazione servizi
│   │   └── ServerMain.java  # Classe principale server
│   └── src/risorse/         # Configurazioni
├── inComune/                 # Classi condivise
│   └── src/java/bookrecommender/
│       └── utenti/          # Modelli dati
└── creazioneDB/              # Script per database
```

## Funzionalità Implementate

### ✅ Completate
- **Sistema di navigazione**: `ViewsController` gestisce il cambio tra le view
- **Schermata di benvenuto**: Menu principale con pulsanti per login, registrazione e ricerca
- **Schermata di login**: Form di accesso con validazione (username, email o codice fiscale)
- **Schermata di registrazione**: Form completo con validazione campi e userID personalizzato
- **Architettura RMI**: Client-server con servizi remoti
- **Logging**: Sistema di logging completo con Log4j2
- **Validazione input**: Controlli sui campi di registrazione e login
- **Database PostgreSQL**: Gestione automatica del database con script Java

### 🔄 In Sviluppo
- Area privata utente
- Sistema di ricerca libri
- Raccomandazioni personalizzate

## Requisiti di Sistema

- Java 11 o superiore
- Maven 3.6+
- PostgreSQL 12+
- JavaFX 17+

## Installazione e Configurazione

### 1. Database PostgreSQL
Il database viene creato automaticamente tramite lo script Java nella cartella `creazioneDB`:

```bash
# Compila e esegui lo script di creazione database
cd creazioneDB
mvn clean package
java -jar target/DBCreatorBR-1.0-jar-with-dependencies.jar <username> <password>
```

Lo script creerà automaticamente:
- Database `dbBR`
- Tabella `UtentiRegistrati` (con user_id personalizzato come chiave primaria)
- Tabella `Libri` (popolata da CSV)
- Tabelle per librerie, valutazioni e consigli

### 2. Configurazione Server
1. Assicurati che PostgreSQL sia in esecuzione
2. Compila il server: `cd serverBR && mvn clean package`
3. Avvia il server: `mvn exec:java -Dexec.mainClass="bookrecommender.ServerMain"`

### 3. Configurazione Client
1. Compila il client: `cd clientBR && mvn clean package`
2. Assicurati che il server sia in esecuzione
3. Avvia il client: `mvn exec:java -Dexec.mainClass="bookrecommender.BookRecommender"`

## Utilizzo

### Avvio del Server
```bash
cd serverBR
mvn exec:java -Dexec.mainClass="bookrecommender.ServerMain"
```

### Avvio del Client
```bash
cd clientBR
mvn exec:java -Dexec.mainClass="bookrecommender.BookRecommender"
```

### Script di Avvio Automatico
- **Windows**: Usa `start_server.bat` e `start_client.bat`
- **Linux/Mac**: Crea script bash equivalenti

## Architettura

### Pattern MVC
- **Model**: `Utenti` (record), `UtentiService` (interfaccia RMI)
- **View**: File FXML (`benvenuti-view.fxml`, `login-view.fxml`, `registrazione-view.fxml`)
- **Controller**: `ViewsController`, `LoginController`, `RegistrazioneController`

### Comunicazione RMI
- **Client** → **Server**: Chiamate remote per autenticazione e registrazione
- **Server** → **Database**: Accesso dati tramite JDBC PostgreSQL
- **Logging**: Tracciamento completo delle operazioni

### Database Schema
- **UtentiRegistrati**: user_id personalizzato come chiave primaria, email e codice fiscale univoci
- **Libri**: Catalogo libri con metadati completi
- **Librerie**: Librerie personali degli utenti
- **Valutazioni**: Sistema di rating multi-dimensionale
- **Consigli**: Raccomandazioni basate su preferenze

## Struttura delle Classi

### ViewsController
Gestisce la navigazione tra le view dell'applicazione:
- `mostraBenvenuti()`: Schermata principale
- `mostraLogin()`: Form di accesso
- `mostraRegistrazione()`: Form di registrazione
- `mostraAreaPrivata()`: Area utente (da implementare)

### LoginController
Gestisce l'autenticazione degli utenti:
- Validazione input
- Chiamate RMI al server
- Supporto per login con username, email o codice fiscale
- Gestione errori e feedback utente

### RegistrazioneController
Gestisce la registrazione di nuovi utenti:
- Validazione completa dei campi
- Controlli di sicurezza
- UserID personalizzato scelto dall'utente
- Salvataggio nel database PostgreSQL

### UtentiService (RMI)
Interfaccia per i servizi remoti:
- `authenticateUser()`: Autenticazione
- `registerUser()`: Registrazione
- `isUsernameExists()`: Verifica duplicati

## Sicurezza

- Password non vengono mai loggate
- Validazione lato client e server
- Controlli sui campi di input
- Gestione sicura delle connessioni RMI
- Codice fiscale, email e userID univoci per utente

## Logging

Il sistema utilizza Log4j2 per il logging completo:
- **Console**: Output immediato per debug
- **File**: Log persistenti per audit
- **Livelli**: DEBUG, INFO, WARN, ERROR

## Troubleshooting

### Problemi Comuni

1. **Errore RMI**: Verifica che il server sia in esecuzione sulla porta 1099
2. **Errore Database**: Controlla che PostgreSQL sia attivo e le credenziali corrette
3. **Errore JavaFX**: Verifica la versione di Java e JavaFX
4. **Database non trovato**: Esegui prima lo script `creazioneDB`

### Log di Debug
I log dettagliati sono disponibili in:
- `logs/client.log` per il client
- `logs/server.log` per il server

## Sviluppo Futuro

- [ ] Sistema di raccomandazione libri
- [ ] Gestione preferenze utente
- [ ] Interfaccia per amministratori
- [ ] API REST per integrazioni esterne
- [ ] Test automatizzati
- [ ] Documentazione API

## Contributi

Per contribuire al progetto:
1. Fork del repository
2. Crea un branch per la feature
3. Implementa le modifiche
4. Crea una Pull Request

## Licenza

Questo progetto è rilasciato sotto licenza MIT.
