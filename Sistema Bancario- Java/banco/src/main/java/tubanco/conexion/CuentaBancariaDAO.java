package tubanco.conexion;

import tubanco.model.CuentaBancaria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;



public class CuentaBancariaDAO {


    public void crearCuentaBancaria(CuentaBancaria cuenta) {
        try {
            Connection conexion = ConexionSQLite.obtenerConexion();
            String query = "INSERT INTO Cuenta (CuentaID, Fecha_Creacion, Tipo_Cuenta, Saldo, Titular, Moneda) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, cuenta.getNumeroCuenta());
            statement.setString(2,cuenta.getFechaCreacion().toString());
            statement.setString(3, cuenta.getTipoCuenta().name());
            statement.setDouble(4, cuenta.getSaldo());
            statement.setLong(5,cuenta.getTitular());
            statement.setString(6, cuenta.getMoneda());
            statement.executeUpdate();
            statement.close();
            System.out.println("Cuenta bancaria creada con éxito.");
        } catch (SQLException e) {
           System.out.println("Error al crear la cuenta bancaria: " + e.getMessage());
        }
    }

    public void borrarCuenta(int identificador) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            conexion.setAutoCommit(false);
            String query = "DELETE FROM Cuenta WHERE CuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, identificador);
            conexion.commit();
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                conexion.commit();
                System.out.println("Cuenta borrada con éxito.");
            } else {
                System.out.println("No se encontró una cuenta con el numero especificado.");
                conexion.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Error al borrar la cuenta: " + e.getMessage());
        }
    }

    public void borrarTodasLasCuentas(int identificador){
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            conexion.setAutoCommit(false);
            String query = "DELETE FROM Cuenta WHERE Titular = ?";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, identificador);
            statement.executeUpdate();
            conexion.commit();
            
        } catch (SQLException e) {
            System.out.println("Error al borrar las cuenta: " + e.getMessage());
        }
    }

    public void modificarCuenta(int identificador, String atributo, String valor) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            conexion.setAutoCommit(false);
            String query = "UPDATE Cuenta SET " + atributo + " = ? WHERE CuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setString(1, valor);
            statement.setInt(2, identificador);
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                conexion.commit();
                System.out.println("Cuenta modificado con éxito.");
            } else {
                System.out.println("No se encontró un cuenta con el identificador especificado.");
                conexion.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el cuenta: " + e.getMessage());
        }
    }

    public CuentaBancaria obtenerCuentaPorId(int identificador) {
        CuentaBancaria cuenta = null;
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            conexion = ConexionSQLite.obtenerConexion();
            String query = "SELECT * FROM Cuenta WHERE CuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, identificador);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                cuenta = new CuentaBancaria();
                cuenta.setNumeroCuenta(resultSet.getInt("CuentaID"));
                cuenta.setFechaCreacion(LocalDate.parse(resultSet.getString("Fecha_Creacion")));
                cuenta.setTipoCuenta(CuentaBancaria.TipoCuenta.valueOf(resultSet.getString("Tipo_Cuenta")));
                cuenta.setSaldo(resultSet.getInt("Saldo"));
                cuenta.setTitular(resultSet.getInt("Titular"));
                cuenta.setMoneda(resultSet.getString("Moneda"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la cuenta: " + e.getMessage());
        }
        return cuenta;
    }
    

    public boolean numeroDeCuentaExiste(int numeroCuenta) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean existe = false;

        try {
            conexion = ConexionSQLite.obtenerConexion();
            String query = "SELECT 1 FROM Cuenta WHERE CuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, numeroCuenta);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                existe = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar el número de cuenta: " + e.getMessage());
        }
        return existe;
}

        public void aumentarSaldo(int identificador, double monto) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            conexion.setAutoCommit(false);
            String query = "UPDATE Cuenta SET Saldo = Saldo + ? WHERE CuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setDouble(1, monto);
            statement.setInt(2, identificador);
            statement.executeUpdate();
            conexion.commit();

            
        } catch (SQLException e) {
         System.out.println("Error al aumentar el saldo: " + e.getMessage());
        }
        }

        public void disminuirSaldo(int identificador, double monto) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try{
            conexion = ConexionSQLite.obtenerConexion();
            conexion.setAutoCommit(false);
            String query = "UPDATE Cuenta SER Saldo = Saldo - ? WHERE cuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setDouble(1, monto);
            statement.setInt(2, identificador);
            statement.executeUpdate();
            conexion.commit();
        }catch (SQLException e){
            System.out.println("Error al disminuir el saldo: " + e.getMessage());
        }
        }

        public void consultarSaldo(int identificador) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            String query = "SELECT Saldo FROM Cuenta WHERE CuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, identificador);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double saldo = resultSet.getDouble("Saldo");
                System.out.println("El saldo actual es de : $" + saldo);
            } else {
                System.out.println("No se encontró una cuenta con el identificador especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el saldo: " + e.getMessage());
        }
        }

        public void transferir(int cuentaOrigen, int cuentaDestino, double monto) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            conexion.setAutoCommit(false);
            String query = "UPDATE Cuenta SET Saldo = Saldo - ? WHERE CuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setDouble(1, monto);
            statement.setInt(2, cuentaOrigen);
            statement.executeUpdate();
            query = "UPDATE Cuenta SET Saldo = Saldo + ? WHERE CuentaID = ?";
            statement = conexion.prepareStatement(query);
            statement.setDouble(1, monto);
            statement.setInt(2, cuentaDestino);
            statement.executeUpdate();
            conexion.commit();
            System.out.println("Transferencia realizada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al realizar la transferencia: " + e.getMessage());
        }
        }
}
