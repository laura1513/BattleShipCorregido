package Ej02_BattleShip;

import javax.swing.*;

/**
 * Clase para personalizar una JLabel para el tablero del juego
 */
public class Cell extends JLabel {
    private boolean hayBarco;
    private String contenido;
    private boolean attacked;
    private String id;

    /**
     * Constructor de la celda
     * @param name El nombre de la celda
     */
    public Cell(String name){
        setName(name);
        this.hayBarco = false;
        this.contenido = "Agua";
        this.attacked = false;
        this.id = "Agua";
    }

    /**
     * Setter para poner un barco dentro de la celda
     * @param barco Nombre del tipo de barco
     */
    public void setBarco(String barco) {
        this.hayBarco = true;
        this.contenido = barco;
    }

    /**
     * Getter para conseguir que tiene dentro la celda
     * @return Devuelve el contenido de la celda
     */
    public String getContenido() {
        return this.contenido;
    }

    /**
     * Getter para saber si la celda contiene un barco
     * @return Devuelve si contiene un barco
     */
    public boolean getHayBarco() {
        return hayBarco;
    }

    /**
     * Getter para saber si ya se ha atacado a esta celda
     * @return Devuelve si la celda ha sido atacada
     */
    public boolean isAttacked() {
        return attacked;
    }

    /**
     * Setter para introducir que la celda ha sido atacada
     * @param attacked Booleano para saber si la celda a sido atacada
     */
    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    /**
     * Getter para conseguir la identificación del barco en la celda
     * @return Devuelve la identificación del barco en la celda
     */
    public String getId() {
        return id;
    }

    /**
     * Setter para introducir la identificación del barco en la celda
     * @param id Identificación del barco en la celda
     */
    public void setId(String id) {
        this.id = id;
    }
}
