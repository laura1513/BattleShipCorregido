package Ej02_BattleShip;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class VentanaPartida extends JFrame {
    private JPanel panelPrincipal;
    private JLabel playerName;
    private JLabel lblAttack;
    private JLabel lblMyShips;
    private JLabel numberTurns;
    private JLabel lblTurns;
    private JLabel lblPlayerName;
    private JPanel panelAttack;
    private JPanel panelMyShips;
    private JButton btnAttack;
    private JTable showSunkedShipsP2;
    private Tablero p1Board;
    private Tablero p2Board;
    private int turns;
    private Cell cell;
    private int height;
    private int width;

    private Map<String, Integer> sunkedShipsP1;

    private Map<String, Integer> sunkedShipsP2;

    private DefaultTableModel dtm;

    /**
     * Constructor para la ventana donde transcurrirá la partida
     * @param p1Board Tablero del jugador donde la IA efectuara los disparos
     * @param p2Board Tablero del enemigo donde se efectuaran los disparos
     * @param playerName Nombre del jugador
     */
    public VentanaPartida(Tablero p1Board, Tablero p2Board, String playerName) {
        this.p1Board = p1Board;
        this.p2Board = p2Board;
        turns = 0;
        setSize(new Dimension(1030, 800));
        add(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        this.playerName.setText(playerName);
        p1Board.removeMouseListener(p1Board.getMouseListeners()[0]);
        setTitle("Hecho por Aitor Rodriguez Gallardo");

        IntroducirBarcos.setMaxShips(sunkedShipsP1);
        IntroducirBarcos.setMaxShips(sunkedShipsP2);

        // Listener para seleccionar a que celda se va a disparar
        panelAttack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point position = panelAttack.getMousePosition();
                height = (int) position.getY();
                width = (int) position.getX();

                height /= 25;
                width /= 25;

                Cell[][] cells = p2Board.getCells();

                cell = cells[height][width];

                //cell.setBorder(new LineBorder(Color.GREEN));
                btnAttack.setEnabled(true);
            }
        });

        //Listener para efectuar el disparo además de comprobar si el barco se hundió o si ya se hundieron todos los barcos
        btnAttack.addActionListener(a -> {
            //cell.setBorder(new LineBorder(Color.BLACK));
            if (!cell.isAttacked()) {
                if (cell.getContenido().equalsIgnoreCase("agua")) {
                    cell.setBackground(Color.BLUE);
                } else {
                    cell.setBackground(Color.RED);
                    cell.setAttacked(true);
                    boolean sunked = ComprobacionesDisparos.isSunked(p2Board.getCells(), cell, height, width, cell.getContenido());
                    if (sunked) {
                        JOptionPane.showMessageDialog(null, "Se ha hundido el " + cell.getContenido());
                        sunkedShipsP2.replace(cell.getContenido(), sunkedShipsP2.get(cell.getContenido()), sunkedShipsP2.get(cell.getContenido()) - 1);
                        dtm.setValueAt(sunkedShipsP2.get(cell.getContenido()), getRowBasedOnShipType(cell.getContenido()), 2);
                    }
                }
                btnAttack.setEnabled(false);
                int winP1 = -1;
                turns++;
                numberTurns.setText(String.valueOf(turns));
                if (sunkedShipsP2.values().stream().reduce(0, Integer::sum) == 0) {
                    winP1 = JOptionPane.showConfirmDialog(null, "Ha ganado " + playerName, "title", JOptionPane.DEFAULT_OPTION);
                }
                int winP2 = JugadorIA.shotACell(p1Board.getCells(), sunkedShipsP1);
                if (winP2 == JOptionPane.YES_OPTION || winP1 == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }

        });


    }

    private void createUIComponents() {
        Object[][] tableData = {{"Carrier", 5, 1},{"Battleship", 4, 2},{"Destroyer", 3, 3},{"Submarine", 3, 4}, {"Patrol Boat", 2, 5}};
        String[] columnName = {"Ship Name", "Ship Size", "Afloat"};
        dtm = new DefaultTableModel(tableData, columnName);
        panelMyShips = p1Board;
        panelAttack = p2Board;
        sunkedShipsP1 = new HashMap<>();
        sunkedShipsP2 = new HashMap<>();
        showSunkedShipsP2 = new JTable(dtm);
    }

    private int getRowBasedOnShipType(String shipType){
        int row = -1;
        switch (shipType) {
            case "Carrier" -> row = 0;
            case "Battleship" -> row = 1;
            case "Destroyer" -> row = 2;
            case "Submarine" -> row = 3;
            case "Patrol Boat" -> row = 4;
        }

        return row;
    }
}
