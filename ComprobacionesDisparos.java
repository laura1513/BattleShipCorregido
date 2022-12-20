package Ej02_BattleShip;

/**
 * Clase con métodos sobre los disparos y las comprobaciones del hundimiento de barcos
 */
public class ComprobacionesDisparos {

    /**
     * Método para saber si un barco ha sido hundido
     * @param cells Matriz donde se intentara introducir el barco
     * @param cell Celda la cual se quiere comprobar
     * @param height Posición en el eje Y donde se intentara introducir el barco
     * @param width Posición en el eje X donde se intentara introducir el barco
     * @param shipType Tipo del barco a introducir
     * @return Devuelve si el barco ha sido hundido
     */
    public static boolean isSunked(Cell[][] cells, Cell cell, int height, int width, String shipType) {
        boolean[] checkSunked = null;
        boolean isSunked = true;
        int searchShip = 1, angleSearch;
        switch (shipType) {
            case "Carrier" -> checkSunked = new boolean[5];
            case "Battleship" -> checkSunked = new boolean[4];
            case "Destroyer", "Submarine" -> checkSunked = new boolean[3];
            case "Patrol Boat" -> checkSunked = new boolean[2];
        }
        String shipId = cell.getId();
        angleSearch = getAngleSearch(cells, height, width, shipId);
        checkSunked[0] = true;

        boolean outOfBounds = false;
        while (searchShip < checkSunked.length && !outOfBounds) {
            switch (angleSearch) {
                case 0 -> outOfBounds = (height - searchShip) < 0;
                case 1 -> outOfBounds = (width + searchShip) > 9;
                case 2 -> outOfBounds = (height + searchShip) > 9;
                case 3 -> outOfBounds = (width - searchShip) < 0;
            }
            if (!outOfBounds){
                Cell cellLoop = IntroducirBarcos.getCell(angleSearch, cells, height, width, searchShip, 1);
                checkSunked[searchShip] = cellLoop.isAttacked() && cellLoop.getId().equals(shipId);
                searchShip++;
            }
        }
        if (outOfBounds) {
            int angleSearchAgain = -1;
            switch (angleSearch) {
                case 0 -> angleSearchAgain = getAngleSearch(cells, height - (searchShip - 1), width, shipId);
                case 1 -> angleSearchAgain = getAngleSearch(cells, height, width + (searchShip - 1), shipId);
                case 2 -> angleSearchAgain = getAngleSearch(cells, height + (searchShip - 1), width, shipId);
                case 3 -> angleSearchAgain = getAngleSearch(cells, height, width - (searchShip - 1), shipId);
            }
            checkSunked[0] = true;
            searchShip = 1;
            while (searchShip < checkSunked.length) {
                Cell cellLoop = IntroducirBarcos.getCell(angleSearchAgain, cells, height, width, searchShip, 1);
                checkSunked[searchShip] = cellLoop.isAttacked() && cellLoop.getId().equals(shipId);
                searchShip++;
            }
        }

        for (boolean b : checkSunked) {
            if (!b) {
                isSunked = false;
            }
        }

        return isSunked;
    }

    /**
     * Método para saber en qué ángulo buscar las demás partes del barco
     * @param cells Matriz donde se intentara introducir el barco
     * @param height Posición en el eje Y donde se intentara introducir el barco
     * @param width Posición en el eje X donde se intentara introducir el barco
     * @param shipId Identificador del barco a buscar
     * @return devuelve el ángulo en el que hay que buscar
     */
    public static int getAngleSearch(Cell[][] cells, int height, int width, String shipId) {
        int angleSearch = -1;
        Cell cell;
        if (height > 0) {
            cell = cells[height - 1][width];
            if (cell.getId().equals(shipId)) {
                angleSearch = 0;
            }
        }
        if (width < 9) {
            cell = cells[height][width + 1];
            if (cell.getId().equals(shipId)) {
                angleSearch = 1;
            }
        }
        if (height < 9) {
            cell = cells[height + 1][width];
            if (cell.getId().equals(shipId)) {
                angleSearch = 2;
            }
        }
        if (width > 0) {
            cell = cells[height][width - 1];
            if (cell.getId().equals(shipId)) {
                angleSearch = 3;
            }
        }
        return angleSearch;
    }
}
