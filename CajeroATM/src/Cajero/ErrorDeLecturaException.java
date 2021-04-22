package Cajero;

import java.io.IOException;

public class ErrorDeLecturaException extends IOException {

    String mensaje;
    public ErrorDeLecturaException(String mensaje) {
        this.mensaje = mensaje;
        System.err.println(mensaje);
    }
    public ErrorDeLecturaException(){
        this.mensaje = "Error en la lectura de los archivos";
        System.err.println(mensaje);
    }

    @Override
    public String toString() {
        return mensaje;
    }
}
