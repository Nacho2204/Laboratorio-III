package tubanco.conexion;


import tubanco.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> obtenerTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            Connection conexion = ConexionSQLite.obtenerConexion();
            String query = "SELECT * FROM Cliente";
            PreparedStatement statement = conexion.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Cliente cliente = new Cliente();
                cliente.setNombre(result.getString("Nombre"));
                cliente.setApellido(result.getString("Apellido"));
                cliente.setDni(result.getLong("DNI"));
                clientes.add(cliente);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public void crearCliente(Cliente cliente) {
        try {
            Connection conexion = ConexionSQLite.obtenerConexion();
            String query = "INSERT INTO Cliente (Nombre, Apellido, DNI, Banco, FechaNacimiento, FechaAlta, TipoPersona) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getApellido());
            statement.setLong(3, cliente.getDni());
            statement.setString(4, cliente.getBanco());
            statement.setDate(5, java.sql.Date.valueOf(cliente.getFechaNacimiento()));
            statement.setDate(6, java.sql.Date.valueOf(cliente.getFechaAlta()));
            statement.setString(7, cliente.getTipoPersona());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}
