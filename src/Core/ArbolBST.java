/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

/**
 *
 * @author samit
 */
public class ArbolBST {
    Nodo raiz;

    public ArbolBST(Nodo raiz) {
        this.raiz = raiz;
    }
    
    public void insertarGema(Nodo gema, Nodo actual){
        if(this.raiz == null){
            this.raiz = gema;
           }
        else if(gema.getPoder()> actual.getPoder() ){
            if(actual.getLd() != null){
                insertarGema(gema, actual.getLd());
            }else{
            actual.setLd(gema);}
        }else if(gema.getPoder()< actual.getPoder()){
            if(actual.getLi() != null){
                insertarGema(gema, actual.getLi());
            }else{
            actual.setLi(gema);}
        }else if(gema.getPoder()== actual.getPoder()){
            System.out.println("¡No puedes llevar 2 gemas del mismo poder!");
        }
    }
    
    
    private Nodo buscarRecursivo(Nodo actual, int clave) {
        if (actual == null) {
            return null; 
        }
        if (clave == actual.getPoder()) {
            return actual;
        }
        if (clave < actual.getPoder()) {
            return buscarRecursivo(actual.getLi(), clave);
        } else {
            return buscarRecursivo(actual.getLd(), clave);
        }
    }
    
    private Nodo eliminarRecursivo(Nodo actual, int clave) {
        if (actual == null) {
            return null;
        }

        if (clave < actual.getPoder()) {
            actual.setLi(eliminarRecursivo(actual.getLi(), clave));
        } else if (clave > actual.getPoder()) {
            actual.setLd(eliminarRecursivo(actual.getLd(), clave));
        } // se encontró el que se queria borrar 
        else {
            // Caso 1: sin hijos
            if (actual.getLi() == null && actual.getLd() == null) {
                return null;
            }
            // Caso 2: un solo hijo
            else if (actual.getLi() == null) {
                return actual.getLd();
            } else if (actual.getLd() == null) {
                return actual.getLi();
            }
            // Caso 3: dos hijos
            else {
                Nodo sucesor = encontrarSucesor(actual);
                sucesor.setLi(actual.getLi());
                sucesor.setLd(actual.getLd());
                sucesor.setLr(null);
                actual = sucesor;
                eliminarRecursivo(actual.getLd(), sucesor.getPoder());
            }
        }
        return actual;
    }
    
    public void inOrden(Nodo actual) {
        if (actual != null) {
            inOrden(actual.getLi());
            System.out.print(actual.getPoder() + " ");
            inOrden(actual.getLd());
        }
    }
    
    public void preOrden(Nodo actual) {
        if (actual != null) {
            System.out.print(actual.getPoder() + " ");
            preOrden(actual.getLi());            
            preOrden(actual.getLd());
        }
    }
    
    public void postOrden(Nodo actual) {
        if (actual != null) {
            postOrden(actual.getLi());            
            postOrden(actual.getLd());
            System.out.print(actual.getPoder() + " ");
        }
    }
    
    public Nodo encontrarSucesor(Nodo nodo) {
        Nodo sucesor = nodo.getLd();
        while (sucesor.getLi() != null) {
            sucesor = sucesor.getLi();
        }
        return sucesor;
    }
    
    public Nodo encontrarPredecesor(Nodo nodo) {
        Nodo predecesor = nodo.getLi();
        while (predecesor.getLd() != null) {
            predecesor = predecesor.getLd();
        }
        return predecesor;
    }
}
