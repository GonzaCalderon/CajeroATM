package Cajero;


import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class EscritorDeArchivos {

    BufferedWriter escritor;

    public void guardarArchivoDeCuentas(Map<String, Cuenta> archivoDeCuentas) throws IOException {
        escritor = new BufferedWriter(new FileWriter("ArchivoDeCuentas"));
        for (Cuenta unaCuenta :
                archivoDeCuentas.values()) {
            if(unaCuenta.getClass() == CAEnPesos.class){
                escritor.write("01,"+unaCuenta.getAlias()+","+ unaCuenta.getSaldo());
                escritor.newLine();
            }else if(unaCuenta.getClass() == CuentaCorriente.class){
                escritor.write("02,"+unaCuenta.alias+","+ unaCuenta.getSaldo()+","+((CuentaCorriente) unaCuenta).obtenerDescubierto());
                escritor.newLine();
            }else if(unaCuenta.getClass() == CAEnDolares.class){
                escritor.write("03,"+unaCuenta.alias+","+ unaCuenta.getSaldo());
                escritor.newLine();
            }
        }
        escritor.close();
    }

    public void guardarArchivoDeClientes(List<Cliente> archivoDeClientes) throws IOException {
        escritor = new BufferedWriter(new FileWriter("ArchivoDeClientes"));
        for (Cliente unCliente :
                archivoDeClientes) {
            for (String aliasDeCuenta : unCliente.getCuentasDelCliente()){
                escritor.write(unCliente.getCuit()+","+aliasDeCuenta);
                escritor.newLine();
            }
        }
        escritor.close();
    }
    public void guardarArchivoDeMovimientos(Map<String, Operacion> archivoDeMovimientos) throws IOException {
        escritor = new BufferedWriter(new FileWriter("ArchivoDeMovimientos"));
        for(Operacion unaOperacion : archivoDeMovimientos.values()){
            escritor.write(unaOperacion.getFecha() + "," + unaOperacion.getConcepto() + "," + unaOperacion.getCuenta().getAlias() + "," + unaOperacion.getMonto());
            escritor.newLine();
        }
        escritor.close();
    }

    public void guardarArchivoDeValidacionDeTarjetas(Map<String, Tarjeta> archivoDeValidacionDeTarjetas) throws IOException {
        escritor = new BufferedWriter(new FileWriter("ArchivoDeValidacionDeTarjetas"));
        for(Tarjeta unaTarjeta : archivoDeValidacionDeTarjetas.values()){
            escritor.write(unaTarjeta.obtenerNumeroDeTarjeta()+"," + unaTarjeta.obtenerPin()+"," + unaTarjeta.obtenerCuitAsociado());
            escritor.newLine();
        }
        escritor.close();
    }
}
