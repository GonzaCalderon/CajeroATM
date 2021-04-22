package Cajero;

public class DineroInsuficienteException extends Exception {

    String mensaje;

    public DineroInsuficienteException(){
        mensaje = "Dinero insuficiente";
        System.err.println("Dinero insuficiente");
    }
    public DineroInsuficienteException(String mensaje) {
        this.mensaje = mensaje;
        System.err.println(mensaje);
    }
    @Override
    public String toString() {
        return mensaje;
    }
}
