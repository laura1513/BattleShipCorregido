package Ej02_BattleShip;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class VentanaPrincipal extends JFrame{
    private JPanel panelPrincipal;
    private JButton placeButton;
    private JButton salirButton;
    private JLabel lblTitle;
    private JButton startButton;
    private JButton btnReset;
    private Tablero player1Board = new Tablero();
    private Tablero player2Board;
    private Map<String, Integer> availableShipsP1 = new HashMap<>();
    private String[] playerName = new String[1];
    private AtomicInteger carrierId, battleshipId, destroyerId, submarineId, patrolId;

    public VentanaPrincipal()   {
        add(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(500,500));
        setLocationRelativeTo(null);
        setTitle("Hecho por Aitor Rodriguez Gallardo");

        IntroducirBarcos.setMaxShips(availableShipsP1);

        placeButton.addActionListener(a -> new VentanaPonerBarcos(player1Board, availableShipsP1, playerName).setVisible(true));

        //Listener para activar el botón de start solo cuando se han introducido todos los barcos
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (availableShipsP1.values().stream().reduce(0, Integer::sum) == 0){
                    startButton.setEnabled(true);
                }
            }
        });
        //Listener en el cual se crea el tablero enemigo
        //Se creara un tablero nuevo cada vez que se dé al botón
        startButton.addActionListener(a -> {
            player2Board = new Tablero();

            Thread tableroEnemigo = new Thread(() -> {
                Boolean ok;
                Map<String, Integer> availableShipsP2 = new HashMap<>();
                Cell[][] cells = player2Board.getCells();
                IntroducirBarcos.setMaxShips(availableShipsP2);
                carrierId = new AtomicInteger(1);
                do {
                    ok = JugadorIA.createEnemyBoard(cells, 5, "Carrier", availableShipsP2, carrierId);
                }while (!ok);
                battleshipId = new AtomicInteger(1);
                do {
                    ok = JugadorIA.createEnemyBoard(cells, 4, "Battleship", availableShipsP2, battleshipId);
                }while (!ok);
                destroyerId = new AtomicInteger(1);
                do {
                    ok = JugadorIA.createEnemyBoard(cells, 3, "Destroyer", availableShipsP2, destroyerId);
                }while (!ok);
                submarineId = new AtomicInteger(1);
                do {
                    ok = JugadorIA.createEnemyBoard(cells, 3, "Submarine", availableShipsP2, submarineId);
                }while (!ok);
                patrolId = new AtomicInteger(1);
                do {
                    ok = JugadorIA.createEnemyBoard(cells, 2, "Patrol Boat", availableShipsP2, patrolId);
                }while (!ok);
            });
            try {
                tableroEnemigo.start();
                tableroEnemigo.join();
                for (Cell[] cells : player2Board.getCells()){
                    for (Cell cell : cells) {
                        cell.setBackground(Color.WHITE);
                    }
                }
                new VentanaPartida(player1Board, player2Board, playerName[0]).setVisible(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

        salirButton.addActionListener(a -> System.exit(0));
        //Listener donde se pone por defecto el tablero del jugador
        btnReset.addActionListener(e -> {
            player1Board = new Tablero();
            startButton.setEnabled(false);
            availableShipsP1 = new HashMap<>();
            IntroducirBarcos.setMaxShips(availableShipsP1);
        });
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
