package Ej02_BattleShip;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Clase que extiende de JPanel para crear un tablero de battleship
 */
public class Tablero extends JPanel{



    private Cell[][] cells;

    public Tablero() {
        innitComponents();
    }

    /**
     * Método para inicializar todos los componentes del tablero
     */
    private void innitComponents() {
        setLayout(new GridLayout(20, 20, 0, 0));
        setSize(new Dimension(500, 500));
        cells = new Cell[20][20];
        char[] row = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T'};

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                Cell label = new Cell("celda"+row[i]+(j+1));
                label.setSize(new Dimension(50, 50));
                label.setBorder(new LineBorder(Color.BLACK));
                label.setOpaque(true);
                label.setBackground(Color.WHITE);

                cells[i][j] = label;
            }
        }

        for (Cell[] cell : cells) {
            for (int j = 0; j < cells[0].length; j++) {
                add(cell[j]);
            }
        }
        //setSize(new Dimension(500, 550));


    }

    /**
     * Método para obtener la matriz de celdas
     * @return Devuelve la matriz de celdas
     */
    public Cell[][] getCells() {
        return cells;
    }


}