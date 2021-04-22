package Cajero;

public abstract class Operacion {

    protected String fecha;
    protected Cuenta unaCuenta;
    protected String concepto;
    protected double monto;

    public Operacion(String fecha, Cuenta unaCuenta, String concepto,double monto){
        this.fecha = fecha;
        this.unaCuenta = unaCuenta;
        this.concepto = concepto;
        this.monto = monto;
    }

    public abstract String toString();

    public String getFecha() {
        return fecha;
    }

    public Cuenta getCuenta() {
        return unaCuenta;
    }

    public String getConcepto() {
        return concepto;
    }

    public double getMonto() {
        return monto;
    }

}
