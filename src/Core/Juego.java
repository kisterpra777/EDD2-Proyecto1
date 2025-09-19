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
import Resources.Sonido;
    import java.util.ArrayList;
    import java.util.Random;
    import javax.swing.JOptionPane;

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

        public enum Lugar {
            VALLE("valle"),
            ALTOS("altos"),
            RUINA("ruina"),
            SENDERO("sendero"),
            PUEBLO("pueblo"),
            ARROYO("arroyo"),
            RIO("río"),
            TABERNA("taberna"),
            PUENTE("puente"),
            PUERTO("puerto"),
            INFIERNO("infierno");

            private final String nombre;

            Lugar(String name) {
                this.nombre = name;
            }

            public String getNombre() {
                return nombre;
            }
        }

        public enum NombreLugar {
            SOLEDAD("Soledad 2k"),
            MALAMBO("Malambo city"),
            MIXWELL("Masgüel"),
            MAURO("Mauri"),
            CELESTIA("Celestia"),
            REBOLO("Rebolo town"),
            COORDIALIDAD("la Coordialidad"),
            MOCHA("Mocha city"),
            J("Bloque J");

            private final String nombre;

            NombreLugar(String name) {
                this.nombre = name;
            }

            public String getNombre() {
                return nombre;
            }
        }

        public void llamadaEvento(int posX, int posY) {
            EnumEventos evento;
            float i = (float) Math.random();


                if (i <= 0.3) {
                    evento = GEMA;
                } else if (i <= 0.6) {
                    evento = NADAXD;
                } else if (i <= 0.8) {
                    evento = JEFEENEMIGO;
                } else if (i <= 0.9) {
                    evento = COFRES;
                } else {
                    evento = TRAMPA;
                }


            ejecutarEvento(evento, posX, posY);
        }

        public void ejecutarEvento(EnumEventos evento, int posX, int posY) {
            switch (evento) {
                case GEMA:
                    gema(posX,posY);
                    break;
                case JEFEENEMIGO:
                    jefeEnemigo(random.nextInt(100));
                    break;
                case COFRES:
                    cofre();
                    break;
                case PORTAL:
                    abrirPortal();
                    break;
                case TRAMPA:
                    trampa();
                    break;
                case NADAXD:
                    nada();
                    break;
            }
        }

        public void gema(int posX,int posY) {
            NombreLugar[] nombresLugar = NombreLugar.values();
            Lugar[] lugar = Lugar.values();
            int i = random.nextInt(nombresLugar.length);
            int j = random.nextInt(lugar.length);
            Sonido.reproducir("src/Resources/gema.wav");
            JOptionPane.showMessageDialog(null,
                    "¡Conseguiste la gema de " + lugar[j].getNombre() + " de " + nombresLugar[i].getNombre() + "!",
                    "Gema encontrada",
                    JOptionPane.INFORMATION_MESSAGE);
                    

            arbol.insertarGema(new Nodo(random.nextInt(100),
                    "Gema de " + lugar[j].getNombre() + " de " + nombresLugar[i].getNombre(),
                    posX+i*10, posY+j*10),
                    arbol.getRaiz());

        }

        public Nodo jefeEnemigo(int gemaPedir) {
            Nodo raiz = arbol.raiz;
            Nodo gemaBuscada = this.arbol.buscarRecursivo(raiz, gemaPedir);
            Sonido.reproducir("src/Resources/enemy.wav");
            if (gemaBuscada != null) {                
                //System.out.println("El jefe recibe la gema exacta: " + gemaBuscada.getNombre());
                JOptionPane.showMessageDialog(null,
                        "El jefe recibe la gema exacta: " + gemaBuscada.getNombre(),
                        "Cuidado",
                        JOptionPane.WARNING_MESSAGE);
                arbol.eliminar(gemaBuscada.getPoder());
                return gemaBuscada;

            } else {

                Nodo sucesor = this.arbol.encontrarSucesor(gemaPedir);
                Nodo predecesor = this.arbol.encontrarPredecesor(gemaPedir);

                if (sucesor != null) {
                    //System.out.println("No está la gema exacta. Se entrega el sucesor: " + sucesor.getNombre());
                    JOptionPane.showMessageDialog(null,
                            "El jefe espera la gema con poder "+ gemaPedir +". Se entrega el sucesor: " + sucesor.getNombre(),
                            "Jefe",
                            JOptionPane.INFORMATION_MESSAGE);
                    arbol.eliminar(sucesor.getPoder());
                    return sucesor;
                } else if (predecesor != null) {
                    //System.out.println("No está la gema exacta. Se entrega el predecesor: " + predecesor.getNombre());
                    JOptionPane.showMessageDialog(null,
                            "El jefe espera la gema con poder "+ gemaPedir +" Se entrega el predecesor: " + predecesor.getNombre(),
                            "Jefe",
                            JOptionPane.INFORMATION_MESSAGE);
                    arbol.eliminar(predecesor.getPoder());
                    return predecesor;
                } else {
                    //System.out.println("No hay gemas para entregar.");
                    JOptionPane.showMessageDialog(null,
                            "No hay gemas que entregar al jefe.",
                            "Jefe",
                            JOptionPane.WARNING_MESSAGE);
                            Sonido.reproducir("src/Resources/GameOver.wav");
                    return null; // El árbol está vacío
                }
                
            }

        }

        public void cofre() {
            

            Nodo minimo = arbol.encontrarMinimo();
            if (minimo != null) {
                Sonido.reproducir("src/Resources/cofre.wav");
                JOptionPane.showMessageDialog(null,
                        "El cofre se abre con la gema mínima: " + minimo.getNombre(),
                        "Cofre",
                        JOptionPane.INFORMATION_MESSAGE);
                        arbol.eliminar(minimo.getPoder());
            } else {
                Sonido.reproducir("src/Resources/GameOver.wav");
                JOptionPane.showMessageDialog(null,
                        "El cofre no se puede abrir. No hay gemas.",
                        "Cofre",
                        JOptionPane.WARNING_MESSAGE);
                       
            }
            

        }

        public boolean abrirPortal() {

            Nodo maximo = arbol.encontrarMaximo();
            if (maximo != null) {
                //System.out.println("El portal se abre con la gema maxima: " + maximo.getNombre());
                JOptionPane.showMessageDialog(null,
                        "El portal se abre con la gema maxima: " + maximo.getNombre(),
                        "Portal",
                        JOptionPane.INFORMATION_MESSAGE);
                        arbol.eliminar(maximo.getPoder());
                        return true;


            } else {
                //System.out.println("El portal no se puede abrir. No hay gemas.");
                JOptionPane.showMessageDialog(null,
                        "El portal no se puede abrir. No hay gemas.",
                        "Portal",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }




        }

        public void trampa() {

            if (arbol.raiz == null) {                
                Sonido.reproducir("src/Resources/GameOver.wav");
                JOptionPane.showMessageDialog(null,
                        "No hay gemas que perder.",
                        "Trampa",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            ArrayList<Nodo> inventario = this.arbol.obtenerInventario();
            int index = random.nextInt(inventario.size());
            Nodo gemaAEliminar = inventario.get(index);
            arbol.eliminar(gemaAEliminar.getPoder());
            //System.out.println("El jugador cayó en una trampa y perdió la gema: " + gemaAEliminar.getNombre());
            JOptionPane.showMessageDialog(null,
                    "El jugador cayó en una trampa y perdió la gema: " + gemaAEliminar.getNombre(),
                    "Trampa",
                    JOptionPane.WARNING_MESSAGE);

        }

        public void nada() {
            Sonido.reproducir("src/Resources/nada.wav");
            JOptionPane.showMessageDialog(null,
                    "No hay nada aquí, busca en otro lado :p",
                    "No pasó nada",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        public ArrayList<Nodo> obtenerInventario() {
            return arbol.obtenerInventario();
        }
    }
