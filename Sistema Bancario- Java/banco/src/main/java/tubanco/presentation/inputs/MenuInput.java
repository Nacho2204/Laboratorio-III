package tubanco.presentation.inputs;
import java.util.Scanner;



public class MenuInput {
    protected Scanner scanner;
    protected ClienteInput clienteInput;
    protected CuentaBancariaInput cuentaBancariaInput;

    public MenuInput() {
        this.scanner = new Scanner(System.in);
        this.clienteInput = new ClienteInput(scanner);
        this.cuentaBancariaInput = new CuentaBancariaInput(scanner);
    }

    public void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n--------------------------------------");
            System.out.println("0. Salir");
            System.out.println("1. Crear cliente");
            System.out.println("2. Modificar cliente");
            System.out.println("3. Mostrar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Crear cuenta bancaria");
            System.out.println("6. Eliminar cuenta bancaria.");
            System.out.println("7. Modificar cuenta bancaria");
            System.out.println("8. Realizar operaciones.");
            System.out.println("9. Consultar movimientos.");
            System.out.println("--------------------------------------");
            
            System.out.print("Elige una opcion: ");
            opcion = this.scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 0:
                    System.out.println("Saliendo del programa");
                    break;

                case 1:
                    clienteInput.ingresarCliente();
                    break;

                case 2:
                    System.out.println("Ingrese el identificador del cliente que desea modificar: ");
                    int identificadorClienteModificar = scanner.nextInt();
                    scanner.nextLine();
            
            
                    clienteInput.modificarCliente(identificadorClienteModificar);
                    break;

                case 3:
                    System.out.println("Ingrese el identificador del cliente que desea mostrar: ");
                    int identificadorClienteMostrar = scanner.nextInt();
                    clienteInput.mostrarCliente(identificadorClienteMostrar);
                    break;

                case 4:
                    clienteInput.eliminarCliente();
                    break;

                 case 5:
                    System.out.println("Ingrese el identificador del cliente para asociar la cuenta bancaria: ");
                    int identificadorClienteCrearCuenta = scanner.nextInt();
                    scanner.nextLine();
                    cuentaBancariaInput.crearCuentaBancaria(identificadorClienteCrearCuenta);
                    break;

                case 6:
                    cuentaBancariaInput.eliminarCuenta();
                    break;

                case 7:
                    System.out.println("Ingrese el número de la cuenta que desea modificar: ");
                    int numeroCuentaModificar = scanner.nextInt();
                    cuentaBancariaInput.modificarCuenta(numeroCuentaModificar);
                    break;

                case 8:
                    System.out.println("Ingrese el número de cuenta para realizar operaciones: ");
                    int numeroCuentaOperaciones = scanner.nextInt();
                    cuentaBancariaInput.realizarOperaciones(numeroCuentaOperaciones);
                    break;

                case 9:
                    System.out.println("Ingrese el número de cuenta para consultar movimientos: ");
                    int numeroCuentaConsultar = scanner.nextInt();
                    cuentaBancariaInput.mostrarMovimientos(numeroCuentaConsultar);
                    break;

                default:
                    System.out.println("ERROR, VOLVER A INTENTAR NUEVAMENTE");
                    break;
            
            }

        } while (opcion != 0);
    }


    
    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public ClienteInput getClienteInput() {
        return clienteInput;
    }

    public void setClienteInput(ClienteInput clienteInput) {
        this.clienteInput = clienteInput;
    }

    public CuentaBancariaInput getCuentaBancariaInput() {
        return cuentaBancariaInput;
    }

    public void setCuentaBancariaInput(CuentaBancariaInput cuentaBancariaInput) {
        this.cuentaBancariaInput = cuentaBancariaInput;
    }

    
}