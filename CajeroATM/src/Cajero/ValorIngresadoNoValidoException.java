package Cajero;

public class ValorIngresadoNoValidoException extends NumberFormatException {
    String mensaje;
    public ValorIngresadoNoValidoException(){
        mensaje = "Se ha finalizado la sesion";
        System.err.println(mensaje);
    }
    public ValorIngresadoNoValidoException(String mensaje){
        this.mensaje = mensaje;
        System.err.println(mensaje);
    }

    @Override
    public String toString() {
        return mensaje;
    }
}