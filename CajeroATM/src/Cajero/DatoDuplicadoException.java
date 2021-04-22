package Cajero;

import java.io.IOException;

public class DatoDuplicadoException extends IOException {
        String mensaje;
    public DatoDuplicadoException(){
        mensaje = "El dato ya fue creado";
        System.err.println(mensaje);
    }
    public DatoDuplicadoException(String mensaje){
        this.mensaje = mensaje;
        System.err.println(mensaje);
    }

    @Override
    public String toString() {
        return mensaje;
    }
}
