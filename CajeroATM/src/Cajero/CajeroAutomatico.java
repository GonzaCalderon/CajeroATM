package Cajero;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class CajeroAutomatico {

    private Date actualDate;
    private SimpleDateFormat fechaYHora;
    private SimpleDateFormat hora = new SimpleDateFormat("HH:mm:ss");
    private static final double unDolar = 63;
    private Dispensador unDispensador = new Dispensador();
    private RanuraDeDeposito unaRanuraDeDeposito = new RanuraDeDeposito();
    private LectorDeTarjetas unLectorDeTarjetas = new LectorDeTarjetas();
    private Pantalla unaPantalla = new Pantalla();
    private Teclado unTeclado = new Teclado();
    private Impresora unaImpresora = new Impresora();
    private Sesion unaSesion;

    public static void main(String[] args) throws Exception {
        CajeroAutomatico test = new CajeroAutomatico();
        test.iniciarSesion();
    }
    public void iniciarSesion() throws Exception {
        unaSesion = new Sesion();
        String numeroDeTarjeta = unTeclado.llamarTecladoAlfanumerico("Ingrese su tarjeta (Para la simulacion de esta instancia le pedimos que ingrese el cuit asociado a su tarjeta");
        Tarjeta tarjetaDelCliente = unaSesion.getArchivoDeValidacionDeTarjetas().get(numeroDeTarjeta);
        String pin = unTeclado.llamarTecladoAlfanumerico("Ingrese el pin");

        unaSesion.setClienteEnSesion(tarjetaDelCliente);
        inicializarOperacion(tarjetaDelCliente, pin);
    }
    public void finalizarSesion() throws IOException {
        unaSesion.cerrarSesion();
    }
    private boolean autenticarIdentidad(Tarjeta tarjeta, String pin) throws Exception {
        return unLectorDeTarjetas.leerTarjeta(tarjeta, pin, unaSesion.getArchivoDeValidacionDeTarjetas());
    }


    private void inicializarOperacion(Tarjeta tarjeta, String pin) throws Exception {
        Cuenta cuentaDelCliente = null;
        try {
            if (autenticarIdentidad(tarjeta, pin)) {
                List<String> opciones = new ArrayList<>();
                for (String unaCuentaDelCliente :
                        unaSesion.obtenerCuentasDelCliente(tarjeta.obtenerCuitAsociado())) {
                    opciones.add(unaSesion.getArchivoDeCuentas().get(unaCuentaDelCliente).obtenerTipo());  //debe llamar a la pantalla

                }
                opciones.add("Retirar tarjeta");
                opciones.add("Salir");
                int opcionElecta = unTeclado.llamarOpciones("Seleccione una cuenta para operar\n", opciones);
                if (opcionElecta == opciones.size() - 1) {
                    finalizarSesion();
                }else if(opcionElecta == opciones.size() -2){
                    finalizarSesion();
                    iniciarSesion();
                }else {
                    cuentaDelCliente = unaSesion.obtenerCuentaPorCuitYTipo(tarjeta.obtenerCuitAsociado(), opciones.get(opcionElecta));
                    seleccionarOperacion(cuentaDelCliente, unaSesion.obtenerClienteEnSesion());
                }
            }
        }
        catch (NoSeEncuentranMovimientosException e){
            e.printStackTrace();
            unaPantalla.mostrarError(e.toString());
            inicializarOperacion(tarjeta, pin);
        }
        catch (CAEnDolaresInexistenteException e){
            e.printStackTrace();
            unaPantalla.mostrarError(e.toString());
            inicializarOperacion(tarjeta, pin);
        }
        catch (TarjetaInexistenteException e){
            e.printStackTrace();
            unaPantalla.mostrarError(e.toString());
            inicializarOperacion(tarjeta, pin);
        }
        catch (ValorIngresadoNoValidoException e){
            e.printStackTrace();
            unaPantalla.mostrarError(e.toString());
            inicializarOperacion(tarjeta, pin);
        }
        catch (SesionFinalizadaException e){
            unaPantalla.mostrarError(e.toString());
            finalizarSesion();

        }
        catch (MontoIlegalException e){
            e.printStackTrace();
            unaPantalla.mostrarError(e.toString());
            volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
        }
        catch (DatoDuplicadoException e){
            e.printStackTrace();
            unaPantalla.mostrarError(e.toString());
            volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
        }
        catch (DineroInsuficienteException e){
            e.printStackTrace();
            unaPantalla.mostrarError(e.toString());
            volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
        }
        catch (ErrorDeLecturaException e){
            e.printStackTrace();
            unaPantalla.mostrarError(e.toString());
            volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
        }
        catch (Exception e){
            e.printStackTrace();
            unaPantalla.mostrarError("Ha ocurrido un error inesperado, intente nuevamente o busque personal que lo pueda ayudar");
            volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
        }
    }
    private void seleccionarOperacion(Cuenta cuentaDelcliente, Cliente clienteEnSesion) throws Exception {


        System.out.println("Usted seleccionó: "+cuentaDelcliente.obtenerTipo());
        unaPantalla.mostrarMensaje("Usted seleccionó: "+cuentaDelcliente.obtenerTipo());
        if(cuentaDelcliente.getClass() == CuentaCorriente.class){
            System.out.println("Seleccione una operacion: \n1.Deposito en pesos\n2.Extraccion\n3.Consultar alias.\n4.Comprar Dolares.\n5.Transferencia\n6.Consultar saldo.\n7.Consultar ultimos 10 movimientos.\n8.Volver atras.\n");
            int operacionElecta  = 1 + unTeclado.llamarOpciones("Seleccione una operacion: ", new String[]{"Deposito en pesos", "Extraccion", "Consultar alias", "Comprar Dolares", "Transferencia", "Consultar saldo.", "Obtener ultimas 10 operaciones.", "8.Volver atras."});
            switch (operacionElecta) {
                case 1 -> {
                    seleccionarDepositoEnPesos(cuentaDelcliente);
                }
                case 2 -> {
                    seleccionarExtraccion(cuentaDelcliente);
                }
                case 3 -> {
                    seleccionarConsultarAlias(cuentaDelcliente);
                }
                case 4 -> {
                    seleccionarComprarDolares(cuentaDelcliente, clienteEnSesion);
                }
                case 5 -> {
                    seleccionarTransferencia(cuentaDelcliente);
                }
                case 6 -> {
                    seleccionarConsultarSaldo(cuentaDelcliente);
                }
                case 7 -> {
                    seleccionarConsultarUltimos10Movimientos(cuentaDelcliente);
                }
                case 8 -> {
                    inicializarOperacion(clienteEnSesion.getTarjetaDeIdentificacion(), clienteEnSesion.getPin());
                }
            }
        }else if (cuentaDelcliente.getClass() == CAEnPesos.class){
            System.out.println("Seleccione una operacion: \n1.Deposito en pesos\n2.Extraccion\n3.Consultar alias.\n4.Comprar Dolares.\n5.Transferencia\n6.Consultar saldo\n7.Volver atras.\n");
            int operacionElecta  = 1 + unTeclado.llamarOpciones("Seleccione una operacion: ", new String[]{"Deposito en pesos", "Extraccion", "Consultar alias", "Comprar Dolares", "Transferencia", "Consultar saldo","Consultar ultimos 10 movimientos", "Volver atras."});
            switch (operacionElecta) {
                case 1 -> {
                    seleccionarDepositoEnPesos(cuentaDelcliente);
                }
                case 2 -> {
                    seleccionarExtraccion(cuentaDelcliente);
                }
                case 3 -> {
                    seleccionarConsultarAlias(cuentaDelcliente);
                }
                case 4 -> {
                    seleccionarComprarDolares(cuentaDelcliente, clienteEnSesion);
                }
                case 5 -> {
                    seleccionarTransferencia(cuentaDelcliente);
                }
                case 6 -> {
                    seleccionarConsultarSaldo(cuentaDelcliente);
                }
                case 7 -> {
                    seleccionarConsultarUltimos10Movimientos(cuentaDelcliente);
                }
                case 8 -> {
                    inicializarOperacion(clienteEnSesion.getTarjetaDeIdentificacion(), clienteEnSesion.getPin());
                }
            }
        }else if(cuentaDelcliente.getClass() == CAEnDolares.class){
            System.out.println("Seleccione una operacion: \n1.Deposito en dolares.\n2.Consultar alias.\n3.Consultar saldo.\n4.Volver atras.\n");
            int operacionElecta  = 1 +unTeclado.llamarOpciones("Seleccione una operacion: ", new String[]{"Deposito en dolares", "Consultar alias", "Consultar saldo","Consultar ultimos 10 movimientos", "Volver atras"});
            switch (operacionElecta) {
                case 1 -> {
                    seleccionarDepositoEnDolares(cuentaDelcliente);
                }
                case 2 -> {
                    seleccionarConsultarAlias(cuentaDelcliente);
                }
                case 3 -> {
                    seleccionarConsultarSaldo(cuentaDelcliente);
                }
                case 4 -> {
                    seleccionarConsultarUltimos10Movimientos(cuentaDelcliente);
                }
                case 5 -> {
                    inicializarOperacion(clienteEnSesion.getTarjetaDeIdentificacion(), clienteEnSesion.getPin());
                }
            }
        }
    }
    private void realizarOperacion(Operacion operacion, Cuenta cuentaDelCliente) throws Exception {
        if(operacion.getClass() == Transferencia.class){
            ((Transferencia) operacion).transferir();
            System.out.println("Usted ha iniciado una transferencia.\n Desde la cuenta: " + cuentaDelCliente.getAlias()+", con destino a la cuenta: "+ ((Transferencia) operacion).getCuentaDestino()+ ". Desea confirmar\n");
            unaPantalla.mostrarMensaje("Usted ha realizado una transferencia.\n Desde la cuenta: " + cuentaDelCliente.getAlias()+", con destino a la cuenta: "+ ((Transferencia) operacion).getCuentaDestino().getAlias());
            System.out.println("Se ha realizado la transferencia, seleccione:\n1.Anular la transferencia y finalizar sesion\n2.Finalizar sesion\n-");
            int seleccion = 1 + unTeclado.llamarOpciones("Se ha realizado la transferencia ", new String[]{"Anular la transferencia y finalizar sesion","Volver","Finalizar sesion"});
            switch (seleccion) {
                case 1 -> {
                    ((Transferencia) operacion).revertirOperacion();
                    unaSesion.guardarMovimiento(getFecha(), operacion);
                    preguntarSiImprimirOperacion(operacion, cuentaDelCliente);
                    volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
                }
                case 2 -> {
                    inicializarOperacion(unaSesion.obtenerClienteEnSesion().getTarjetaDeIdentificacion(), unaSesion.obtenerClienteEnSesion().getPin());
                    preguntarSiImprimirOperacion(operacion, cuentaDelCliente);
                }
                case 3 -> {
                    preguntarSiImprimirOperacion(operacion, cuentaDelCliente);
                    finalizarSesion();
                }
            }
        }else if(operacion.getClass() == OperacionDeDivisas.class){
            switch (operacion.concepto) {
                case "Deposito en pesos" -> {

                    System.out.println("Ingrese billetes de $100 (PESOS):\nCantidad: ");
                    int billetesDe100 = unTeclado.llamarTecladoNumerico("Ingrese billetes de $100 (PESOS):\nCantidad: ");
                    System.out.println("Ingrese billetes de $500 (PESOS):\nCantidad: ");
                    int billetesDe500 = unTeclado.llamarTecladoNumerico("Ingrese billetes de $500 (PESOS):\nCantidad: ");
                    System.out.println("Ingrese billetes de $1000 (PESOS):\nCantidad: ");
                    int billetesDe1000 = unTeclado.llamarTecladoNumerico("Ingrese billetes de $1000 (PESOS):\nCantidad: ");
                    unaRanuraDeDeposito.depositarPesos(billetesDe100,billetesDe500,billetesDe1000);
                    ((OperacionDeDivisas) operacion).depositarPesos(billetesDe100,billetesDe500,billetesDe1000);
                    unaSesion.guardarMovimiento(getHora(), operacion);
                    preguntarSiImprimirOperacion(operacion, cuentaDelCliente);
                    volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
                }
                case "Deposito en dolares" -> {
                    ((OperacionDeDivisas) operacion).depositarDolares();
                    unaSesion.guardarMovimiento(getHora(), operacion);
                    preguntarSiImprimirOperacion(operacion, cuentaDelCliente);
                    volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
                }
                case "Extraccion" -> {

                    try{
                        unDispensador.retirarEfectivo(((int)operacion.getMonto()));
                        ((OperacionDeDivisas) operacion).retirarEfectivo();
                        unaPantalla.mostrarMensaje(unDispensador.billetesEntregados());
                        unaSesion.guardarMovimiento(getHora(), operacion);
                        preguntarSiImprimirOperacion(operacion, cuentaDelCliente);
                    } finally {
                        volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
                    }
                }
                case "Comprar Dolares" -> {
                    for(String unAlias : unaSesion.obtenerCuentasDelCliente(unaSesion.obtenerClienteEnSesion().getCuit())){
                        if(unaSesion.getArchivoDeCuentas().get(unAlias).getClass() == CAEnDolares.class){
                            ((OperacionDeDivisas) operacion).comprarDolares(unaSesion.getArchivoDeCuentas().get(unAlias), unDolar);
                            unaSesion.guardarMovimiento(getHora(), operacion);
                            preguntarSiImprimirOperacion(operacion, cuentaDelCliente);
                        }
                    }
                    volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
                }
            }
        }
    }
    public void seleccionarDepositoEnPesos(Cuenta cuentaDelCliente) throws Exception {

        realizarOperacion(new OperacionDeDivisas(getFecha(), cuentaDelCliente, "Deposito en pesos", 0), cuentaDelCliente);

    }
    public void seleccionarDepositoEnDolares(Cuenta cuentaDelCliente) throws Exception {
        System.out.println("Indique un monto: ");
        double monto = unTeclado.llamarTecladoNumerico("Indique un monto: ");
        realizarOperacion(new OperacionDeDivisas(getFecha(), cuentaDelCliente, "Deposito en dolares", monto), cuentaDelCliente);
    }
    public void seleccionarExtraccion(Cuenta cuentaDelCliente) throws Exception {
        System.out.println("Indique un monto: ");
        double monto = unTeclado.llamarTecladoNumerico("Indique un monto: ");
        realizarOperacion(new OperacionDeDivisas(getFecha(), cuentaDelCliente, "Extraccion", monto), cuentaDelCliente);
    }
    public void seleccionarConsultarAlias(Cuenta cuentaDelCliente) throws Exception {
        System.out.println("El alias de la cuenta \""+cuentaDelCliente.obtenerTipo()+"\" es:"  + cuentaDelCliente.getAlias());
        unaImpresora.imprimirAlias(cuentaDelCliente.getAlias());
        unaPantalla.mostrarMensaje(cuentaDelCliente.getAlias());
        volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
    }
    private void seleccionarConsultarUltimos10Movimientos(Cuenta cuentaDelcliente) throws Exception {
        unaImpresora.imprimirUltimosMovimientos(unaSesion.obtenerUltimos10Movimientos(cuentaDelcliente));
        volverASeleccionarOperacionOFinalizarSesion(cuentaDelcliente);
    }
    public void seleccionarComprarDolares(Cuenta cuentaDelCliente, Cliente clienteEnSesion) throws Exception {
        System.out.println("El valor de UN dolar es: $"+unDolar+" (PESOS) y se cobra un impuesto del 30%.\nDesea continuar?\n1.SI\n2.NO (volvera al menu anterior)" );
        int seleccion = 1+ unTeclado.llamarOpciones("El valor de UN dolar es: $"+unDolar+" (PESOS) y se cobra el impuesto PAIS (30%).\nDesea continuar?", new String[] {"SI","NO (volvera al menu anterior)"});
        switch (seleccion) {
            case 1 -> {
                System.out.println("Indique un monto: ");
                double monto = unTeclado.llamarTecladoNumerico("Indique un monto: ");
                System.out.println("Por el monto total de: U$S" + monto + ". se cobraron: $" + monto * (unDolar + (unDolar * 0.3)) + " (PESOS).");
                unaPantalla.mostrarMensaje("Por el monto total de: U$S" + monto + ". se cobraron: " + monto * (unDolar + (unDolar * 0.3)) + " (PESOS).");
                realizarOperacion(new OperacionDeDivisas(getFecha(), cuentaDelCliente, "Comprar Dolares", monto), cuentaDelCliente);
            }
            case 2 -> {
                seleccionarOperacion(cuentaDelCliente, clienteEnSesion);
            }
        }
    }
    public void seleccionarTransferencia(Cuenta cuentaDelCliente) throws Exception {
        System.out.println("Indique un monto: ");
        double monto = unTeclado.llamarTecladoNumerico("Indique un monto: ");
        System.out.println("Ingrese el alias de destino: ");
        String aliasDeDestino = unTeclado.llamarTecladoAlfanumerico("Ingrese el alias de destino: ");
        Cuenta cuentaDestino = unaSesion.getArchivoDeCuentas().get(aliasDeDestino);
        realizarOperacion(new Transferencia(getFecha(), cuentaDelCliente, cuentaDestino, monto), cuentaDelCliente);
    }
    public  void seleccionarConsultarSaldo(Cuenta cuentaDelCliente) throws Exception {
        System.out.println("El saldo de la cuenta: \""+cuentaDelCliente.obtenerTipo()+"\" es: " + cuentaDelCliente.getSaldo());
        unaPantalla.mostrarMensaje("El saldo de la cuenta: \""+cuentaDelCliente.obtenerTipo()+"\" es: " + cuentaDelCliente.getSaldo());
        unaImpresora.imprimirSaldo(cuentaDelCliente.getSaldo());
        volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
    }
    public  void preguntarSiImprimirOperacion(Operacion operacion, Cuenta cuentaDelCliente) throws Exception {
        int seleccion = 1 + unTeclado.llamarOpciones("Desea imprimir la operacion?", new String[]{"SI","NO"});
        switch (seleccion) {
            case 1 -> {
                unaImpresora.imprimirOperacion(operacion);
            }
            case 2 -> {
                volverASeleccionarOperacionOFinalizarSesion(cuentaDelCliente);
            }
        }
    }
    public void volverASeleccionarOperacionOFinalizarSesion(Cuenta cuentaDelCliente) throws Exception {
        int seleccion = 1 + unTeclado.llamarOpciones("Operacion finalizada",new String[]{ "Volver", "Finalizar sesion"});
        switch (seleccion) {
            case 1 -> {
                seleccionarOperacion(cuentaDelCliente, unaSesion.obtenerClienteEnSesion());;
            }
            case 2 -> {
                finalizarSesion();
            }
        }
    }
    public String getFecha() {
        actualDate = new Date();
        fechaYHora = new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm:ss");
        return fechaYHora.format(actualDate);
    }
    public String getHora() {
        return hora.format(actualDate);
    }

}