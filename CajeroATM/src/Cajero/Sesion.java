package Cajero;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sesion {

    private LectorDeArchivos lector = new LectorDeArchivos();
    private EscritorDeArchivos escritor = new EscritorDeArchivos();
    private Cliente clienteEnSesion;
    private Map<String, List<Cliente>> archivoDeClientes;
    private Map<String, Cuenta> archivoDeCuentas;
    private Map<String, Operacion> archivoDeMovimientos;
    private Map<String, Tarjeta> archivoDeValidacionDeTarjetas;

    public Sesion() throws Exception {


        archivoDeCuentas = lector.obtenerArchivoDeCuentas();
        archivoDeClientes = lector.obtenerArchivoDeClientes();
        archivoDeMovimientos = lector.obtenerArchivoDeMovimientos();
        archivoDeValidacionDeTarjetas = lector.obtenerArchivoDeValidacionDeTarjetas();

    }

    public void cerrarSesion() throws IOException {
        List<Cliente> subArchivoDeClientes = new ArrayList<>();
        for (List<Cliente> listaDeClientes :
                archivoDeClientes.values()) {
            subArchivoDeClientes.addAll(listaDeClientes);
        }
        escritor.guardarArchivoDeCuentas(archivoDeCuentas);
        escritor.guardarArchivoDeClientes(subArchivoDeClientes);
        escritor.guardarArchivoDeValidacionDeTarjetas(archivoDeValidacionDeTarjetas);
        escritor.guardarArchivoDeMovimientos(archivoDeMovimientos);
    }

    public Map<String, Operacion> getArchivoDeMovimientos() {
        return archivoDeMovimientos;
    }

    public Map<String, Tarjeta> getArchivoDeValidacionDeTarjetas() {
        return archivoDeValidacionDeTarjetas;
    }

    public Map<String, List<Cliente>> getArchivoDeClientes() {
        return archivoDeClientes;
    }

    public Map<String, Cuenta> getArchivoDeCuentas() {
        return archivoDeCuentas;
    }

    public List<String> obtenerCuentasDelCliente(String cuit) throws Exception {
        List<Cliente> clientesConCuit = archivoDeClientes.get(cuit);
        List<String> cuentasDelCliente = new ArrayList<>();
        for (Cliente cliente : clientesConCuit) {
            cuentasDelCliente.addAll(cliente.getCuentasDelCliente());
        }
        return cuentasDelCliente;
    }
    public void guardarMovimiento(String fecha, Operacion operacion){
        archivoDeMovimientos.put(fecha, operacion);
    }
    public void setClienteEnSesion(Tarjeta tarjetaDelCliente){

        clienteEnSesion = archivoDeClientes.get(tarjetaDelCliente.obtenerCuitAsociado()).get(0);
    }
    public Cliente obtenerClienteEnSesion(){
        return clienteEnSesion;
    }

    /*
       No hubo tiempo para hacer al algoritmo mas eficiente.
     */
    public List<Operacion> obtenerUltimos10Movimientos(Cuenta unaCuenta) throws NoSeEncuentranMovimientosException {
        List<Operacion> ultimos10Movimientos = new ArrayList<>();
        for(Operacion unaOperacion : archivoDeMovimientos.values()){
            if(unaOperacion.getCuenta().getAlias().equals(unaCuenta.getAlias()) && ultimos10Movimientos.size() < 10){
                ultimos10Movimientos.add(unaOperacion);
            }else{
                throw new NoSeEncuentranMovimientosException();
            }
        }
        return ultimos10Movimientos;
    }

    public Cuenta obtenerCuentaPorCuitYTipo(String cuit, String tipo) throws Exception {
        Cuenta cuentaElecta = null;
        for(String unAlias : obtenerCuentasDelCliente(cuit)){
            if(archivoDeCuentas.get(unAlias).obtenerTipo().equals(tipo)){
               cuentaElecta = archivoDeCuentas.get(unAlias);
            }
        }
        return cuentaElecta;
    }
}
