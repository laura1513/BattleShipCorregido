package Ej02_BattleShip;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase para los métodos que tienen que ver con el enemigo
 */
public class JugadorIA {

    /**
     * Método para crear el tablero enemigo
     * @param cells Matriz donde está el tablero
     * @param shipSize Tamaño del barco a poner
     * @param shipType Tipo de barco a poner
     * @param availableShips Mapa con la cantidad de barcos que hay que poner
     * @param idCont Contador para la identificación del barco
     * @return devuelve si ha podido introducir el barco
     */
    public static Boolean createEnemyBoard(Cell[][] cells, int shipSize, String shipType, Map<String, Integer> availableShips, AtomicInteger idCont) {
        boolean noIntroducir = true;
        int height, width, angulo, cont = 0;
        String id = shipType.substring(0, 3) + idCont.get();

        while (availableShips.get(shipType) != 0 && cont < 100) {
            angulo = (int) (Math.random() * 4);
            height = (int) (Math.random() * 10);
            width = (int) (Math.random() * 10);
            try {
                IntroducirBarcos.tryPlaceShips(angulo, cells, height, width, shipSize, shipType, availableShips.get(shipType));
            } catch (ArrayIndexOutOfBoundsException | CellOccupiedException boundsException) {
                noIntroducir = false;
            }
            if (noIntroducir) {
                id = shipType.substring(0, 3) + idCont.getAndAdd(1);
            }
            IntroducirBarcos.placeShips(noIntroducir, angulo, cells, height, width, availableShips, shipSize, shipType, id);
            cont++;
        }
        return noIntroducir;
    }

    /**
     * Metodo para disparar en el panel del jugador
     * @param cells matriz donde están los barcos del jugador
     * @param sunkedShipsP1 mapa con los barcos para hundir y comprobar si se han hundido todos
     * @return devuelve si el enemigo ha hundido todos los barcos del jugador
     */
    public static int shotACell(Cell[][] cells, Map<String, Integer> sunkedShipsP1) {
        Cell cell;
        int height, width;
        boolean attacked;
        int win = -1;
        do {
            height = (int) (Math.random() * 20);
            width = (int) (Math.random() * 20);
            cell = cells[height][width];
            attacked = cell.isAttacked();
            if (!attacked) {
                JOptionPane.showMessageDialog(null, "La IA ha disparado en la " + cell.getName());
                if (cell.getContenido().equalsIgnoreCase("agua")) {
                    cell.setBackground(Color.BLUE);
                } else {
                    cell.setBackground(Color.RED);
                    cell.setAttacked(true);
                    boolean sunked = ComprobacionesDisparos.isSunked(cells, cell, height, width, cell.getContenido());
                    if (sunked) {
                        JOptionPane.showMessageDialog(null, "Te han hundido el " + cell.getContenido());
                        sunkedShipsP1.replace(cell.getContenido(), sunkedShipsP1.get(cell.getContenido()), sunkedShipsP1.get(cell.getContenido()) -1);
                    }
                }
            }
        } while ((attacked));
        if (sunkedShipsP1.values().stream().reduce(0, Integer::sum) == 0) {
            win = JOptionPane.showConfirmDialog(null, "Ha ganado la IA", "title", JOptionPane.DEFAULT_OPTION);
        }
        return win;
    }
}
