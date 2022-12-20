package Ej02_BattleShip;

import java.awt.*;
import java.util.Map;

/**
 * Clase que tiene todos los metodos sobre introducir barcos en un tablero
 */
public class IntroducirBarcos {
    /**
     * Método para comprobar si se puede meter el barco en esa posición
     * @param angulo Ángulo en el que se intentara introducir el barco
     * @param cells Matriz donde se intentara introducir el barco
     * @param height Posición en el eje Y donde se intentara introducir el barco
     * @param width Posición en el eje X donde se intentara introducir el barco
     * @param shipSize Tamaño del barco a introducir
     * @param shipType Tipo del barco a introducir
     * @param shipsAvailable Cantidad de barcos restantes de ese tipo
     * @throws CellOccupiedException Lanza esta excepción si la celda donde se intenta introducir ya tiene un barco
     * @throws ArrayIndexOutOfBoundsException Lanza esta excepción si se sale de la matriz intentando introducir el barco
     */
    public static void tryPlaceShips(int angulo, Cell[][] cells, int height, int width, int shipSize, String shipType, int shipsAvailable) throws CellOccupiedException {
        Cell cell;
        CellOccupiedException occupiedExcept = new CellOccupiedException("Ya hay un barco en esta celda");
        try {
            cell = cells[height][width];
            if (cell.getHayBarco()) {
                throw occupiedExcept;
            }
            for (int i = 1; i < shipSize; i++) {
                cell = getCell(angulo, cells, height, width, i, shipsAvailable);
                if (cell.getHayBarco()) {
                    throw occupiedExcept;
                }
            }
        } catch (ArrayIndexOutOfBoundsException outOfBoundsException) {
            throw new ArrayIndexOutOfBoundsException("El barco " + shipType + " no cabe");
        } catch (CellOccupiedException occupiedException) {
            throw new CellOccupiedException(occupiedException.getMessage());
        }
    }

    /**
     * Método donde se introducirá un barco en el tablero
     * @param placeable Booleano para saber si el barco se puede introducir
     * @param angulo Ángulo en el que se intentara introducir el barco
     * @param cells Matriz donde se intentara introducir el barco
     * @param height Posición en el eje Y donde se intentara introducir el barco
     * @param width Posición en el eje X donde se intentara introducir el barco
     * @param availableShips Mapa con los barcos restantes por introducir
     * @param shipSize Tamaño del barco a introducir
     * @param shipType Tipo del barco a introducir
     * @param id Identificador del barco
     */
    public static void placeShips(boolean placeable, int angulo, Cell[][] cells, int height, int width, Map<String, Integer> availableShips, int shipSize, String shipType, String id) {
        if (placeable && availableShips.get(shipType) != 0) {
            Cell cell = cells[height][width];
            cell.setBarco(shipType);
            cell.setId(id);
            cell.setBackground(Color.gray);
            for (int i = 1; i < shipSize; i++) {
                cell = getCell(angulo, cells, height, width, i, availableShips.get(shipType));
                cell.setBarco(shipType);
                cell.setId(id);
                cell.setBackground(Color.GRAY);
            }
            availableShips.replace(shipType, availableShips.get(shipType), availableShips.get(shipType) - 1);
        }
    }

    /**
     * Método para rellenar el mapa con los barcos disponibles
     * @param availableShips Mapa a rellenar
     */
    public static void setMaxShips(Map<String, Integer> availableShips) {
        availableShips.put("Carrier", 1);
        availableShips.put("Battleship", 2);
        availableShips.put("Destroyer", 3);
        availableShips.put("Submarine", 4);
        availableShips.put("Patrol Boat", 5);
    }

    /**
     * Método para coger una celda de la matriz basado en un ángulo
     * @param angulo Ángulo en el que se intentara introducir el barco
     * @param cells Matriz donde se intentara introducir el barco
     * @param height Posición en el eje Y donde se intentara introducir el barco
     * @param width Posición en el eje X donde se intentara introducir el barco
     * @param offset Número que se le restara basado en que parte del barco se esté introduciendo
     * @param shipsAvailable Número de barcos restantes por introducir (En caso de ser 0 se devolverá una celda nula)
     * @return Devuelve la celda encontrada en esa posición
     * @throws ArrayIndexOutOfBoundsException Puede llegar a lanzar esta excepción si intenta coger una celda más alla de su tamaño
     */
    public static Cell getCell(int angulo, Cell[][] cells, int height, int width, int offset, int shipsAvailable) {
        Cell cell = null;
        int positionY, positionX;
        if (shipsAvailable != 0) {
            switch (angulo) {
                case 0 -> {
                    positionY = height - offset;
                    positionX = width;
                    cell = cells[positionY][positionX];
                }
                case 1 -> {
                    positionY = height;
                    positionX = width + offset;
                    cell = cells[positionY][positionX];
                }
                case 2 -> {
                    positionY = height + offset;
                    positionX = width;
                    cell = cells[positionY][positionX];
                }
                case 3 -> {
                    positionY = height;
                    positionX = width - offset;
                    cell = cells[positionY][positionX];
                }
            }
        }
        return cell;
    }
}
