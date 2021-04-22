package Cajero;

import java.util.ArrayList;
import java.util.List;

public class Dispensador {

    private List<Billete> billetes;
    private String billetesEntregados;
    public Dispensador(){
        billetes= new ArrayList<>();
        billetes.add(new Billete(1000,500));
        billetes.add(new Billete(500, 500));
        billetes.add(new Billete(100, 500));
    }

    public String retirarEfectivo(int monto) throws Exception {

       validarMonto(monto);
       String dispensador = "";
       dispensador += "Se entregan " + monto + " en: ";
       for (Billete unBillete : billetes) {
           int cantidadDispensada;
           if(monto != 0 && !hayBilletesSuficientes(unBillete, monto)){
               cantidadDispensada =  unBillete.restarCantidad(unBillete.obtenerCantidad());
           }else{
               cantidadDispensada = unBillete.restarCantidad(monto / unBillete.obtenerValor());
           }
           monto -= cantidadDispensada * unBillete.obtenerValor();
           dispensador += entrega(unBillete.obtenerValor(), cantidadDispensada);
       }
        billetesEntregados = dispensador;
        return dispensador;
    }
    public String billetesEntregados(){
        String mensaje = billetesEntregados;
        billetesEntregados = "";
        return mensaje;
    }
    private String entrega(int billete, int monto) {

        String cadena = "";
        int cantidad = 1;

        for (int i = 0; i < monto; i++) {
            if(cantidad == 1) {
                cadena = cantidad + " billete de " + billete + " -";
            } else {
                cadena = cantidad + " billetes de " + billete + " -";
            }
            cantidad ++;
        }
        return cadena;

    }

    private boolean hayBilletesSuficientes(Billete billete, int monto){
        return monto / billete.obtenerValor() <= billete.obtenerCantidad();
    }


    private void validarMonto(int monto) throws Exception {
        int dineroDisponible = 0;
        for(Billete unBillete : billetes){
            dineroDisponible += unBillete.obtenerValor() * unBillete.obtenerCantidad();
        }
        if(dineroDisponible < monto){
            throw new MontoIlegalException("Este cajero no dispone de suficiente dinero");
        }else if(monto % 100 != 0){
            throw new MontoIlegalException("Este cajero no puede dispensar el monto electo con los billetes disponibles, vuelva a intentar con un monto divisible por 100");
        }else if(monto < 0){
            throw new MontoIlegalException("El monto no puede ser negativo");
        }
    }

}
