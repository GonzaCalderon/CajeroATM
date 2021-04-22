package Cajero;


public class OperacionDeDivisas extends Operacion {


    public OperacionDeDivisas(String fecha, Cuenta unaCuenta, String concepto, double monto) {
        super(fecha, unaCuenta, concepto,monto);
    }
    public void retirarEfectivo() throws Exception {
        if (unaCuenta.getSaldo() - monto >= unaCuenta.obtenerSaldoMinimo()) {
            unaCuenta.retirarDinero(monto);
        }else{
            throw new DineroInsuficienteException();
        }

    }
    public void comprarDolares(Cuenta cuentaEnDolares, double valorDolarHoy) throws Exception {
        try{
            double montoEnPesos = monto * (valorDolarHoy + (valorDolarHoy* 0.3));
            if(unaCuenta.obtenerSaldoMinimo() <= unaCuenta.getSaldo() - montoEnPesos) {
                unaCuenta.retirarDinero(montoEnPesos);
                cuentaEnDolares.ingresarDinero(monto);
            }else{
                throw new MontoIlegalException("No posee suficiente dinero en la cuenta para comprar esta cantidad de dolares");
            }
        }catch (NullPointerException e){
            throw new CAEnDolaresInexistenteException();
        }

    }
    public void depositarPesos(int billetesDe100,int billetesDe500, int billetesDe1000) throws Exception{
        monto = (billetesDe100*100) + (billetesDe500*500) + (billetesDe1000*1000);
        unaCuenta.ingresarDinero(monto);
    }

    public void depositarDolares() {
        unaCuenta.ingresarDinero(monto);
    }



    @Override
    public String toString() {
        return fecha+","+concepto+","+unaCuenta.getAlias()+","+monto;
    }
}