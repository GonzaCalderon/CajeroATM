package Cajero;

import java.util.Comparator;

public class Tarjeta  {

    private String pin;
    private String numeroDeTarjeta;
    private String cuitAsociado;

    public Tarjeta(String pin, String numeroDeTarjeta, String cuitAsociado) {
        this.pin = pin;
        this.numeroDeTarjeta = numeroDeTarjeta;
        this.cuitAsociado = cuitAsociado;
    }

    public String obtenerPin() {
        return pin;
    }

    public String obtenerNumeroDeTarjeta() {
        return numeroDeTarjeta;
    }

    public String obtenerCuitAsociado() {
        return cuitAsociado;
    }


    Comparator<Tarjeta> comparador = new Comparator<Tarjeta>()
    {
        public int compare(Tarjeta tarjetaA, Tarjeta tarjetaB)
        {
            return tarjetaA.obtenerCuitAsociado().compareTo(tarjetaB.obtenerCuitAsociado());
        }
    };

    public int compareTo(Tarjeta tarjetaDeIdentificacion) {
        return obtenerNumeroDeTarjeta().compareTo(tarjetaDeIdentificacion.obtenerNumeroDeTarjeta());
    }
}