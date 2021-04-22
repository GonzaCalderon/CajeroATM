package Cajero;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ATMTests {


    Cliente carlitos, marcos, marta;
    Cuenta CAEnPesosDeCarlitos, CAEnDolaresDeCarlitos, CuentaCorrienteDeCarlitos,
            CAEnPesosDeMarcos, CAEnDolaresDeMarcos, CuentaCorrienteDeMarcos,
            CAEnPesosDeMarta,CuentaCorrienteDeMarta;


    public void inicializarClientes() throws Exception {


        // SE CREA AL CLIENTE CARLITOS

        String cuitDeCarlitos = "12345678910";
        String numeroDeTarjetaDeCarlitos = "12345678";
        String pinDeCarlitos = "1234";

        carlitos = new Cliente(cuitDeCarlitos);
        carlitos.obtenerTarjeta(numeroDeTarjetaDeCarlitos, pinDeCarlitos);

        carlitos.agregarCajaDeAhorroEnPesos("pan.mermelada.queso");
        carlitos.agregarCajaDeAhorroEnDolares("pan.mermelada.manteca");
        carlitos.agregarCuentaCorriente("pan.mortadela.jamon");

        //SE CREA AL CLIENTE MARCOS

        String cuitDeMarcos = "20401234562";
        String numeroDeTarjetaDeMarcos = "40123456";
        String pinDeMarcos = "4012";

        marcos = new Cliente(cuitDeMarcos);
        marcos.obtenerTarjeta(numeroDeTarjetaDeMarcos, pinDeMarcos);


        marcos.agregarCajaDeAhorroEnPesos("animal.felino.gato");
        marcos.agregarCajaDeAhorroEnDolares("animal.felino.tigre");
        marcos.agregarCuentaCorriente("animal.canino.perro");

        //SE CREA AL CLIENTE MARTA

        String cuitDeMarta = "22058995432";
        String numeroDeTarjetaDeMarta = "05899543";
        String pinDeMarta = "0589";

        marta = new Cliente(cuitDeMarta);
        marta.obtenerTarjeta(numeroDeTarjetaDeMarta, pinDeMarta);


        marta.agregarCajaDeAhorroEnPesos("luz.lampara.plata");
        marta.agregarCuentaCorriente("luz.vela.llama");

    }
    public void inicializarCuentas() throws Exception {

        CAEnPesosDeCarlitos = new CAEnPesos("pan.mermelada.queso", 20000);
        CAEnDolaresDeCarlitos = new CAEnDolares("pan.mermelada.manteca", 1500);
        CuentaCorrienteDeCarlitos = new CuentaCorriente("pan.mortadela.jamon", 0, 15000);


        CAEnPesosDeMarcos = new CAEnPesos("animal.felino.gato", 45000);
        CAEnDolaresDeMarcos = new CAEnDolares("animal.felino.tigre", 0);
        CuentaCorrienteDeMarcos = new CuentaCorriente("animal.canino.perro", 5000, 30000);


        CAEnPesosDeMarta = new CAEnPesos("luz.lampara.plata", 250000);
        CuentaCorrienteDeMarta = new CuentaCorriente("luz.vela.llama", -25000, 50000);



    }
    public String getFecha(){
        Date fecha = new Date();
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm:ss");
        return formatoDeFecha.format(fecha);
    }

    //TEST OPERACIONES

    @Test
    public void depositoEn_CAEnPesos() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas unDepositoDeCarlitos = new OperacionDeDivisas(getFecha(), CAEnPesosDeCarlitos, "Deposito en pesos", 0);
        unDepositoDeCarlitos.depositarPesos(5, 2, 1); //2500 pesos
        Assert.assertEquals(CAEnPesosDeCarlitos.getSaldo(), 22500, 0);

        OperacionDeDivisas unDepositoDeMarcos = new OperacionDeDivisas(getFecha(), CAEnPesosDeMarcos, "Deposito en pesos", 0);
        unDepositoDeMarcos.depositarPesos(0, 2, 0); //1000 pesos
        Assert.assertEquals(CAEnPesosDeMarcos.getSaldo(), 46000, 0);

        OperacionDeDivisas unDepositoDeMarta = new OperacionDeDivisas(getFecha(), CAEnPesosDeMarta, "Deposito en pesos", 0);
        unDepositoDeMarta.depositarPesos(0, 0, 20); //20000 pesos
        Assert.assertEquals(CAEnPesosDeMarta.getSaldo(), 270000, 0);

    }

    @Test
    public void depositoEn_CuentaCorriente() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas unDepositoDeCarlitos = new OperacionDeDivisas(getFecha(), CuentaCorrienteDeCarlitos, "Deposito en pesos", 0);
        unDepositoDeCarlitos.depositarPesos(5, 2, 1); //2500 pesos
        Assert.assertEquals(CuentaCorrienteDeCarlitos.getSaldo(), 2500, 0);

        OperacionDeDivisas unDepositoDeMarcos = new OperacionDeDivisas(getFecha(), CuentaCorrienteDeMarcos, "Deposito en pesos", 0);
        unDepositoDeMarcos.depositarPesos(0, 2, 0); //1000 pesos
        Assert.assertEquals(CuentaCorrienteDeMarcos.getSaldo(), 6000, 0);

        OperacionDeDivisas unDepositoDeMarta = new OperacionDeDivisas(getFecha(), CuentaCorrienteDeMarta, "Deposito en pesos", 0);
        unDepositoDeMarta.depositarPesos(0, 0, 20); //20000 pesos
        Assert.assertEquals(CuentaCorrienteDeMarta.getSaldo(), -5000, 0);

    }

    @Test
    public void depositoEn_CAEnDolares() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas unDepositoDeCarlitos = new OperacionDeDivisas(getFecha(), CAEnDolaresDeCarlitos, "Deposito en dolares", 100);
        unDepositoDeCarlitos.depositarDolares();
        Assert.assertEquals(CAEnDolaresDeCarlitos.getSaldo(), 1600, 0);

        OperacionDeDivisas unDepositoDeMarcos = new OperacionDeDivisas(getFecha(), CAEnDolaresDeMarcos, "Deposito en dolares", 2500);
        unDepositoDeMarcos.depositarDolares();
        Assert.assertEquals(CAEnDolaresDeMarcos.getSaldo(), 2500, 0);
    }

    @Test (expected = NullPointerException.class)
    public void depositoEn_CAEnDolaresInexistente() throws Exception {
        inicializarCuentas();
        inicializarClientes();

        OperacionDeDivisas unDepositoDeMarta = new OperacionDeDivisas(getFecha(), null, "Deposito en dolares", 1400);
        unDepositoDeMarta.depositarDolares();
    }

    @Test
    public void extraccionEn_CAEnPesos() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas unaExtraccionDeCarlitos = new OperacionDeDivisas(getFecha(), CAEnPesosDeCarlitos, "Extraccion", 20000);
        unaExtraccionDeCarlitos.retirarEfectivo();
        Assert.assertEquals(CAEnPesosDeCarlitos.getSaldo(), 0, 0);

        OperacionDeDivisas unaExtraccionDeMarcos = new OperacionDeDivisas(getFecha(), CAEnPesosDeMarcos, "Extraccion", 20000);
        unaExtraccionDeMarcos.retirarEfectivo();
        Assert.assertEquals(CAEnPesosDeMarcos.getSaldo(), 25000, 0);

        OperacionDeDivisas unaExtraccionDeMarta = new OperacionDeDivisas(getFecha(), CAEnPesosDeMarta, "Extraccion", 20000);
        unaExtraccionDeMarta.retirarEfectivo();
        Assert.assertEquals(CAEnPesosDeMarta.getSaldo(), 230000, 0);
    }

    @Test
    public void extraccionEn_CuentaCorriente() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas unaExtraccionDeCarlitos = new OperacionDeDivisas(getFecha(), CuentaCorrienteDeCarlitos, "Extraccion", 10000);
        unaExtraccionDeCarlitos.retirarEfectivo();
        Assert.assertEquals(CuentaCorrienteDeCarlitos.getSaldo(), -10000, 0);

        OperacionDeDivisas unaExtraccionDeMarcos = new OperacionDeDivisas(getFecha(), CuentaCorrienteDeMarcos, "Extraccion", 1333);
        unaExtraccionDeMarcos.retirarEfectivo();
        Assert.assertEquals(CuentaCorrienteDeMarcos.getSaldo(), 3667, 0);

        OperacionDeDivisas unaExtraccionDeMarta = new OperacionDeDivisas(getFecha(), CuentaCorrienteDeMarta, "Extraccion", 15000);
        unaExtraccionDeMarta.retirarEfectivo();
        Assert.assertEquals(CuentaCorrienteDeMarta.getSaldo(), -40000, 0);
    }

    @Test (expected = DineroInsuficienteException.class)
    public void ExtraccionEn_CAEnPesos_montoMayorAlQueSePuede() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas unaExtraccionDeCarlitos = new OperacionDeDivisas(getFecha(), CAEnPesosDeCarlitos, "Extraccion", 25000);
        unaExtraccionDeCarlitos.retirarEfectivo();
    }

    @Test (expected =  DineroInsuficienteException.class)
    public void ExtraccionEn_CuentaCorriente_montoMayorAlQueSePuede() throws Exception {

        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas unaExtraccionDeCarlitos = new OperacionDeDivisas(getFecha(), CuentaCorrienteDeMarta, "Extraccion", 99588);
        unaExtraccionDeCarlitos.retirarEfectivo();
    }

    @Test (expected =  DineroInsuficienteException.class)
    public void ExtraccionEn_CAEnDolares_montoMayorAlQueSePuede() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas unaExtraccionDeCarlitos = new OperacionDeDivisas(getFecha(), CAEnDolaresDeMarcos, "Extraccion", 1);
        unaExtraccionDeCarlitos.retirarEfectivo();
    }

    @Test
    public void consultarAliasEn_CAEnPesos() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        Assert.assertSame("pan.mermelada.queso", CAEnPesosDeCarlitos.getAlias());
        Assert.assertSame("animal.felino.gato", CAEnPesosDeMarcos.getAlias());
        Assert.assertSame("luz.lampara.plata", CAEnPesosDeMarta.getAlias());
    }

    @Test
    public void consultarAliasEn_CAEnDolares() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        Assert.assertSame("pan.mermelada.manteca", CAEnDolaresDeCarlitos.getAlias());
        Assert.assertSame("animal.felino.tigre", CAEnDolaresDeMarcos.getAlias());
    }

    @Test
    public void consultarAliasEn_CuentaCorriente() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        Assert.assertSame("pan.mortadela.jamon", CuentaCorrienteDeCarlitos.getAlias());
        Assert.assertSame("animal.canino.perro", CuentaCorrienteDeMarcos.getAlias());
        Assert.assertSame("luz.vela.llama", CuentaCorrienteDeMarta.getAlias());
    }

    @Test
    public void consultarSaldoEn_CAEnPesos() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        Assert.assertEquals(20000, CAEnPesosDeCarlitos.getSaldo(), 0.0);
        Assert.assertEquals(45000, CAEnPesosDeMarcos.getSaldo(), 0.0);
        Assert.assertEquals(250000, CAEnPesosDeMarta.getSaldo(), 0.0);
    }

    @Test
    public void consultarSaldoEn_CAEnDolares() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        Assert.assertEquals(1500, CAEnDolaresDeCarlitos.getSaldo(), 0.0);
        Assert.assertEquals(0, CAEnDolaresDeMarcos.getSaldo(), 0.0);
    }

    @Test
    public void consultarSaldoEn_CuentaCorriente() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        Assert.assertEquals(0, CuentaCorrienteDeCarlitos.getSaldo(), 0.0);
        Assert.assertEquals(5000, CuentaCorrienteDeMarcos.getSaldo(), 0.0);
        Assert.assertEquals(-25000, CuentaCorrienteDeMarta.getSaldo(), 0.0);
    }

   @Test
   public void transferencia_desdeCC_hastaCAEnPesos_CCquedaEnSaldoNegativo() throws Exception {
       inicializarClientes();
       inicializarCuentas();

       Transferencia unaTransferencia = new Transferencia(getFecha(),CuentaCorrienteDeCarlitos,CAEnPesosDeMarta, 5000);
       unaTransferencia.transferir();

       Assert.assertEquals(CuentaCorrienteDeCarlitos.getSaldo(), -5000, 0);
       Assert.assertEquals(CAEnPesosDeMarta.getSaldo(), 255000, 0);

   }

   @Test
    public void transferencia_desdeCAEnPesos_hastaCuentaCorriente_CCquedaENSaldoNegativo() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        Transferencia unaTransferencia = new Transferencia(getFecha(), CAEnPesosDeMarcos, CuentaCorrienteDeMarta, 5000);
        unaTransferencia.transferir();

        Assert.assertEquals(CAEnPesosDeMarcos.getSaldo(), 40000, 0);
        Assert.assertEquals(CuentaCorrienteDeMarta.getSaldo(), -20000, 0);

   }

    @Test
    public void transferencia_desdeCAEnPesos_hastaCuentaCorriente_CCquedaENSaldoPositivo() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        Transferencia unaTransferencia = new Transferencia(getFecha(), CAEnPesosDeMarcos, CuentaCorrienteDeMarta, 45000);
        unaTransferencia.transferir();

        Assert.assertEquals(CAEnPesosDeMarcos.getSaldo(), 0, 0);
        Assert.assertEquals(CuentaCorrienteDeMarta.getSaldo(), 20000, 0);

    }

    @Test
    public void transferenciaEntreCAEnPesos() throws Exception {

        inicializarCuentas();
        inicializarClientes();

        Transferencia unaTransferencia = new Transferencia(getFecha(), CAEnPesosDeMarta, CAEnPesosDeCarlitos, 50000);
        unaTransferencia.transferir();

        Assert.assertEquals(CAEnPesosDeMarta.getSaldo(), 200000, 0);
        Assert.assertEquals(CAEnPesosDeCarlitos.getSaldo(), 70000, 0);
    }

    @Test
    public void transferenciaEntreCC_ambasQuedanEnNegativo() throws Exception {
        inicializarCuentas();
        inicializarClientes();

        Transferencia unaTransferencia = new Transferencia(getFecha(), CuentaCorrienteDeCarlitos, CuentaCorrienteDeMarta, 10000);
        unaTransferencia.transferir();

        Assert.assertEquals(CuentaCorrienteDeCarlitos.getSaldo(), -10000, 0);
        Assert.assertEquals(CuentaCorrienteDeMarta.getSaldo(), -15000, 0);
    }

    @Test
    public void transferenciaEntreCC_cuentaDestinoQuedaEnNegativo() throws Exception {
        inicializarCuentas();
        inicializarClientes();

        Transferencia unaTransferencia = new Transferencia(getFecha(), CuentaCorrienteDeMarcos, CuentaCorrienteDeMarta, 5000);
        unaTransferencia.transferir();

        Assert.assertEquals(CuentaCorrienteDeMarcos.getSaldo(), 0, 0);
        Assert.assertEquals(CuentaCorrienteDeMarta.getSaldo(), -20000, 0);
    }

    @Test
    public void transferenciaEntreCC_cuentaEmisoraQuedaEnNegativo() throws Exception {
        inicializarCuentas();
        inicializarClientes();

        Transferencia unaTransferencia = new Transferencia(getFecha(), CuentaCorrienteDeMarta, CuentaCorrienteDeCarlitos, 100);
        unaTransferencia.transferir();

        Assert.assertEquals(CuentaCorrienteDeMarta.getSaldo(), -25100, 0 );
        Assert.assertEquals(CuentaCorrienteDeCarlitos.getSaldo(), 100, 0);
    }

    @Test (expected = DineroInsuficienteException.class)
    public void transferenciaValorExcedidoDelSaldoTiraExcepcion() throws Exception {
        inicializarCuentas();
        inicializarClientes();

        Transferencia unaTransferencia = new Transferencia(getFecha(), CAEnPesosDeMarta, CAEnPesosDeCarlitos, 300999);
        unaTransferencia.transferir();
    }

    @Test
    public void transferenciaRevertida() throws Exception {
        inicializarCuentas();
        inicializarClientes();

        Transferencia unaTransferencia = new Transferencia(getFecha(), CAEnPesosDeMarta, CAEnPesosDeCarlitos, 1000);
        unaTransferencia.transferir();
        unaTransferencia.revertirOperacion();

        Assert.assertEquals(CAEnPesosDeMarta.getSaldo(), 250000, 0);
        Assert.assertEquals(CAEnPesosDeCarlitos.getSaldo(), 20000, 0);
    }

    @Test
    public void comprarDolaresEn_CAEnPesos() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas operacionCarlitos = new OperacionDeDivisas(getFecha(), CAEnPesosDeCarlitos, "Comprar dolares", 10);
        operacionCarlitos.comprarDolares(CAEnDolaresDeCarlitos, 63);
        Assert.assertEquals(19181, CAEnPesosDeCarlitos.getSaldo(), 1);
        Assert.assertEquals(1510, CAEnDolaresDeCarlitos.getSaldo(), 1);
    }

    @Test
    public void comprarDolaresEn_CuentaCorriente() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas operacionMarcos = new OperacionDeDivisas(getFecha(), CuentaCorrienteDeMarcos, "Comprar dolares", 20);
        operacionMarcos.comprarDolares(CAEnDolaresDeMarcos, 63);
        Assert.assertEquals(3362, CuentaCorrienteDeMarcos.getSaldo(), 1);
        Assert.assertEquals(20, CAEnDolaresDeMarcos.getSaldo(), 1);
    }

    @Test (expected = MontoIlegalException.class)
    public void noAlcanzaParaComprarDolares() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas operacionMarcos = new OperacionDeDivisas(getFecha(), CuentaCorrienteDeMarcos, "Comprar dolares", 1000);
        operacionMarcos.comprarDolares(CAEnDolaresDeMarcos, 63);
    }

    @Test
    public void comprarDolaresUsandoElDescubierto() throws Exception {
        inicializarClientes();
        inicializarCuentas();

        OperacionDeDivisas operacionMarcos = new OperacionDeDivisas(getFecha(), CuentaCorrienteDeMarcos, "Comprar dolares", 80);
        operacionMarcos.comprarDolares(CAEnDolaresDeMarcos, 63);
    }

    @Test (expected = MontoIlegalException.class)
    public void retirarMontoNoDivisiblePor100() throws Exception {
        Dispensador unDispensador = new Dispensador();
        unDispensador.retirarEfectivo(1350);
    }

    @Test (expected = MontoIlegalException.class)
    public void retirarMontoNegativo() throws Exception {
        Dispensador unDispensador = new Dispensador();
        unDispensador.retirarEfectivo(-500);
    }

    @Test (expected = MontoIlegalException.class)
    public void NoAlcanzanLosBilletes() throws Exception {
        Dispensador unDispensador = new Dispensador();
        unDispensador.retirarEfectivo(1000000);
    }

}