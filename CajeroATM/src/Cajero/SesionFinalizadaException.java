package Cajero;


public class SesionFinalizadaException extends NumberFormatException {
    String mensaje;
    public SesionFinalizadaException(){
        mensaje = "Se ha finalizado la sesion";
        System.err.println(mensaje);
    }
    public SesionFinalizadaException(String mensaje){
        this.mensaje = mensaje;
        System.err.println(mensaje);
    }

    @Override
    public String toString() {
        return mensaje;
    }
}
