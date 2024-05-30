package tubanco.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class MovimientosDAO {
    
    public void generarDeposito(int cuenta, double monto) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            String query = "INSERT INTO MovimientosDeCuenta (Fecha, Detalles, Monto, CuentaID) " +
                           "VALUES (?, ?, ?, ?)";
            statement = conexion.prepareStatement(query);
            statement.setString(1, LocalDateTime.now().toString());
            statement.setString(2, "DEPOSITO");
            statement.setDouble(3, monto);
            statement.setInt(4, cuenta);
            statement.executeUpdate();
            conexion.commit();
        } catch (SQLException e) {
            System.out.println("Error al realizar el deposito: " + e.getMessage());
        }
    }

    public void generarRetiro(int cuenta, double monto){
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            String query = "INSERT INTO MovimientosDeCuenta (Fecha, Detalles, Monto, CuentaID) " +
                           "VALUES (?, ?, ?, ?)";
            statement = conexion.prepareStatement(query);
            statement.setString(1, LocalDateTime.now().toString());
            statement.setString(2, "RETIRO");
            statement.setDouble(3, monto);
            statement.setInt(4, cuenta);
            statement.executeUpdate();
            conexion.commit();
            System.out.println("Retiro realizado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al realizar el retiro: " + e.getMessage());
        }
    }

    public void generarTransferencia(int cuentaOrigen, int cuentaDestino, double monto){
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            String query = "INSERT INTO MovimientosDeCuenta (Fecha, Detalles, Monto, CuentaID) " +
                           "VALUES (?, ?, ?, ?)";
            statement = conexion.prepareStatement(query);
            statement.setString(1, LocalDateTime.now().toString());
            statement.setString(2, "TRANSFERENCIA A CUENTA " + cuentaDestino);
            statement.setDouble(3, monto);
            statement.setInt(4, cuentaOrigen);
            statement.executeUpdate();
            query = "INSERT INTO MovimientosDeCuenta (Fecha, Detalles, Monto, CuentaID) " +
                    "VALUES (?, ?, ?, ?) WHERE CuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setString(1, LocalDateTime.now().toString());
            statement.setString(2, "TRANSFERENCIA RECIBIDA DE CUENTA " + cuentaOrigen);
            statement.setDouble(3, monto);
            statement.setInt(4, cuentaDestino);
            conexion.commit();
            System.out.println("Transferencia realizada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al realizar la transferencia: " + e.getMessage());
        }
    }

    public void mostrarMovimientos(int cuenta){
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            String query = "SELECT * FROM MovimientosDeCuenta WHERE CuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, cuenta);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Fecha: " + resultSet.getString("Fecha"));
                System.out.println("Detalles: " + resultSet.getString("Detalles"));
                System.out.println("Monto: $" + resultSet.getDouble("Monto"));
                System.out.println("CuentaID: " + resultSet.getInt("CuentaID"));
                System.out.println("-------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("No se encontraron movimientos en su cuenta: " + e.getMessage());
        }
    }


}
