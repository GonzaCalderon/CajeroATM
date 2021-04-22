package Cajero;

public class CuentaCorriente extends Cuenta{

    private double descubierto;
    private static final String tipo = "Cuenta corriente";

    public CuentaCorriente(String alias, double saldoEnPesos, double descubierto) throws Exception {
        super(alias, saldoEnPesos);
            this.descubierto = descubierto;
    }
    @Override
    public void ingresarDinero(double monto) {
        saldo += monto;
    }
    @Override
    public void retirarDinero(double monto) {
        saldo -= monto;
    }

    public double getSaldo(){
        return saldo;
    }

    public double obtenerDescubierto(){
        return descubierto;
    }

    public String obtenerTipo(){
        return tipo;
    }

    public double obtenerSaldoMinimo(){

        return -descubierto;
    }
}
