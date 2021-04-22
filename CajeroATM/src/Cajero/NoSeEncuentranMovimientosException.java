package Cajero;

public class NoSeEncuentranMovimientosException extends Exception {

    String mensaje;
    public NoSeEncuentranMovimientosException(){
        mensaje = "No se encontraron movimientos en el sistema.";
        System.err.println("Monto ilegal");
    }
    public NoSeEncuentranMovimientosException(String mensaje){
        this.mensaje = mensaje;
        System.err.println(mensaje);
    }

    @Override
    public String toString() {
        return mensaje;
    }
}
