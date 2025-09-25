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

        // Lugares de donde son las gemas
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

            //Metodos para darle nombres a las gemas
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

        //Metodo para llamar a los metodos especificos de los eventos del juego
        public Nodo llamadaEvento(int posX, int posY) {
            EnumEventos evento;
            float i = (float) Math.random();

                //Se utiliza la i para controlar la probabilidad de que ocurra cada evento
                if (i <= 0.4) {
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


            return ejecutarEvento(evento, posX, posY);
            
        }
        
        // Switch para manejar mejor los eventos

        public Nodo ejecutarEvento(EnumEventos evento, int posX, int posY) {
            switch (evento) {
                case GEMA:
                    return gema(posX,posY);
                case JEFEENEMIGO:
                    return jefeEnemigo(random.nextInt(100));
                case COFRES:
                    return cofre();
                case PORTAL:
                    return abrirPortal();
                case TRAMPA:
                    return trampa();
                case NADAXD:
                    return nada();
                    
            }
            return null;
        }

        
        public Nodo gema(int posX,int posY) {
            //Seleccion al azar de los atributos de la gema
            NombreLugar[] nombresLugar = NombreLugar.values();
            Lugar[] lugar = Lugar.values();
            int i = random.nextInt(nombresLugar.length);
            int j = random.nextInt(lugar.length);
            //Retroalimentacion visual
            Sonido.reproducir("src/Resources/gema.wav");
            JOptionPane.showMessageDialog(null,
                    "¡Conseguiste la gema de " + lugar[j].getNombre() + " de " + nombresLugar[i].getNombre() + "!",
                    "Gema encontrada",
                    JOptionPane.INFORMATION_MESSAGE);
                    
            //Metodo para insertar la nueva gema en el arbol del inventario
            Nodo nuevaGema =new Nodo(random.nextInt(100),
                    "Gema de " + lugar[j].getNombre() + " de " + nombresLugar[i].getNombre(),
                    posX+i*10, posY+j*10) ;
            arbol.insertarGema(nuevaGema,arbol.getRaiz());
            return nuevaGema;
        }

        public Nodo jefeEnemigo(int gemaPedir) {
            Nodo raiz = arbol.raiz;
            Nodo gemaBuscada = this.arbol.buscarRecursivo(raiz, gemaPedir);
            //Retroalimentacion visual
            Sonido.reproducir("src/Resources/enemy.wav");
            if (gemaBuscada != null) {                
                JOptionPane.showMessageDialog(null,
                        "El jefe recibe la gema exacta: " + gemaBuscada.getNombre(),
                        "Cuidado",
                        JOptionPane.WARNING_MESSAGE);
                //Se elimina el nodo de el inventario si existe
                arbol.eliminar(gemaBuscada.getPoder());
                return gemaBuscada;

            } else {

                // Sino existe se usa el sucesor y el predecesor para eliminar gema
                Nodo sucesor = this.arbol.encontrarSucesor(gemaPedir);
                Nodo predecesor = this.arbol.encontrarPredecesor(gemaPedir);

                if (sucesor != null) {
                                //Retroalimentacion visual

                    JOptionPane.showMessageDialog(null,
                            "El jefe espera la gema con poder "+ gemaPedir +". Se entrega el sucesor: " + sucesor.getNombre(),
                            "Jefe",
                            JOptionPane.INFORMATION_MESSAGE);
                    arbol.eliminar(sucesor.getPoder());
                    return sucesor;
                } else if (predecesor != null) {
                                //Retroalimentacion visual

                    JOptionPane.showMessageDialog(null,
                            "El jefe espera la gema con poder "+ gemaPedir +" Se entrega el predecesor: " + predecesor.getNombre(),
                            "Jefe",
                            JOptionPane.INFORMATION_MESSAGE);
                    arbol.eliminar(predecesor.getPoder());
                    return predecesor;
                } else {
            //Retroalimentacion visual
                    JOptionPane.showMessageDialog(null,
                            "No hay gemas que entregar al jefe.",
                            "Jefe",
                            JOptionPane.WARNING_MESSAGE);
                            Sonido.reproducir("src/Resources/GameOver.wav");
                            
                    return null; // El árbol está vacío
                }
                
            }

        }

        public Nodo cofre() {
            
            // Se busca el minimo en el arbol para abrir el cofre
            Nodo minimo = arbol.encontrarMinimo();
            if (minimo != null) {
                            //Retroalimentacion visual

                Sonido.reproducir("src/Resources/cofre.wav");
                JOptionPane.showMessageDialog(null,
                        "El cofre se abre con la gema mínima: " + minimo.getNombre(),
                        "Cofre",
                        JOptionPane.INFORMATION_MESSAGE);
                        arbol.eliminar(minimo.getPoder());
            } else {
                // Sino existe minimo es porque esta vacio, por ende el jugador perdio
                Sonido.reproducir("src/Resources/GameOver.wav");
                JOptionPane.showMessageDialog(null,
                        "El cofre no se puede abrir. No hay gemas.",
                        "Cofre",
                        JOptionPane.WARNING_MESSAGE);
                       
            }
            return minimo;

        }

        public Nodo abrirPortal() {
            // Se busca el maximo para abrir el portal
            Nodo maximo = arbol.encontrarMaximo();
            if (maximo != null) {
                Sonido.reproducir("src/Resources/Portal-sound-design.wav");
                JOptionPane.showMessageDialog(null,
                        "El portal se abre con la gema maxima: " + maximo.getNombre(),
                        "Portal",
                        JOptionPane.INFORMATION_MESSAGE);
                        arbol.eliminar(maximo.getPoder());
              
                        

            } else {
                JOptionPane.showMessageDialog(null,
                        "El portal no se puede abrir. No hay gemas.",
                        "Portal",
                        JOptionPane.WARNING_MESSAGE);
            }
            return maximo;



        }

        public Nodo trampa() {

            if (arbol.raiz == null) {                
              // Sino existe la rayz es porque esta vacio, por ende el jugador perdio

                Sonido.reproducir("src/Resources/GameOver.wav");
                JOptionPane.showMessageDialog(null,
                        "No hay gemas que perder.",
                        "Trampa",
                        JOptionPane.WARNING_MESSAGE);
                return null;
            }

            // Se busca una gema en especifico aleatoriamente y se elimina
            ArrayList<Nodo> inventario = this.arbol.obtenerInventario();
            int index = random.nextInt(inventario.size());
            Nodo gemaAEliminar = inventario.get(index);
            arbol.eliminar(gemaAEliminar.getPoder());
                        //Retroalimentacion visual

            JOptionPane.showMessageDialog(null,
                    "El jugador cayó en una trampa y perdió la gema: " + gemaAEliminar.getNombre(),
                    "Trampa",
                    JOptionPane.WARNING_MESSAGE);
            return gemaAEliminar;
        }

        public Nodo nada() {
            //Retroalimentacion visual

            Sonido.reproducir("src/Resources/nada.wav");
            JOptionPane.showMessageDialog(null,
                    "No hay nada aquí, busca en otro lado :p",
                    "No pasó nada",
                    JOptionPane.INFORMATION_MESSAGE);
            return new Nodo(0,
                    "gema vacia",
                    0,0) ;
        }

        public ArrayList<Nodo> obtenerInventario() {
            return arbol.obtenerInventario();
        }
    }
