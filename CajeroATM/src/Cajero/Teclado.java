package Cajero;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


public class Teclado {
    BufferedReader lector;
    String unaLinea;

    public Teclado()  {


    }
    public int llamarOpciones(String mensaje, List<String> opciones) {
        try{

            int seleccion = JOptionPane.showOptionDialog(null, tecladoPersonalizado(mensaje), "ATM",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opciones.toArray(), opciones.toArray()[0]);
            return seleccion;
        }catch (NumberFormatException e){
            throw new ValorIngresadoNoValidoException();
        }

    }
    public int llamarOpciones(String mensaje, String[] opciones) throws IOException {
        try{

            return JOptionPane.showOptionDialog(null, tecladoPersonalizado(mensaje), "ATM",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones);
        }catch (NumberFormatException e){
            throw new ValorIngresadoNoValidoException();
        }

    }
    public int llamarTecladoNumerico(String mensaje) throws IOException {
        try{

            return Integer.parseInt(JOptionPane.showInputDialog(null, tecladoPersonalizado(mensaje), "ATM", 1));
        }catch (NumberFormatException e){
            throw new ValorIngresadoNoValidoException();
        }

    }
    public String llamarTecladoAlfanumerico(String mensaje) {
        try {
            unaLinea = JOptionPane.showInputDialog(null,
                    tecladoPersonalizado(mensaje),
                    "ATM",
                    JOptionPane.PLAIN_MESSAGE);
        }catch (NumberFormatException e){
            throw new ValorIngresadoNoValidoException();
        }
        return  unaLinea;
    }
    private JPanel tecladoPersonalizado(String mensaje) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(mensaje);
        ImageIcon image = null;
        image = new ImageIcon("imagenDePantalla.png");

        label.setIcon(image);
        panel.add(label);

        return panel;
    }





}
