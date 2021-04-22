package Cajero;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Impresora {
        BufferedWriter escritor;
    public void imprimirOperacion(Operacion operacion) throws IOException {
        String impresion = operacion.toString();
        String archivo = "Operacion";
        imprimir(impresion,archivo);
    }

    public void imprimirUltimosMovimientos(List<Operacion> ultimos10Movimientos) throws IOException {

        String archivo = "Ultimos10Movimientos";
        escritor = new BufferedWriter(new FileWriter(new File(archivo)));
        for (Operacion unMovimiento :
                ultimos10Movimientos) {
            String impresion = unMovimiento.toString();
            escritor.write(impresion);
            escritor.newLine();
        }
        escritor.close();
    }

    public void imprimirAlias(String alias) throws IOException {
        String impresion = alias ;
        String archivo = "Alias";
        imprimir(impresion,archivo);

    }
    public void imprimirSaldo(double saldo) throws IOException {
        String impresion = String.valueOf(saldo);
        String archivo = "Saldo";
        imprimir(impresion,archivo);
    }
    private void imprimir(String impresion, String archivo) throws IOException {
       try{
           escritor = new BufferedWriter(new FileWriter(new File(archivo)));
           escritor.write(impresion);
           escritor.close();
       }catch (IOException e){
           throw new IOException("Hubo un error al imprimir el archivo");
       }

    }


}
