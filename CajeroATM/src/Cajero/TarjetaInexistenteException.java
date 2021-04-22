package Cajero;

public class TarjetaInexistenteException extends Exception {

    String mensaje;
    public TarjetaInexistenteException(String mensaje) {
        this.mensaje = mensaje;
        System.err.println(mensaje);
    }
    public TarjetaInexistenteException(){
        this.mensaje = "La tarjeta ingresada no es valida en este banco.";
        System.err.println(mensaje);
    }

    @Override
    public String toString() {
        return mensaje;
    }
}
