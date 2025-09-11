/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author samit
 */
public class ArbolBST {

    Nodo raiz;
    ArrayList<Nodo> inventario;

    public ArbolBST(Nodo raiz) {
        this.raiz = raiz;
        this.inventario = new ArrayList<>();
    }

    public void insertarGema(Nodo gema, Nodo actual) {
        if (this.raiz == null) {
            this.raiz = gema;
        } else if (gema.getPoder() > actual.getPoder()) {
            if (actual.getLd() != null) {
                insertarGema(gema, actual.getLd());
            } else {
                actual.setLd(gema);
            }
        } else if (gema.getPoder() < actual.getPoder()) {
            if (actual.getLi() != null) {
                insertarGema(gema, actual.getLi());
            } else {
                actual.setLi(gema);
            }
        } else if (gema.getPoder() == actual.getPoder()) {
            //System.out.println("¡No puedes llevar 2 gemas del mismo poder!");
            JOptionPane.showMessageDialog(null,
                    "¡No puedes llevar 2 gemas del mismo poder!",
                    "Cuidado",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public Nodo buscarRecursivo(Nodo actual, int clave) {
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

    public Nodo eliminarRecursivo(Nodo actual, int clave) {
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
            } // Caso 2: un solo hijo
            else if (actual.getLi() == null) {
                return actual.getLd();
            } else if (actual.getLd() == null) {
                return actual.getLi();
            } // Caso 3: dos hijos
            else {
                Nodo sucesor = encontrarSucesor(actual);
                actual.poder = sucesor.poder;
                actual.nombre = sucesor.nombre;
                actual.coordX = sucesor.coordX;
                actual.coordY = sucesor.coordY;
                actual.setLd(eliminarRecursivo(actual.getLd(), sucesor.getPoder()));

            }
        }
        return actual;
    }

    public ArrayList obtenerInventario() {
        ObtenerinOrden(raiz);
        return this.inventario;
    }
    
    public ArrayList<Nodo> listaInventario(Nodo actual){
        if (actual != null) {
            inventario.clear();
            ObtenerinOrden(actual.getLi());
            this.inventario.add(actual);
            ObtenerinOrden(actual.getLd());
        }
        return null;
    }

    public void ObtenerinOrden(Nodo actual) {
        if (actual != null) {
            inventario.clear();
            ObtenerinOrden(actual.getLi());
            this.inventario.add(actual);
            ObtenerinOrden(actual.getLd());
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

    public Nodo encontrarSucesor(int clave) {
        Nodo actual = raiz;
        Nodo sucesor = null;

        while (actual != null) {
            if (clave < actual.getPoder()) {
                sucesor = actual; // candidato a sucesor
                actual = actual.getLi();
            } else {
                actual = actual.getLd();
            }
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

    public Nodo encontrarPredecesor(int clave) {
        Nodo actual = raiz;
        Nodo predecesor = null;

        while (actual != null) {
            if (clave > actual.getPoder()) {
                predecesor = actual; // candidato a predecesor
                actual = actual.getLd();
            } else {
                actual = actual.getLi();
            }
        }

        return predecesor;
    }
    
    public Nodo encontrarMinimo() {
    Nodo actual = raiz;
    while (actual != null && actual.getLi() != null) {
        actual = actual.getLi();
    }
    return actual;
}

    public Nodo encontrarMaximo() {
    Nodo actual = raiz;
    while (actual != null && actual.getLd() != null) {
        actual = actual.getLd();
    }
    return actual;
}
}
