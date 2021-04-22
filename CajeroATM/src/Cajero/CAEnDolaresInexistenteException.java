package Cajero;

public class CAEnDolaresInexistenteException extends Exception {
    String mensaje;
    public CAEnDolaresInexistenteException(){
        mensaje = "Necesita una caja de ahorro en dolares para realizar la operacion.";
        System.err.println(mensaje);
    }
    public CAEnDolaresInexistenteException(String mensaje){
        this.mensaje = mensaje;
        System.err.println(mensaje);
    }

    @Override
    public String toString() {
        return mensaje;
    }
}