package tubanco.presentation.inputs;

import java.util.Scanner;

import tubanco.conexion.ClienteDAO;
import tubanco.conexion.CuentaBancariaDAO;
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

        int opcionTipoPersona;
            do{
                        System.out.println("Ingrese el nuevo tipo de persona del cliente: ");
                        System.out.println("1. Juridica");
                        System.out.println("2. Fisica");
                        System.out.println("-------------------------");
                        opcionTipoPersona = scanner.nextInt();
                        switch (opcionTipoPersona) {
                            case 1:
                            cliente.setTipoPersona("JURIDICA");
                                break;
                            
                            case 2:
                            cliente.setTipoPersona("FISICA");                                

                            default:
                                System.out.println("Valor no válido.");
                                break;
                        }
            }
            while(opcionTipoPersona != 1 && opcionTipoPersona != 2);
                        

        System.out.println("Cliente creado exitosamente.\n");

        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.crearCliente(cliente);

        return cliente;
    }

    public void eliminarCliente() {
        int opcion;
      do{
        System.out.println("\nSi borra el cliente todas sus cuentas se eliminaran!");
        System.out.println("Si desea eliminarlo seleccione 1.");
        System.out.println("Si no quiere eliminarlo seleccione 2.");
        opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {
            case 1:
            ClienteDAO clienteDAO = new ClienteDAO();
            CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAO();
            System.out.println("Ingrese el identificador del cliente que desea eliminar: ");
            int identificador = scanner.nextInt();
            scanner.nextLine();
            clienteDAO.borrarCliente(identificador);
            cuentaDAO.borrarTodasLasCuentas(identificador);
            break;
            
            case 2:
                break;
            default:
            System.out.println("ERROR, VOLVER A INTENTAR NUEVAMENTE");
                break;
        }
      }while(opcion != 2);
      
    }

    public void modificarCliente(int identificador) {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente clienteAModificar = clienteDAO.obtenerClientePorId(identificador);
        if (clienteDAO.obtenerClientePorId(identificador) == null) {
            System.out.println("No existe ningún cliente con el identificador ingresado.");
            return;
            
        }else{
            int opcion;
            do{
            System.out.println("Ingrese el atributo que desea modificar: ");
            System.out.println("0. Salir");
            System.out.println("1. Nombre");
            System.out.println("2. Apellido");
            System.out.println("3. Fecha de nacimiento");
            System.out.println("4. Banco");
            System.out.println("5. Tipo de persona");
            System.out.println("-------------------------");
            opcion = scanner.nextInt();

                switch (opcion) {

                    case 1:
                        System.out.println("Ingrese el nuevo nombre del cliente: ");
                        String nuevoNombre = scanner.nextLine();
                        clienteDAO.modificarCliente(identificador, "Nombre", nuevoNombre);
                        break;

                    case 2:
                        System.out.println("Ingrese el nuevo apellido del cliente: ");
                        String nuevoApellido = scanner.nextLine();
                        clienteDAO.modificarCliente(identificador, "Apellido", nuevoApellido);
                        break;

                    case 3:
                    LocalDate fechaNacimiento = null;
                    boolean fechaValida = false;
                    while (!fechaValida) {
                        System.out.println("Ingrese la nueva fecha de nacimiento del cliente (Formato: YYYY-MM-DD): ");
                        String fechaStr = scanner.nextLine();
                        if (validarFormatoFecha(fechaStr)) {
                            LocalDate fechaIngresada = LocalDate.parse(fechaStr);
                            if (!fechaIngresada.equals(clienteAModificar.getFechaNacimiento())) { 
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

                    case 4:
                        System.out.println("Ingrese el nuevo banco del cliente: ");
                        String nuevoBanco = scanner.nextLine();
                        clienteDAO.modificarCliente(identificador, "Banco", nuevoBanco);
                        break;
                    
                    case 5:
                        int opcionTipoPersona;
                        do{
                        System.out.println("Ingrese el nuevo tipo de persona del cliente: ");
                        System.out.println("1. Juridica");
                        System.out.println("2. Fisica");
                        System.out.println("-------------------------");
                        opcionTipoPersona = scanner.nextInt();
                        switch (opcionTipoPersona) {
                            case 1:
                                clienteDAO.modificarCliente(identificador, "Tipo_de_persona", "JURIDICA");
                                break;
                            
                            case 2:
                            clienteDAO.modificarCliente(identificador, "Tipo_de_persona", "FISICA");
                                break;

                            default:
                                System.out.println("Valor no válido.");
                                break;
                        }
                        }while(opcionTipoPersona != 1 || opcionTipoPersona != 2);
                        break;

                    default:
                        System.out.println("Atributo no válido.");
                        break;
                }
            }while(opcion != 0);
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
