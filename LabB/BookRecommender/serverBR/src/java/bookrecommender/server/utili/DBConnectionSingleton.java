package bookrecommender.server.utili;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionSingleton {
    private static Connection connection;
    private static String jdbcUrl;
    private static String username;
    private static String password;

    public static synchronized void initialiseConnection(String jdbcUrl, String user, String password) throws SQLException {
        DBConnectionSingleton.jdbcUrl = jdbcUrl;
        DBConnectionSingleton.username = user;
        DBConnectionSingleton.password = password;
        closeConnectionQuietly();
        connection = DriverManager.getConnection(jdbcUrl, user, password);
    }

    public static synchronized Connection getConnection() {
        return connection;
    }

    public static synchronized Connection openNewConnection() throws SQLException {
        if (jdbcUrl == null) {
            throw new SQLException("Database non inizializzato");
        }
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public static synchronized void closeConnectionQuietly() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) { }
            connection = null;
        }
    }
}


