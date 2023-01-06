package Ej02_BattleShip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class VentanaPonerBarcos extends JFrame {
    private JPanel panelDatos;
    private JTextField txtPlayerName;
    private JComboBox<String> shipList;
    private JPanel panelBarcos;
    private JLabel lblPlayerName;
    private JLabel lblShips;
    private JLabel shipSize;
    private JLabel lblShipSize;
    private JButton btnExit;
    private JLabel lblShipAvailable;
    private JLabel shipAvailable;
    private JButton help;

    private Tablero board;

    private final String[] posiciones = {"NORTH", "EAST", "SOUTH", "WEST"};

    private Map<String, Integer> availableShips;

    private String[] playerName;

    private void createUIComponents() {
        panelBarcos = board;
    }

    /**
     * Constructor de la ventana
     * @param board Tablero donde se introducirán los barcos
     * @param availableShipsP1 numero de barcos restantes por poner
     * @param playerName vector para el paso por referencia del nombre del jugador
     */
    public VentanaPonerBarcos(Tablero board, Map<String, Integer> availableShipsP1, String[] playerName) {
        availableShips = availableShipsP1;
        this.board = board;
        setSize(800, 700);
        add(panelDatos);
        this.playerName = playerName;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Hecho por Aitor Rodriguez Gallardo");


        //Listener que hace toda la introducción de los barcos en el tablero
        panelBarcos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                boolean noIntroducir = true;
                Point position = panelBarcos.getMousePosition();
                int height = (int) position.getY();
                int width = (int) position.getX();

                height /= 25;
                width /= 25;

                Cell[][] cells = board.getCells();
                if (!cells[height][width].getHayBarco() && availableShips.get((String) shipList.getSelectedItem()) != 0) {
                    int angulo = JOptionPane.showOptionDialog(null, "Como quieres poner el " + shipList.getSelectedItem(), "Poner el barco", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, posiciones, posiciones[0]);

                    if (angulo != -1) {
                        String id =  (((String) shipList.getSelectedItem()).substring(0, 3)) + availableShips.get((String) shipList.getSelectedItem());
                        try {
                            IntroducirBarcos.tryPlaceShips(angulo, cells, height, width, Integer.parseInt(shipSize.getText()), (String) shipList.getSelectedItem(), availableShips.get((String) shipList.getSelectedItem()));
                        } catch (ArrayIndexOutOfBoundsException boundsException) {
                            JOptionPane.showMessageDialog(null, boundsException.getMessage(), "Error: no cabe", JOptionPane.ERROR_MESSAGE);
                            noIntroducir = false;
                        } catch (CellOccupiedException occupiedException) {
                            JOptionPane.showMessageDialog(null, occupiedException.getMessage(), "Error: celda ocupada", JOptionPane.ERROR_MESSAGE);
                            noIntroducir = false;
                        }
                        if (noIntroducir) {
                            id =  (((String) shipList.getSelectedItem()).substring(0, 3)) + availableShips.get((String) shipList.getSelectedItem());
                        }
                        IntroducirBarcos.placeShips(noIntroducir, angulo, cells, height, width, availableShips, Integer.parseInt(shipSize.getText()), (String) shipList.getSelectedItem(), id);
                        shipAvailable.setText(String.valueOf(availableShips.get((String) shipList.getSelectedItem())));
                    }
                }

        }});

        //Listener para sincronizar los numeros sobre el tamaño y los barcos restantes con el barco seleccionado en el combo box
        shipList.addActionListener(a -> {
            switch (shipList.getSelectedIndex()) {
                case 0 -> shipSize.setText("5");
                case 1 -> shipSize.setText("4");
                case 2, 3 -> shipSize.setText("3");
                case 4 -> shipSize.setText("2");
            }
            shipAvailable.setText(String.valueOf(availableShips.get((String) shipList.getSelectedItem())));
        });

        //Listener para cerrar la ventana y devolver el nombre del jugador
        btnExit.addActionListener(e -> {
            playerName[0] = txtPlayerName.getText();
            dispose();
        });

        help.addActionListener(h -> {
            VentanaHelp vh = new VentanaHelp();
        });
    }
}
