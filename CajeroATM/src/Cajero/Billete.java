package Cajero;

public class Billete {

    private int valor;
    private int cantidad;


    public Billete(int valor, int cantidad){
        this.cantidad = cantidad;
        this.valor = valor;
    }

    public int obtenerCantidad() {
        return cantidad;
    }

    public int obtenerValor() {
        return valor;
    }

    public void cambiarCantidad(int nuevaCantidad){

        cantidad = nuevaCantidad;
    }

    public int restarCantidad(int cantidad){

        this.cantidad -= cantidad;
       return cantidad;
    }


}
