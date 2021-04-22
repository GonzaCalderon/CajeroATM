package Cajero;


import java.io.FileWriter;
import java.io.IOException;


public class ClienteTest {


    private void limpiarArchivo(String archivo) throws IOException {
        try {
            FileWriter fw =new FileWriter(archivo, false);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(archivo + " fue limpiado despues de realizar el test de escritura.");
    }

//     @Test
//    public void crearUnClienteYComprobarQueEsta() throws Exception {
//         Cliente carlitos = new Cliente("1234", "12345678910", "12345678");
//         LectorDeArchivos lector = new LectorDeArchivos();
//         carlitos.crearCuentaCorriente("pato.perro.murcielago", 25000, 10000);
//         String[] arrayEsperado = {"12345678910", "pato.perro.murcielago"};
//         String[] arrayResultado = lector.obtenerArchivo("ArchivoDeClientes", "12345678910");
//         boolean estaCarlitos = Arrays.deepEquals(arrayEsperado, arrayResultado);
//         Assert.assertTrue(estaCarlitos);
//
////         limpiarArchivo("ArchivoDeClientes");
////         limpiarArchivo("ArchivoDeCuentas");
//     }
//
//     @Test
//     public void crearUna_CC_AlCliente() throws Exception {
//          Cliente carlitos = new Cliente("1234", "12345678910", "12345678");
//          carlitos.crearCuentaCorriente("pato.perro.oso",25000,10000);
//          LectorDeArchivos lector = new LectorDeArchivos();
//          String[] CCDeCarlitos = lector.obtenerArchivo("ArchivoDeCuentas", "pato.perro.oso");
//          String[] esperado = {"02","pato.perro.oso","25000.0","[10000.0]"};
//          boolean estaLaCuentaDeCarlitos = Arrays.deepEquals(CCDeCarlitos,esperado);
//          Assert.assertTrue(estaLaCuentaDeCarlitos);
//
////          limpiarArchivo("ArchivoDeClientes");
////          limpiarArchivo("ArchivoDeCuentas");
//     }
//    @Test
//    public void crearUna_CA_U$D_AlCliente() throws Exception {
//        Cliente carlitos = new Cliente("1234", "12345678910", "12345678");
//        carlitos.crearCajaDeAhorroEnDolares("pato.perro.venado",15000);
//        LectorDeArchivos lector = new LectorDeArchivos();
//        String[] CAU$D_deCarlitos = lector.obtenerArchivo("ArchivoDeCuentas", "pato.perro.venado");
//        String[] esperado = {"03","pato.perro.venado","15000.0"};
//        boolean estaLaCuentaDeCarlitos = Arrays.deepEquals(CAU$D_deCarlitos,esperado);
//        Assert.assertTrue(estaLaCuentaDeCarlitos);
//
////        limpiarArchivo("ArchivoDeClientes");
////        limpiarArchivo("ArchivoDeCuentas");
//
//    }
//
//    @Test
//    public void crearUna_CA_AlCliente() throws Exception {
//        Cliente carlitos = new Cliente("1234", "12345678910", "12345678");
//        carlitos.crearCajaDeAhorroEnPesos("pato.perro.gato", 80000);
//        LectorDeArchivos lector = new LectorDeArchivos();
//        String[] CCDeCarlitos = lector.obtenerArchivo("ArchivoDeCuentas", "pato.perro.gato");
//        String[] esperado = {"01","pato.perro.gato","80000.0"};
//        boolean estaLaCuentaDeCarlitos = Arrays.deepEquals(CCDeCarlitos,esperado);
//        Assert.assertTrue(estaLaCuentaDeCarlitos);
//
////        limpiarArchivo("ArchivoDeClientes");
////        limpiarArchivo("ArchivoDeCuentas");
//    }



}