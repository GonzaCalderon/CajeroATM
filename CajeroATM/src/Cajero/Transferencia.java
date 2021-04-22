package Cajero;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Transferencia extends Operacion implements Reversible {

    private Cuenta cuentaDestino;
    private String fecha;

    public Transferencia(String fecha, Cuenta cuentaEmisora,Cuenta cuentaDestino,  double monto) throws DineroInsuficienteException {
        super(fecha, cuentaEmisora,"Transferencia",monto);
        if( cuentaEmisora.getSaldo() - monto < cuentaEmisora.obtenerSaldoMinimo()){
            throw new DineroInsuficienteException("No alcanza el dinero en cuenta para hacer la transferencia");
        }else{
            this.fecha = fecha;
            this.cuentaDestino = cuentaDestino;
        }

    }

    private void realizarTransferencia() throws Exception {
        if (cuentaDestino != null ) {
            unaCuenta.retirarDinero(monto);
            cuentaDestino.ingresarDinero(monto);
        } else {
            throw new Exception("La cuenta de destino no esta en el sistema");
        }

    }

    public void transferir() throws Exception {
        realizarTransferencia();
    }

    @Override
    public void revertirOperacion() {

        unaCuenta.ingresarDinero(monto);
        cuentaDestino.retirarDinero(monto);
    }

//    public void finalizarOperacion(){
//        sesionDeOperacion.guardarOperacion(archivoDeOperacion);
//    }

//    public String getCuentaDestino() {
//        return cuentaDestino;
//    }
//
//    public String getCuentaEmisora() {
//        return unaCuenta;
//    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public String getFecha() {
        return fecha;
    }
    @Override
    public String toString() {
        return fecha+","+concepto+","+unaCuenta.getAlias()+","+monto;
    }
}