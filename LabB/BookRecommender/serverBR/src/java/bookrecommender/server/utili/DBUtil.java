package bookrecommender.server.utili;

import java.sql.SQLException;

public final class DBUtil {
    private DBUtil() {}

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException sqlEx) {
                System.err.println("SQLState: " + sqlEx.getSQLState());
                System.err.println("Error Code: " + sqlEx.getErrorCode());
                System.err.println("Message: " + sqlEx.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.err.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}


