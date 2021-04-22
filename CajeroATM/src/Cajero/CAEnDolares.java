package Cajero;

class CAEnDolares extends CajaDeAhorro{


    private static final String tipo = "Caja de ahorro en dolares";
    public CAEnDolares(String alias, double saldoEnDolares){
        super(alias, saldoEnDolares);
    }

    public double getSaldo(){
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
