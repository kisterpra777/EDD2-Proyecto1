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
            System.out.println("El jefe recibe la gema exacta: " + gemaBuscada.getNombre());
            arbol.eliminarRecursivo(gemaBuscada, gemaBuscada.getPoder());
            return gemaBuscada;

        } else {

            Nodo sucesor = this.arbol.encontrarSucesor(gemaPedir);
            Nodo predecesor = this.arbol.encontrarPredecesor(gemaPedir);

            if (sucesor != null) {
                System.out.println("No está la gema exacta. Se entrega el sucesor: " + sucesor.getNombre());
                arbol.eliminarRecursivo(sucesor, sucesor.getPoder());
                return sucesor;
            } else if (predecesor != null) {
                System.out.println("No está la gema exacta. Se entrega el predecesor: " + predecesor.getNombre());
                arbol.eliminarRecursivo(predecesor, predecesor.getPoder());
                return predecesor;
            } else {
                System.out.println("No hay gemas para entregar.");
                return null; // El árbol está vacío
            }
        }

    }

    public void cofre() {

        Nodo minimo = arbol.encontrarMinimo();
        if (minimo != null) {
            System.out.println("El cofre se abre con la gema mínima: " + minimo.getNombre());
        } else {
            System.out.println("El cofre no se puede abrir. No hay gemas.");
        }
        arbol.eliminarRecursivo(minimo, minimo.getPoder());

    }

    public void abrirPortal() {
        
        Nodo maximo = arbol.encontrarMaximo();
        if (maximo != null) {
            System.out.println("El portal se abre con la gema maxima: " + maximo.getNombre());
        } else {
            System.out.println("El portal no se puede abrir. No hay gemas.");
        }
        arbol.eliminarRecursivo(maximo, maximo.getPoder());

    }

    public void trampa() {

        if (arbol.raiz == null) {
            System.out.println("No hay gemas que perder.");
            return;
        }

        ArrayList<Nodo> inventario = this.arbol.obtenerInventario();
        int index = random.nextInt(inventario.size());
        Nodo gemaAEliminar = inventario.get(index);
        arbol.eliminarRecursivo(gemaAEliminar, gemaAEliminar.getPoder());
        System.out.println("El jugador cayó en una trampa y perdió la gema: " + gemaAEliminar.getNombre());


    }

}
