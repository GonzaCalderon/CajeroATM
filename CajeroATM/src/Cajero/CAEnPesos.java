package Cajero;

class CAEnPesos extends CajaDeAhorro{

    private static final String tipo = "Caja de ahorro en pesos";

    public CAEnPesos(String alias, double saldoEnPesos) {
        super(alias, saldoEnPesos);
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public double obtenerSaldoMinimo() {
        return 0;
    }

    @Override
    public void ingresarDinero(double monto) {
        saldo += monto;
    }

    @Override
    public void retirarDinero(double monto) {
        saldo -= monto;
    }

    public String obtenerTipo(){
        return tipo;
    }
}
