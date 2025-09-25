/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

/**
 *
 * @author samit
 */
public class Nodo {

    //Clase nodo que representa a las gemas
    int poder;
    String nombre;
    int coordX;
    int coordY;

    Nodo Li;
    Nodo Ld;
    Nodo Lr;

    
    public Nodo(int poder, String nombre, int coordX, int coordY) {
        this.poder = poder;
        this.nombre = nombre;
        this.coordX = coordX;
        this.coordY = coordY;
    }

        //Submetodos basicos de Nodo

    public void setLi(Nodo Li) {
        this.Li = Li;
        if (Ld != null) {
            Ld.setLr(this);
        }
    }

    public void setLd(Nodo Ld) {
        this.Ld = Ld;
        if (Ld != null) {
            Ld.setLr(this);
        }
    }

    public void setLr(Nodo Lr) {
        this.Lr = Lr;
    }

    public Nodo getLr() {
        return Lr;
    }

    public int getPoder() {
        return poder;
    }

    public String getNombre() {
        return nombre;
    }

    public Nodo getLi() {
        return Li;
    }

    public Nodo getLd() {
        return Ld;
    }

}
