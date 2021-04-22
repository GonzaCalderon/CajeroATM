package Cajero;


import javax.swing.*;
import java.awt.*;

public class Pantalla extends JOptionPane{

    public void mostrarMensaje(String mensaje){
        try{

            JOptionPane.showConfirmDialog(null,
                    pantallaPersonalizada(mensaje),
                    "Cajero automatico",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
        }catch (NumberFormatException e){
            throw new SesionFinalizadaException();
        }

    }
    public  void mostrarError(String error) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        try{

            JOptionPane.showConfirmDialog(null,
                    pantallaPersonalizada(error),
                    "ERROR",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE);
        }catch (NumberFormatException e){
            throw new SesionFinalizadaException();
        }
    }

    private JPanel pantallaPersonalizada(String mensaje) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(mensaje);
        ImageIcon image = null;
        image = new ImageIcon("imagenDePantalla.png");

        label.setIcon(image);
        panel.add(label);

        return panel;
    }

}
