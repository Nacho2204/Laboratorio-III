package tubanco.presentation.inputs;

import java.util.Scanner;

import tubanco.conexion.ClienteDAO;
import tubanco.model.Cliente; 
import java.time.LocalDate;

public class ClienteInput {
    protected static Scanner scanner = new Scanner(System.in);

    public ClienteInput(Scanner scanner) {
    }
    

    public Cliente ingresarCliente() {
        Cliente cliente = new Cliente(); 

        System.out.println("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();
        cliente.setNombre(nombre);

        System.out.println("Ingrese el apellido del cliente: ");
        String apellido = scanner.nextLine();
        cliente.setApellido(apellido);

        System.out.println("Ingrese el DNI del cliente: ");
        long dni = 0;

        while (true) {
            if (scanner.hasNextLong()) {
                dni = scanner.nextLong();
                if (String.valueOf(dni).length() == 8) {
                    break;
                } else {
                    System.out.println("El DNI debe ser de 8 cifras.");
                }
            } else {
                System.out.println("Por favor, ingrese un número válido para el DNI.");
                scanner.next(); 
            }
        }

        cliente.setDni(dni);
        scanner.nextLine(); 

        System.out.println("Ingrese el banco del cliente: ");
        String banco = scanner.nextLine();
        cliente.setBanco(banco);

        System.out.println("Su identificador es: " + cliente.getDni());
        cliente.setIdentificador(cliente.getDni());

        System.out.println("Ingrese la fecha de nacimiento del cliente (Formato: YYYY-MM-DD): ");
        LocalDate fechaNacimiento = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            try {
                fechaNacimiento = LocalDate.parse(scanner.nextLine());
                LocalDate fechaHoy = LocalDate.now();
                LocalDate fechaMayorEdad = fechaHoy.minusYears(18);
                if (fechaNacimiento.isBefore(fechaMayorEdad)) {
                    fechaValida = true;
                } else {
                    System.out.println("El cliente debe tener al menos 18 años de edad.");
                }
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido. Ingrese la fecha en formato YYYY-MM-DD:");
            }
        }
        cliente.setFechaNacimiento(fechaNacimiento);

        cliente.setFechaAlta(LocalDate.now());
        System.out.println("Su fecha de alta es: " + cliente.getFechaAlta());

        System.out.println("Ingrese el tipo de persona del cliente (Juridica - Fisica): ");
        String tipoPersona = scanner.nextLine();
        cliente.setTipoPersona(tipoPersona);

        System.out.println("Cliente creado exitosamente.\n");

        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.crearCliente(cliente);

        return cliente;
    }

    public void eliminarCliente() {
      ClienteDAO clienteDAO = new ClienteDAO();
      System.out.println("Ingrese el identificador del cliente que desea eliminar: ");
      int identificador = scanner.nextInt();
      scanner.nextLine();
      clienteDAO.borrarCliente(identificador);
    }

    public void modificarCliente(int identificador, String atributo) {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente clienteAModificar = clienteDAO.obtenerClientePorId(identificador);
       

                switch (atributo.toLowerCase()) {

                    case "nombre":
                        System.out.println("Ingrese el nuevo nombre del cliente: ");
                        String nuevoNombre = scanner.nextLine();
                        clienteDAO.modificarCliente(identificador, "Nombre", nuevoNombre);
                        break;

                    case "apellido":
                        System.out.println("Ingrese el nuevo apellido del cliente: ");
                        String nuevoApellido = scanner.nextLine();
                        clienteDAO.modificarCliente(identificador, "Apellido", nuevoApellido);
                        break;

                    case "fechanacimiento":
                    LocalDate fechaNacimiento = null;
                    boolean fechaValida = false;
                    while (!fechaValida) {
                        System.out.println("Ingrese la nueva fecha de nacimiento del cliente (Formato: YYYY-MM-DD): ");
                        String fechaStr = scanner.nextLine();
                        if (validarFormatoFecha(fechaStr)) {
                            LocalDate fechaIngresada = LocalDate.parse(fechaStr);
                            if (!fechaIngresada.equals(clienteAModificar.getFechaNacimiento())) { // Verifica si la nueva fecha es diferente a la fecha actual
                                fechaNacimiento = fechaIngresada;
                                fechaValida = true;
                            } else {
                                System.out.println("La nueva fecha de nacimiento debe ser diferente a la fecha actual del cliente.");
                            }
                        } else {
                            System.out.println("Formato de fecha inválido. Ingrese la fecha en formato YYYY-MM-DD:");
                        }
                    }
                    clienteDAO.modificarCliente(identificador, "Fecha_de_nacimiento", fechaNacimiento.toString());

                    break;
                    case "fechaalta":
                    LocalDate fechaAlta = null;
                    boolean fechaValida2 = false;
                    while (!fechaValida2) {
                        System.out.println("Ingrese la nueva fecha de alta del cliente (Formato: YYYY-MM-DD): ");
                        String fechaStr = scanner.nextLine();
                        if (validarFormatoFecha(fechaStr)) {
                            LocalDate fechaIngresada = LocalDate.parse(fechaStr);
                            if (!fechaIngresada.equals(clienteAModificar.getFechaAlta())) { // Verifica si la nueva fecha es diferente a la fecha actual
                                fechaAlta = fechaIngresada;
                                fechaValida = true;
                            } else {
                                System.out.println("La nueva fecha de alta debe ser diferente a la fecha actual del cliente.");
                            }
                        } else {
                            System.out.println("Formato de fecha inválido. Ingrese la fecha en formato YYYY-MM-DD:");
                        }
                    }
                    clienteDAO.modificarCliente(identificador, "Fecha_de_alta", fechaAlta.toString());
                    break;

                    case "banco":
                        System.out.println("Ingrese el nuevo banco del cliente: ");
                        String nuevoBanco = scanner.nextLine();
                        clienteDAO.modificarCliente(identificador, "Banco", nuevoBanco);
                        break;
                    
                    case "tipoPersona":
                        System.out.println("Ingrese el nuevo tipo de persona del cliente: ");
                        String nuevoTipoPersona = scanner.nextLine();
                        clienteDAO.modificarCliente(identificador, "Tipo_de_persona", nuevoTipoPersona);
                        break;

                    default:
                        System.out.println("Atributo no válido.");
                        break;
                }
            }
        
    
    
    public void mostrarCliente(int identificador) {
        
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.mostrarCliente(identificador);
    }

    private boolean validarFormatoFecha(String fechaStr) {
        String regex = "^\\d{4}-\\d{2}-\\d{2}$";
        return fechaStr.matches(regex);
    }


    public static Scanner getScanner() {
        return scanner;
    }


    public static void setScanner(Scanner scanner) {
        ClienteInput.scanner = scanner;
    }


    
    
    
 }
