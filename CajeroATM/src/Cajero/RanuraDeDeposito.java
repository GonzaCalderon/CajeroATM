package Cajero;

import java.util.ArrayList;
import java.util.List;

public class RanuraDeDeposito {
    private List<Billete> billetesDepositados;
    private Double dolaresDepositados;
    public  List<Billete> depositarPesos(int billetesDe100_cantidad, int billetesDe500_cantidad, int billetesDe1000_cantidad ){
        billetesDepositados = new ArrayList<>();

        billetesDepositados.add(new Billete(100,billetesDe100_cantidad));
        billetesDepositados.add(new Billete(500, billetesDe500_cantidad));
        billetesDepositados.add(new Billete(1000, billetesDe1000_cantidad));

        return billetesDepositados;
    }

    public double depositarDolares(double cantidad){
        dolaresDepositados = cantidad;
        return cantidad;
    }
}
