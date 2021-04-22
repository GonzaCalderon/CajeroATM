package Cajero;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class LectorDeArchivos {


    public Map<String, Cuenta> obtenerArchivoDeCuentas() throws Exception {
        String[] datos;
        String alias;
        double saldo;
        Map<String, Cuenta> cuentas = new TreeMap<>();
        try (BufferedReader lectorDeCuentas = new BufferedReader(new FileReader("CajeroATM/ArchivoDeCuentas"))) {
            String unaLinea;
            while ((unaLinea = lectorDeCuentas.readLine()) != null) {
                if (!unaLinea.isEmpty()) {
                    datos = unaLinea.split(",");
                    switch (datos[0]) {
                        case "01" -> {
                            alias = datos[1];
                            saldo = Double.parseDouble(datos[2]);
                            cuentas.put(alias, new CAEnPesos(alias, saldo));
                        }
                        case "02" -> {
                            alias = datos[1];
                            saldo = Double.parseDouble(datos[2]);
                            double descubierto = Double.parseDouble(datos[3]); //Ver la ultima parte
                            cuentas.put(alias, new CuentaCorriente(alias, saldo, descubierto));
                        }
                        case "03" -> {
                            alias = datos[1];
                            saldo = Double.parseDouble(datos[2]);
                            cuentas.put(alias, new CAEnDolares(alias, saldo));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ErrorDeLecturaException("Error al leer los archivos de cuentas. Intente nuevamente");
        } catch (Exception e) {
            throw new Exception("Error en el sistema intente nuevamente");
        }
        return  cuentas;
    }
    public  Map<String, Tarjeta> obtenerArchivoDeValidacionDeTarjetas() throws IOException {
        String numeroDeTarjeta;
        String pin;
        String cuit;
        String[] datos;
        Map<String, Tarjeta> tarjetas = new TreeMap<>();
        try (BufferedReader lectorDeTarjetas = new BufferedReader(new FileReader("CajeroATM/ArchivoDeValidacionDeTarjetas"))) {
            String unaLinea;
            while ((unaLinea = lectorDeTarjetas.readLine()) != null) {
                if (!unaLinea.isEmpty()) {
                    datos = unaLinea.split(",");
                    numeroDeTarjeta = datos[0];
                    pin = datos[1];
                    cuit = datos[2];
                    tarjetas.put(cuit, new Tarjeta(pin, numeroDeTarjeta, cuit));
                }
            }
        } catch (IOException e) {
            throw new ErrorDeLecturaException("Error al leer los archivos de validacion de tarjetas. Intente nuevamente");
        }
        return tarjetas;
    }
    public Map<String, List<Cliente>> obtenerArchivoDeClientes() throws Exception {
        String[] datos;
        String cuit;
        String alias;
        Map<String, List<Cliente>> clientes = new TreeMap<>();
        List<Cliente> datosDeClientes;
        try (BufferedReader lectorDeClientes = new BufferedReader(new FileReader("CajeroATM/ArchivoDeClientes"))) {

            String unaLinea;
            while ((unaLinea = lectorDeClientes.readLine()) != null) {
                if (!unaLinea.isEmpty()) {
                    datos = unaLinea.split(",");
                    cuit = datos[0];
                    alias = datos[1];
                    if (!clientes.containsKey(cuit)) {
                        datosDeClientes = new ArrayList<>();
                    } else {
                        datosDeClientes = clientes.get(cuit);
                    }
                    Cliente unCliente = new Cliente(cuit);
                    datosDeClientes.add(unCliente);
                    clientes.put(cuit, datosDeClientes);
                    obtenerCuentasDelCliente(unCliente, alias);
                    obtenerTarjetaDelCliente(unCliente);
                }
            }
        } catch (IOException e) {
            throw new ErrorDeLecturaException("Error al leer los archivos de clientes, intente nuevamente");
        } catch (Exception e) {
            throw new Exception("Error en el sistema, intente nuevamente");
        }
        return clientes;
    }
    private void obtenerTarjetaDelCliente(Cliente unCliente) throws Exception {
        String pin = obtenerArchivoDeValidacionDeTarjetas().get(unCliente.getCuit()).obtenerPin();
        String numeroDeTarjeta = obtenerArchivoDeValidacionDeTarjetas().get(unCliente.getCuit()).obtenerNumeroDeTarjeta();
        unCliente.obtenerTarjeta(numeroDeTarjeta,pin);
    }
    private void obtenerCuentasDelCliente(Cliente unCliente,String alias) throws Exception {

        Cuenta unaCuentaEncontrada = obtenerArchivoDeCuentas().get(alias);
        if(unaCuentaEncontrada.getClass() == CuentaCorriente.class){
            unCliente.agregarCuentaCorriente(alias);
        }else if(unaCuentaEncontrada.getClass() == CAEnPesos.class){
            unCliente.agregarCajaDeAhorroEnPesos(alias);
        }else if(unaCuentaEncontrada.getClass() == CAEnDolares.class){
            unCliente.agregarCajaDeAhorroEnDolares(alias);
        }

    }
    private Cuenta obtenerCuenta(String alias) throws Exception {

        return obtenerArchivoDeCuentas().get(alias);
    }

    public  Map<String, Operacion> obtenerArchivoDeMovimientos() throws Exception {
        String fecha;
        String concepto;
        String alias;
        Cuenta unaCuenta;
        double importe;
        String[] datos;
        Map<String, Operacion> movimientos = new TreeMap<>();
        try (BufferedReader lectorDeMovimientos = new BufferedReader(new FileReader("CajeroATM/ArchivoDeMovimientos"))) {

            String unaLinea;
            while ((unaLinea = lectorDeMovimientos.readLine()) != null) {
                if (!unaLinea.isEmpty()) {
                    datos = unaLinea.split(",");
                    fecha = datos[0];
                    concepto = datos[1];
                    alias = datos[2];
                    unaCuenta = obtenerCuenta(alias);
                    importe = Double.parseDouble(datos[3]);
                    movimientos.put(fecha, new OperacionDeDivisas(fecha, unaCuenta, concepto, importe));
                }
            }
        } catch (IOException e) {
            throw new ErrorDeLecturaException("Hubo un error al leer el archivo, intente nuevamente");
        } catch (Exception e) {
            throw new Exception("Error en el sistema intente nuevamente");
        }
        return movimientos;
    }


}