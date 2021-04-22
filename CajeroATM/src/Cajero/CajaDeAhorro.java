package Cajero;

public abstract class CajaDeAhorro extends Cuenta{

    public CajaDeAhorro(String alias, double saldo) {
        super(alias,saldo);
    }

    public abstract double getSaldo();

}



