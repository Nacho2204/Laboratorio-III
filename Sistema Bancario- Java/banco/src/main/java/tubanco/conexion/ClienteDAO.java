package tubanco.conexion;

import tubanco.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

     public Cliente obtenerClientePorId(int identificador) {
        Cliente cliente = null;
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            conexion = ConexionSQLite.obtenerConexion();
            String query = "SELECT * FROM Cliente WHERE DNI = ?";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, identificador);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                cliente = new Cliente();
                cliente.setDni(resultSet.getInt("DNI"));
                cliente.setNombre(resultSet.getString("Nombre"));
                cliente.setApellido(resultSet.getString("Apellido"));
                cliente.setFechaNacimiento(LocalDate.parse(resultSet.getString("Fecha_de_nacimiento")));
                cliente.setFechaAlta(LocalDate.parse(resultSet.getString("Fecha_de_alta")));
                cliente.setBanco(resultSet.getString("Banco"));
                cliente.setTipoPersona(resultSet.getString("Tipo_de_persona"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el cliente: " + e.getMessage());
        } 
        return cliente;
    }

        public void mostrarCliente(int identificador){
            Connection conexion = null;
            PreparedStatement statement = null;

            try{
                conexion = ConexionSQLite.obtenerConexion();
                conexion.setAutoCommit(false);
                String query = "SELECT * FROM Cliente WHERE DNI = ?";
                statement = conexion.prepareStatement(query);
                statement.setInt(1, identificador);
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()){
                    System.out.println("--------------------");
                    System.out.println("Nombre: " + resultSet.getString("Nombre"));
                    System.out.println("Apellido: " + resultSet.getString("Apellido"));
                    System.out.println("DNI: " + resultSet.getInt("DNI"));
                    System.out.println("Banco: " + resultSet.getString("Banco"));
                    System.out.println("Fecha de nacimiento: " + resultSet.getString("Fecha_de_nacimiento"));
                    System.out.println("Fecha de alta: " + resultSet.getString("Fecha_de_alta"));
                    System.out.println("Tipo de persona: " + resultSet.getString("Tipo_de_persona"));
                    System.out.println("--------------------\n");
                }
            }catch(SQLException e){
                System.out.println("Error al mostrar el cliente: " + e.getMessage());
            }
        }
     

}
