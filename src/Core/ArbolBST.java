/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Clase que implementa un Árbol Binario de Búsqueda (BST). Permite insertar,
 * eliminar, buscar nodos y recorrer el árbol en diferentes órdenes. Además
 * mantiene un inventario en forma de lista.
 *
 * @author samit
 */
public class ArbolBST {

    // Nodo raíz del árbol
    Nodo raiz;

    // Lista usada como inventario (almacena los nodos en recorrido inOrden)
    ArrayList<Nodo> inventario;

    public ArbolBST(Nodo raiz) {
        this.raiz = raiz;
        this.inventario = new ArrayList<>();
    }

    //Elimina un nodo del árbol a partir de su clave (poder).
    // valor del poder del nodo a eliminar
    public void eliminar(int clave) {
        this.raiz = eliminarRecursivo(this.raiz, clave);
    }

    // Inserta una gema (nodo) en el árbol de forma recursiva.
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
            // No se permiten gemas con el mismo poder
            JOptionPane.showMessageDialog(null,
                    "¡No puedes llevar 2 gemas del mismo poder!",
                    "Cuidado",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * @return nodo raíz del árbol
     */
    public Nodo getRaiz() {
        return raiz;
    }

    /**
     * Busca un nodo en el árbol de forma recursiva. retorna nodo encontrado o
     * null si no existe
     */
    
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

    /**
     * Elimina un nodo de forma recursiva. Considera tres casos: sin hijos, un
     * hijo, o dos hijos.
     *
     * retorna nuevo subárbol tras la eliminación
     */
    public Nodo eliminarRecursivo(Nodo actual, int clave) {
        if (actual == null) {
            return null;
        }

        if (clave < actual.getPoder()) {
            actual.setLi(eliminarRecursivo(actual.getLi(), clave));
        } else if (clave > actual.getPoder()) {
            actual.setLd(eliminarRecursivo(actual.getLd(), clave));
        } // Nodo encontrado
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
                // Reemplaza el contenido con el del sucesor
                actual.poder = sucesor.poder;
                actual.nombre = sucesor.nombre;
                actual.coordX = sucesor.coordX;
                actual.coordY = sucesor.coordY;
                // Elimina el sucesor del subárbol derecho
                actual.setLd(eliminarRecursivo(actual.getLd(), sucesor.getPoder()));
            }
        }
        return actual;
    }

    /**
     * Obtiene la lista de nodos en recorrido inOrden a partir de un nodo dado.
     * return null (se utiliza inventario como estructura global)
     */
    public ArrayList obtenerInventario() {
        inventario.clear();
        ObtenerinOrden(raiz);
        return this.inventario;
    }

    public ArrayList<Nodo> listaInventario(Nodo actual) {
        if (actual != null) {
            inventario.clear();
            ObtenerinOrden(actual.getLi());
            this.inventario.add(actual);
            ObtenerinOrden(actual.getLd());
        }
        return null;
    }

    // Se implementan los 3 recorridos
    public void ObtenerinOrden(Nodo actual) {
        if (actual != null) {
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

    /**
     * Encuentra el sucesor (mínimo del subárbol derecho). nodo de referencia
     * retorna nodo sucesor
     */
    public Nodo encontrarSucesor(Nodo nodo) {
        Nodo sucesor = nodo.getLd();
        while (sucesor.getLi() != null) {
            sucesor = sucesor.getLi();
        }
        return sucesor;
    }

    /**
     * Encuentra el sucesor de un valor (nodo más pequeño mayor que la clave).
     * valor a buscar retorna nodo sucesor
     */
    public Nodo encontrarSucesor(int clave) {
        Nodo actual = raiz;
        Nodo sucesor = null;

        while (actual != null) {
            if (clave < actual.getPoder()) {
                sucesor = actual; // posible sucesor
                actual = actual.getLi();
            } else {
                actual = actual.getLd();
            }
        }
        return sucesor;
    }

    /**
     * Encuentra el predecesor (máximo del subárbol izquierdo). nodo de
     * referencia returorna nodo predecesor
     */
    public Nodo encontrarPredecesor(Nodo nodo) {
        Nodo predecesor = nodo.getLi();
        while (predecesor.getLd() != null) {
            predecesor = predecesor.getLd();
        }
        return predecesor;
    }

    /**
     * Encuentra el predecesor de un valor (nodo más grande menor que la clave).
     * clave valor a buscar retorna nodo predecesor
     */
    public Nodo encontrarPredecesor(int clave) {
        Nodo actual = raiz;
        Nodo predecesor = null;

        while (actual != null) {
            if (clave > actual.getPoder()) {
                predecesor = actual; // posible predecesor
                actual = actual.getLd();
            } else {
                actual = actual.getLi();
            }
        }
        return predecesor;
    }

    /**
     * Encuentra el nodo con el valor mínimo del árbol.
     *
     * @return nodo mínimo
     */
    public Nodo encontrarMinimo() {
        Nodo actual = raiz;
        while (actual != null && actual.getLi() != null) {
            actual = actual.getLi();
        }
        return actual;
    }

    /**
     * Encuentra el nodo con el valor máximo del árbol. retorna nodo máximo
     */
    public Nodo encontrarMaximo() {
        Nodo actual = raiz;
        while (actual != null && actual.getLd() != null) {
            actual = actual.getLd();
        }
        return actual;
    }
}
