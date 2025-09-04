/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.ArrayList;
import java.util.Random;

public class Juego {

    private Random random;
    private ArbolBST arbol;

    public Juego() {
        this.random = new Random();
        this.arbol = new ArbolBST(null);
    }

    public enum EnumEventos {
        JEFEENEMIGO, COFRES, PORTAL, TRAMPA
    }

    public void ejecutarEvento(EnumEventos evento) {
        switch (evento) {
            case JEFEENEMIGO:
                jefeEnemigo(random.nextInt(100));
            case COFRES:
                cofre();
            case PORTAL:
                abrirPortal();
            case TRAMPA:
                trampa();

        }
    }

    public Nodo jefeEnemigo(int gemaPedir) {
        Nodo raiz = arbol.raiz;
        Nodo gemaBuscada = this.arbol.buscarRecursivo(raiz, gemaPedir);

        if (gemaBuscada != null) {
            return gemaBuscada;
        } else {
            
            Nodo sucesor = this.arbol.encontrarSucesor(gemaPedir);
            Nodo predecesor = this.arbol.encontrarPredecesor(gemaPedir);

            if (sucesor != null) {
                return sucesor;
            } else if (predecesor != null) {
                return predecesor;
            } else {
                return null; // El árbol está vacío
            }
        }

    }

    public void cofre() {
        

        
    }

    public void abrirPortal() {

    }

    public void trampa() {

     ArrayList<Nodo> inventario = this.arbol.obtenerInventario();
     int index = random.nextInt(inventario.size());
     Nodo gemaAEliminar = inventario.get(index);
     arbol.eliminarRecursivo(gemaAEliminar, gemaAEliminar.getPoder());
               
    }

}
