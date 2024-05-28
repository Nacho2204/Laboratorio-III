package tubanco.conexion;

import tubanco.model.CuentaBancaria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuentaBancariaDAO {

    public List<CuentaBancaria> obtenerCuentasPorCliente(long dniCliente) {
        List<CuentaBancaria> cuentas = new ArrayList<>();
        try {
            Connection conexion = ConexionSQLite.obtenerConexion();
            String query = "SELECT * FROM Cuenta WHERE Titular = ?";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setLong(1, dniCliente);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                CuentaBancaria cuenta = new CuentaBancaria();
                cuenta.setNumeroCuenta(result.getInt("CuentaID"));
                cuenta.setFechaCreacion(result.getDate("Fecha_Creacion").toLocalDate().atStartOfDay());
                // Setear los demás atributos
                cuentas.add(cuenta);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuentas;
    }

    public void crearCuentaBancaria(CuentaBancaria cuenta) {
        try {
            Connection conexion = ConexionSQLite.obtenerConexion();
            String query = "INSERT INTO Cuenta (CuentaID, Fecha_Creacion, Tipo_Cuenta, Saldo, Titular, Moneda) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, cuenta.getNumeroCuenta());
            statement.setDate(2, java.sql.Date.valueOf(cuenta.getFechaCreacion().toLocalDate()));
            statement.setString(3, cuenta.getTipoCuenta().name());
            statement.setDouble(4, cuenta.getSaldo());
            statement.setLong(5,cuenta.getTitular().getDni());
            statement.setString(6, cuenta.getMoneda());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
