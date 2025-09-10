/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import static Core.Juego.EnumEventos.COFRES;
import static Core.Juego.EnumEventos.GEMA;
import static Core.Juego.EnumEventos.JEFEENEMIGO;
import static Core.Juego.EnumEventos.NADAXD;
import static Core.Juego.EnumEventos.TRAMPA;
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
        GEMA, JEFEENEMIGO, COFRES, PORTAL, TRAMPA, NADAXD
    }

    public void llamadaEvento(){
        EnumEventos evento;
        float i = (float) Math.random();
        
        if(i<=0.3){
           evento = GEMA;
        }else if(i<=0.5){
            evento = JEFEENEMIGO;
        }else if(i<=0.6){
            evento = COFRES;
        }else if(i<=0.7){
            evento = TRAMPA;
        }else{
            evento = NADAXD;
        }
        
        ejecutarEvento(evento);  
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
            case NADAXD:
                nada();
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

    public void nada(){
        System.out.println("No hay nada aquí, busca en otro lado :p");
    }
    
    public void obtenerInventario(){
        arbol.obtenerInventario();
    }
}
