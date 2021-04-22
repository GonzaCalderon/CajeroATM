package Cajero;

public class MontoIlegalException extends Exception {
    String mensaje;
    public MontoIlegalException(){
        mensaje = "Monto ilegal";
        System.err.println("Monto ilegal");
    }
    public MontoIlegalException(String mensaje){
        this.mensaje = mensaje;
        System.err.println(mensaje);
    }

    @Override
    public String toString() {
        return mensaje;
    }
}
