package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLiteConnection {
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

}