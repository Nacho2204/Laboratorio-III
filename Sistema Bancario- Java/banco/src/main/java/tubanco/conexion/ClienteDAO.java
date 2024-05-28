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
            String query = "INSERT INTO Cliente (Nombre, Apellido, DNI, Banco, Fecha_de_nacimiento, Fecha_de_alta, Tipo_de_persona) "
                    +
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
            System.out.println("Error al crear el cliente: " + e.getMessage());
        }
    }

    public void borrarCliente(int identificador) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            conexion.setAutoCommit(false);
            String query = "DELETE FROM Cliente WHERE DNI = ?";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, identificador);
            statement.executeUpdate();
            conexion.commit();
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                conexion.commit();
                System.out.println("Cliente borrado con éxito.");
            } else {
                System.out.println("No se encontró un cliente con el identificador especificado.");
                conexion.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Error al borrar el cliente: " + e.getMessage());
        }
    }
    
    public void modificarCliente(int identificador, String atributo, String valor) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = ConexionSQLite.obtenerConexion();
            conexion.setAutoCommit(false);
            String query = "UPDATE Cliente SET " + atributo + " = ? WHERE DNI = ?";
            statement = conexion.prepareStatement(query);
            statement.setString(1, valor);
            statement.setInt(2, identificador);
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                conexion.commit();
                System.out.println("Cliente modificado con éxito.");
            } else {
                System.out.println("No se encontró un cliente con el identificador especificado.");
                conexion.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el cliente: " + e.getMessage());
        }
    }

}
