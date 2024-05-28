package tubanco.conexion;

import tubanco.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {

    public void crearCliente(Cliente cliente) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            conexion.setAutoCommit(false);
            String query = "INSERT INTO Cliente (Nombre, Apellido, DNI, Banco, Fecha_de_nacimiento, Fecha_de_alta, Tipo_de_persona) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(query);
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellido());
            statement.setLong(3, cliente.getDni());
            statement.setString(4, cliente.getBanco());
            statement.setString(5, cliente.getFechaNacimiento().toString());
            statement.setString(6, cliente.getFechaAlta().toString());
            statement.setString(7, cliente.getTipoPersona());
            statement.executeUpdate();
            conexion.commit(); 
        } catch (SQLException e) {
            e.printStackTrace();
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
