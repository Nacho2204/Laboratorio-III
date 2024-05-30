package tubanco.model;

import java.time.LocalDate;


public class CuentaBancaria {

    public enum TipoCuenta {
        AHORRO,
        CORRIENTE
    }
    protected Cliente cliente;
    protected String nombre;
    protected LocalDate fechaCreacion;
    protected TipoCuenta tipoCuenta;
    protected int saldo;
    protected int numeroCuenta;
    protected String moneda;
    private long titular;



    public String getNombre() {
        return nombre;
    }

    public CuentaBancaria setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public CuentaBancaria setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public int getSaldo() {
        return saldo;
    }

    public CuentaBancaria setSaldo(int saldo) {
        this.saldo = saldo;
        return this;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }


    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public long getTitular() {
        return titular;
    }

    public void setTitular(long titular) {
        this.titular = titular;
    }
}