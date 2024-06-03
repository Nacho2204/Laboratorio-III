package tubanco.presentation.inputs;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;


import tubanco.conexion.ClienteDAO;
import tubanco.conexion.CuentaBancariaDAO;
import tubanco.conexion.MovimientosDAO;
import tubanco.model.*;


public class CuentaBancariaInput {
    private Scanner scanner;

    public CuentaBancariaInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public CuentaBancaria crearCuentaBancaria(int identificador) {
        CuentaBancaria cuenta = new CuentaBancaria();
        Random random = new Random();
        CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();

        if (clienteDAO.obtenerClientePorId(identificador) == null) {
            System.out.println("No existe ningún cliente con el identificador ingresado, por lo tanto no podemos crear una cuenta.");
            return null;
            
        }
        else{

        int numeroCuenta;
        boolean numeroUnico;

        do {
            numeroCuenta = 100000 + random.nextInt(900000); // Genera un número aleatorio entre 100,000 y 1,000,000
            numeroUnico = !cuentaDAO.numeroDeCuentaExiste(numeroCuenta); // Verifica que el número de cuenta no exista
        } while (!numeroUnico);
    
        System.out.println("Su número de cuenta asignado es: " + numeroCuenta);
        cuenta.setNumeroCuenta(numeroCuenta);

        int tipoCuenta;
        do{
        System.out.println("Ingrese el tipo de cuenta (AHORRO o CORRIENTE):");
        System.out.println("1. Ahorro");
        System.out.println("2. Corriente");
        System.out.println("-------------------------");
         tipoCuenta = scanner.nextInt();
         scanner.nextLine();

        switch (tipoCuenta) {
            case 1:
                cuenta.setTipoCuenta(CuentaBancaria.TipoCuenta.valueOf("AHORRO"));
                break;
        
            case 2:
                cuenta.setTipoCuenta(CuentaBancaria.TipoCuenta.valueOf("CORRIENTE"));
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }while(tipoCuenta != 1 && tipoCuenta != 2);
        


    System.out.println("\nEl balance de la cuenta comenzará siendo 0. Si necesita ingresar dinero deposite dinero en su cuenta");
    cuenta.setSaldo(0);

        
        cuenta.setFechaCreacion(LocalDate.now());
        System.out.println("\nSu fecha de alta es: " + cuenta.getFechaCreacion());

        System.out.println("Ingrese la moneda que va a utilizar: ");
        String moneda = scanner.nextLine();
        cuenta.setMoneda(moneda);
        
        cuenta.setTitular(identificador);
        

        cuentaDAO.crearCuentaBancaria(cuenta);
        return cuenta;
        
    }
}


    public void eliminarCuenta() {
        CuentaBancariaDAO cuenta = new CuentaBancariaDAO();
        System.out.println("Ingrese el numero de la cuenta que desea eliminar: ");
        int identificador = scanner.nextInt();
        scanner.nextLine();
        cuenta.borrarCuenta(identificador);
      }

    public void realizarOperaciones(int numeroCuenta) {

        CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAO();
        MovimientosDAO movimientosDAO = new MovimientosDAO();
        if (cuentaDAO.obtenerCuentaPorId(numeroCuenta) == null) {
            System.out.println("No existe ninguna cuenta con el identificador ingresado.");
            return;
            
        }
        else{
        
        
        System.out.println("Seleccione la operación que desea realizar:");
        System.out.println("0. Salir");
        System.out.println("1. Depósito");
        System.out.println("2. Retiro");
        System.out.println("3. Consulta de saldo");
        System.out.println("4. Tranferencia");
        System.out.println("-------------------------");
        int opcion = scanner.nextInt();

        
        switch (opcion) {
            case 0:
                System.out.println("Salio del menu de operaciones.");
                break;

            case 1:
                System.out.println("Ingrese el monto a depositar:");
                double montoDeposito = scanner.nextDouble();
                if (montoDeposito<=0 || montoDeposito >= 1000000) {
                    System.out.println("El monto a depositar debe ser mayor a $0 y el limite a depositar es de $1000000.");
                }
                else{
                cuentaDAO.aumentarSaldo(numeroCuenta, montoDeposito);
                movimientosDAO.generarDeposito(numeroCuenta, montoDeposito);
                }
                break;

            case 2:
                System.out.println("Ingrese el monto a retirar:");
                double montoRetiro = scanner.nextDouble();
                boolean exitoso = cuentaDAO.disminuirSaldo(numeroCuenta, montoRetiro);

                if (montoRetiro<=0) {
                    System.out.println("El monto a retirar debe ser mayor a $0.");
                }
                else if (exitoso) {
                    cuentaDAO.disminuirSaldo(numeroCuenta, montoRetiro);
                    movimientosDAO.generarRetiro(numeroCuenta, montoRetiro);
                }
                else{
                    System.out.println("No se pudo realizar el retiro.");
                }
                break;

            case 3:
                cuentaDAO.consultarSaldo(numeroCuenta);
                break;

            case 4:
                System.out.println("Ingrese el número de cuenta a la que desea transferir:");
                int numeroCuentaDestino = scanner.nextInt();
                double montoTransferencia;

                boolean exitosoTransferencia = cuentaDAO.disminuirSaldo(numeroCuenta, 0);
                if (cuentaDAO.obtenerCuentaPorId(numeroCuentaDestino) == null) {
                    System.out.println("No se encontro ninguna cuenta con el numero que ingreso.");
                    break;
                }
                else {
                    System.out.println("Ingrese el monto a transferir:");
                    montoTransferencia = scanner.nextDouble();
                }

                if (montoTransferencia<=0 || montoTransferencia >= 1000000) {
                    System.out.println("El monto a transferir debe ser mayor a $0 y tiene un limite de $1000000.");
                }
                else if (exitosoTransferencia) {
                    cuentaDAO.transferir(numeroCuenta, numeroCuentaDestino, montoTransferencia);
                    movimientosDAO.generarTransferencia(numeroCuenta, numeroCuentaDestino, montoTransferencia);
                }
                else{
                    System.out.println("No se pudo realizar la transferencia.");
                }
                break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
        
    }
    

    public void modificarCuenta(int identificador) {

        
        CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();

        if (cuentaDAO.obtenerCuentaPorId(identificador) == null) {
            System.out.println("No existe ninguna cuenta con el identificador ingresado.");
            return;
            
        }else{
            scanner.nextLine();
            int opcion;
            do{
            System.out.println("Ingrese el atributo que desea modificar: ");
            System.out.println("0. Salir");
            System.out.println("1. Titular");
            System.out.println("2. Moneda");
            System.out.println("3. TipoCuenta");
            System.out.println("-------------------------");
            opcion = scanner.nextInt();

                switch (opcion) {
                    
                    case 2:
                        System.out.println("Ingrese la nueva moneda que quiere que maneje su cuenta: ");
                        String nuevaMoneda = scanner.nextLine();
                        cuentaDAO.modificarCuenta(identificador, "Moneda", nuevaMoneda);
                        break;
                    
                    case 1:
                        System.out.println("Ingrese el nuevo DNI del titular de la cuenta: ");
                        int nuevoDniTitular = scanner.nextInt();
    
                    
                        if (clienteDAO.obtenerClientePorId(nuevoDniTitular) != null) {
                        cuentaDAO.modificarCuenta(identificador, "Titular", String.valueOf(nuevoDniTitular));
                        System.out.println("Titular modificado con éxito.");
                        } else {
                        System.out.println("No existe ningún cliente con el DNI ingresado. No se pudo cambiar el titular de la cuenta.");
                        }
                        break;
                    
                    case 3:
                        int tipoCuenta;
                        do{
                        System.out.println("Ingrese el nuevo tipo de cuenta:");
                        System.out.println("1. Ahorro");
                        System.out.println("2. Corriente");
                        System.out.println("-------------------------");
                         tipoCuenta = scanner.nextInt();
            
                        switch (tipoCuenta) {
                            case 1:
                                cuentaDAO.modificarCuenta(identificador, "Tipo_Cuenta", "AHORRO");                                
                                break;
                    
                            case 2:
                                cuentaDAO.modificarCuenta(identificador, "Tipo_Cuenta", "CORRIENTE");
                                break;
                            default:
                                System.out.println("Opción no válida.");
                                break;
                        }
                        }while(tipoCuenta != 1 && tipoCuenta != 2);
                        
                        break;

                    default:
                        System.out.println("Atributo no válido.");
                        break;
                    }
                }while(opcion != 0);
            }
    }

    public void mostrarMovimientos(int numeroCuenta) {
        CuentaBancariaDAO cuentaDAO = new CuentaBancariaDAO();
        MovimientosDAO movimientosDAO = new MovimientosDAO();
        if (cuentaDAO.obtenerCuentaPorId(numeroCuenta) == null) {
            System.out.println("No existe ninguna cuenta con el identificador ingresado.");
            return;
            
        }
        else{
        movimientosDAO.mostrarMovimientos(numeroCuenta);
     }    
    }
}


