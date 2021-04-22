package Cajero;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String pin;
    private String cuit;
    private Tarjeta tarjetaDeIdentificacion;
    private List<String> cuentasDelCliente = new ArrayList<>();

    /*
        Post: Crea un cliente y revisa si la cantidad de digitos de los valores dados es correcta, de lo contrario arroja las excepciones determinadas.
     */
    public  Cliente(String cuit) throws Exception {
        if(cuit.length() == 11){
            this.cuit = cuit;

        }else{
            throw new Exception("El cuit debe ser de 11 digitos y no puede empezar con 0.");
        }
    }

    public void obtenerTarjeta(String numeroDeTarjeta, String pin){
        this.pin = pin;
        tarjetaDeIdentificacion = new Tarjeta(pin,numeroDeTarjeta,cuit);
    }

    public Tarjeta ingresarTarjeta() {

        return tarjetaDeIdentificacion;
    }
    public String getPin(){
        return pin;
    }

    public String getCuit() {
        return cuit;
    }

    public List<String> getCuentasDelCliente(){
        return cuentasDelCliente;
    }

    public Tarjeta getTarjetaDeIdentificacion() {
        return tarjetaDeIdentificacion;
    }

    public void agregarCuentaCorriente(String alias) throws Exception {
        cuentasDelCliente.add(alias);
    }

    public void agregarCajaDeAhorroEnDolares(String alias) {
        cuentasDelCliente.add(alias);
    }

    public void agregarCajaDeAhorroEnPesos(String alias) {
        cuentasDelCliente.add(alias);
    }


}