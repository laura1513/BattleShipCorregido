package Ej02_BattleShip;

import javax.swing.*;

public class VentanaHelp extends JFrame{
    private JLabel titulo;
    private JPanel panel;
    private JTextPane info;

    public VentanaHelp() {
        add(panel);
        setSize(500, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
