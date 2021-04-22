package Cajero;

public abstract class  Cuenta{

    protected String alias;
    protected static final String tipo = "Cuenta";
    protected double saldo;
    public Cuenta(String alias, double saldo) {
        this.alias = alias;
        this.saldo = saldo;
    }
    public String getAlias(){
        return alias;
    }

    public abstract void ingresarDinero(double monto);

    public abstract void retirarDinero(double monto);

    public abstract String obtenerTipo();

    public abstract double getSaldo();

    public abstract double obtenerSaldoMinimo();
}
