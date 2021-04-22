package Cajero;

import java.util.Map;

public class LectorDeTarjetas {

    LectorDeArchivos lector;

    public boolean leerTarjeta(Tarjeta tarjeta, String pin, Map<String,Tarjeta> tarjetasGuardadas) throws TarjetaInexistenteException {
        try{

            lector = new LectorDeArchivos();
            Tarjeta tarjetaGuardada;
            if(tarjetasGuardadas.containsKey(tarjeta.obtenerCuitAsociado())){
                tarjetaGuardada = tarjetasGuardadas.get(tarjeta.obtenerCuitAsociado());
                return tarjetaGuardada.obtenerPin().equals(pin);
            }
            return false;
        }catch (NullPointerException e){
            throw new TarjetaInexistenteException();
        }
    }


}
