package tubanco.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLite {
    private static Connection conexion;

    private ConexionSQLite() {}

    public static Connection obtenerConexion() {
        if (conexion == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                conexion = DriverManager.getConnection("jdbc:sqlite:./database/Sistema_BancarioDB.db");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

