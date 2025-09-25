/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Core.Juego;
import Core.Nodo;
import Resources.MusicaFondo;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class Field extends javax.swing.JFrame {

    private final Juego juego;
    private int i;
    private int j;
    private JLabel jugador;
    private int movimientosRestantes = 0;
    private JButton posicionActual; // el botón donde está el jugador
    private JButton[][] grid;
    private Set<JButton> casillasVisitadas = new HashSet<>();
    MusicaFondo musicaJuego = new MusicaFondo();
    private int portales;

    /**
     * Creates new form Field
     */
    public Field(Juego juego) {
        setBackGround();
        initComponents();
        musicaJuego.reproducir("src/Resources/fondo.wav");
        inicializarGrid();

        // Crear el PNG del jugador
        jugador = new JLabel(new ImageIcon("src/Images/player.png"));
        jugador.setSize(60, 60); // mismo tamaño que los botones
        jugador.setVisible(true);
        jugador.setFocusable(false);

        colocarJugadorEn(grid[0][0]);

        setLocationRelativeTo(null);
        this.juego = juego;
        i = 1;
        j = 1;
        portales = 0;

    }

    public Field(Juego juego, int portales) {
        setBackGround();
        initComponents();
        musicaJuego.reproducir("src/Resources/fondo.wav");
        inicializarGrid();

        // Crear el PNG del jugador
        jugador = new JLabel(new ImageIcon("src/Images/player.png"));
        jugador.setSize(60, 60); // mismo tamaño que los botones
        jugador.setVisible(true);
        jugador.setFocusable(false);

        colocarJugadorEn(grid[0][0]);

        setLocationRelativeTo(null);
        this.juego = juego;
        i = 1;
        j = 1;
        this.portales = portales;

    }

    private void setBackGround() {
        // Sobrescribir el método paint para dibujar el fondo
        JPanel MapGamePanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Cargar y dibujar la imagen
                Image backgroundImage = new ImageIcon("src/Images/grass__r1209392301.png").getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        MapGamePanel.setLayout(new BorderLayout());
        setContentPane(MapGamePanel);
    }

    private void inicializarGrid() {
        grid = new JButton[4][5]; // 4 filas, 5 columnas
        grid[0][0] = bprueba;
        grid[0][1] = bprueba5;
        grid[0][2] = bprueba9;
        grid[0][3] = bprueba13;
        grid[0][4] = bprueba17;

        grid[1][0] = bprueba4;
        grid[1][1] = bprueba6;
        grid[1][2] = bprueba10;
        grid[1][3] = bprueba14;
        grid[1][4] = bprueba18;

        grid[2][0] = bprueba2;
        grid[2][1] = bprueba7;
        grid[2][2] = bprueba11;
        grid[2][3] = bprueba15;
        grid[2][4] = bprueba19;

        grid[3][0] = bprueba3;
        grid[3][1] = bprueba8;
        grid[3][2] = bprueba12;
        grid[3][3] = bprueba16;
        grid[3][4] = bprueba20;

    }

    private void colocarJugadorEn(JButton boton) {
        // Si el jugador estaba en otro botón, lo quitamos de ese botón
        if (jugador.getParent() != null) {
            Container parent = jugador.getParent();
            parent.remove(jugador);
            parent.revalidate();
            parent.repaint();
        }

        // Añadimos el jugador al nuevo botón
        boton.setLayout(new BorderLayout());
        boton.add(jugador, BorderLayout.CENTER);

        boton.revalidate();
        boton.repaint();

        // Actualizamos la posición actual
        posicionActual = boton;
    }

    // Con este metodo se verifica si el jugador se puede mover a la casilla que undio
    private boolean esVecino(JButton nuevo, JButton actual) {
        int filaActual = -1, colActual = -1;
        int filaNuevo = -1, colNuevo = -1;

        for (int f = 0; f < grid.length; f++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[f][c] == actual) {
                    filaActual = f;
                    colActual = c;
                }
                if (grid[f][c] == nuevo) {
                    filaNuevo = f;
                    colNuevo = c;
                }

            }

        }

        System.out.println("Actual: (" + filaActual + "," + colActual
                + ") -> Nuevo: (" + filaNuevo + "," + colNuevo + ")");

        if (filaActual == -1 || filaNuevo == -1) {
            return false;
        }

        
        int difFila = Math.abs(filaNuevo - filaActual);
        int difCol = Math.abs(colNuevo - colActual);

        // Retorna si la nueva casilla esta a una diferencia de 1 de la posición del jugador
        return (difFila + difCol == 1);
    }

    private void moverJugador(JButton botonDestino) {
        movimientosRestantes = 1;

        if (movimientosRestantes > 0 && esVecino(botonDestino, posicionActual)) {
            colocarJugadorEn(botonDestino);
            movimientosRestantes--;
            System.out.println("Movimientos restantes: " + movimientosRestantes);

            int[] pos = getGridPosition(botonDestino);
            int fila = pos[0];
            int col = pos[1];

            if (fila != -1 && col != -1) {
                int coordX = col * 100;
                int coordY = fila * 100;

                // Si nunca visitaste esta casilla, dispara evento
                if (!casillasVisitadas.contains(botonDestino)) {
                    Nodo nodo = juego.llamadaEvento(coordX, coordY);
                    if (nodo == null) {
                        GameOver GO = new GameOver();
                        GO.setVisible(true);
                        dispose();
                        musicaJuego.detener();
                    }

                    // marcar como visitada
                    casillasVisitadas.add(botonDestino);

                    // quitar el icono como indicador visual
                    botonDestino.setEnabled(false);
                }
            } else {
                System.err.println("No se encontró la posición del botón en la malla (grid).");
            }

        } else {
            JOptionPane.showMessageDialog(null,
                    "No te puedes mover a esta casilla.",
                    "Movimiento",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Saber en que posicion del grid esta
    private int[] getGridPosition(JButton boton) {
        for (int f = 0; f < grid.length; f++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[f][c] == boton) {
                    return new int[]{f, c};
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        ButtonsField = new javax.swing.JPanel();
        bprueba = new javax.swing.JButton();
        bprueba2 = new javax.swing.JButton();
        bprueba3 = new javax.swing.JButton();
        bprueba4 = new javax.swing.JButton();
        bprueba5 = new javax.swing.JButton();
        bprueba6 = new javax.swing.JButton();
        bprueba7 = new javax.swing.JButton();
        bprueba8 = new javax.swing.JButton();
        bprueba10 = new javax.swing.JButton();
        bprueba12 = new javax.swing.JButton();
        bprueba11 = new javax.swing.JButton();
        bprueba9 = new javax.swing.JButton();
        bprueba15 = new javax.swing.JButton();
        bprueba13 = new javax.swing.JButton();
        bprueba14 = new javax.swing.JButton();
        bprueba16 = new javax.swing.JButton();
        bprueba18 = new javax.swing.JButton();
        bprueba17 = new javax.swing.JButton();
        bprueba20 = new javax.swing.JButton();
        bprueba19 = new javax.swing.JButton();
        UtilityButtons = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ButtonsField.setBackground(new java.awt.Color(75, 145, 18));
        ButtonsField.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        ButtonsField.setForeground(new java.awt.Color(102, 255, 102));

        bprueba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bpruebaActionPerformed(evt);
            }
        });

        bprueba2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba2ActionPerformed(evt);
            }
        });

        bprueba3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba3ActionPerformed(evt);
            }
        });

        bprueba4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba4ActionPerformed(evt);
            }
        });

        bprueba5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba5ActionPerformed(evt);
            }
        });

        bprueba6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba6ActionPerformed(evt);
            }
        });

        bprueba7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba7ActionPerformed(evt);
            }
        });

        bprueba8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba8ActionPerformed(evt);
            }
        });

        bprueba10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba10ActionPerformed(evt);
            }
        });

        bprueba12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba12ActionPerformed(evt);
            }
        });

        bprueba11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba11ActionPerformed(evt);
            }
        });

        bprueba9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba9ActionPerformed(evt);
            }
        });

        bprueba15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba15ActionPerformed(evt);
            }
        });

        bprueba13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba13ActionPerformed(evt);
            }
        });

        bprueba14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba14ActionPerformed(evt);
            }
        });

        bprueba16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba16ActionPerformed(evt);
            }
        });

        bprueba18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba18ActionPerformed(evt);
            }
        });

        bprueba17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba17ActionPerformed(evt);
            }
        });

        bprueba20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba20ActionPerformed(evt);
            }
        });

        bprueba19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/8d0b7f6bdbddade9fddf851a31a1004d (2).jpg"))); // NOI18N
        bprueba19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprueba19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ButtonsFieldLayout = new javax.swing.GroupLayout(ButtonsField);
        ButtonsField.setLayout(ButtonsFieldLayout);
        ButtonsFieldLayout.setHorizontalGroup(
            ButtonsFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ButtonsFieldLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(ButtonsFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bprueba4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ButtonsFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bprueba6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ButtonsFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bprueba10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ButtonsFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bprueba14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ButtonsFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bprueba18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bprueba17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 28, Short.MAX_VALUE))
        );
        ButtonsFieldLayout.setVerticalGroup(
            ButtonsFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ButtonsFieldLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(ButtonsFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ButtonsFieldLayout.createSequentialGroup()
                        .addComponent(bprueba17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bprueba18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bprueba19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bprueba20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ButtonsFieldLayout.createSequentialGroup()
                        .addComponent(bprueba13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bprueba14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bprueba15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bprueba16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ButtonsFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(ButtonsFieldLayout.createSequentialGroup()
                            .addComponent(bprueba9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(bprueba10, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(bprueba11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(bprueba12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(ButtonsFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ButtonsFieldLayout.createSequentialGroup()
                                .addComponent(bprueba5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bprueba6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bprueba7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bprueba8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(ButtonsFieldLayout.createSequentialGroup()
                                .addComponent(bprueba, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bprueba4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bprueba2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bprueba3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        UtilityButtons.setBackground(new java.awt.Color(75, 145, 18));
        UtilityButtons.setBorder(new javax.swing.border.MatteBorder(null));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btninventario (1) (1).png"))); // NOI18N
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton1.setPreferredSize(new java.awt.Dimension(125, 29));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btnportal.png"))); // NOI18N
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.setPreferredSize(new java.awt.Dimension(124, 29));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/btnreintentar[1].png"))); // NOI18N
        jButton3.setToolTipText("");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout UtilityButtonsLayout = new javax.swing.GroupLayout(UtilityButtons);
        UtilityButtons.setLayout(UtilityButtonsLayout);
        UtilityButtonsLayout.setHorizontalGroup(
            UtilityButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UtilityButtonsLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(UtilityButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(UtilityButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3)))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        UtilityButtonsLayout.setVerticalGroup(
            UtilityButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UtilityButtonsLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(ButtonsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(UtilityButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(ButtonsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(UtilityButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bpruebaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bpruebaActionPerformed
        moverJugador(bprueba);

    }//GEN-LAST:event_bpruebaActionPerformed

    private void bprueba2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba2ActionPerformed
        moverJugador(bprueba2);
    }//GEN-LAST:event_bprueba2ActionPerformed

    private void bprueba3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba3ActionPerformed
        moverJugador(bprueba3);
    }//GEN-LAST:event_bprueba3ActionPerformed

    private void bprueba4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba4ActionPerformed
        moverJugador(bprueba4);
    }//GEN-LAST:event_bprueba4ActionPerformed

    private void bprueba5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba5ActionPerformed
        moverJugador(bprueba5);
    }//GEN-LAST:event_bprueba5ActionPerformed

    private void bprueba6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba6ActionPerformed
        moverJugador(bprueba6);
    }//GEN-LAST:event_bprueba6ActionPerformed

    private void bprueba7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba7ActionPerformed
        moverJugador(bprueba7);
    }//GEN-LAST:event_bprueba7ActionPerformed

    private void bprueba8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba8ActionPerformed
        moverJugador(bprueba8);
    }//GEN-LAST:event_bprueba8ActionPerformed

    private void bprueba10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba10ActionPerformed
        moverJugador(bprueba10);
    }//GEN-LAST:event_bprueba10ActionPerformed

    private void bprueba11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba11ActionPerformed
        moverJugador(bprueba11);
    }//GEN-LAST:event_bprueba11ActionPerformed

    private void bprueba12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba12ActionPerformed
        moverJugador(bprueba12);
    }//GEN-LAST:event_bprueba12ActionPerformed

    private void bprueba13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba13ActionPerformed
        moverJugador(bprueba13);
    }//GEN-LAST:event_bprueba13ActionPerformed

    private void bprueba14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba14ActionPerformed
        moverJugador(bprueba14);
    }//GEN-LAST:event_bprueba14ActionPerformed

    private void bprueba15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba15ActionPerformed
        moverJugador(bprueba15);
    }//GEN-LAST:event_bprueba15ActionPerformed

    private void bprueba16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba16ActionPerformed
        moverJugador(bprueba16);
    }//GEN-LAST:event_bprueba16ActionPerformed

    private void bprueba17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba17ActionPerformed
        moverJugador(bprueba17);
    }//GEN-LAST:event_bprueba17ActionPerformed

    private void bprueba18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba18ActionPerformed
        moverJugador(bprueba18);
    }//GEN-LAST:event_bprueba18ActionPerformed

    private void bprueba19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba19ActionPerformed
        moverJugador(bprueba19);
    }//GEN-LAST:event_bprueba19ActionPerformed

    private void bprueba20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba20ActionPerformed
        moverJugador(bprueba20);
    }//GEN-LAST:event_bprueba20ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Metodo para abrir el portal
        Nodo abierto = juego.abrirPortal();
        if (abierto != null) {

            System.out.println("portales: " + portales);
            this.dispose();
            if (portales < 2) {
                musicaJuego.detener();
                Field field = new Field(this.juego, portales + 1);
                field.setVisible(true);
            } else {
                dispose();
                Congratulations congrats = new Congratulations();
                congrats.setVisible(true);
                musicaJuego.detener();
                musicaJuego.reproducir("src/resources/winMusic");
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void bprueba9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprueba9ActionPerformed
        moverJugador(bprueba9);
    }//GEN-LAST:event_bprueba9ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        juego.obtenerInventario();
        Inventory inventario = new Inventory(this.juego);
        inventario.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Field field = new Field(new Juego());
        field.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Field.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Field.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Field.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Field.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*   java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Field().setVisible(true);
            }
        });*/
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ButtonsField;
    private javax.swing.JPanel UtilityButtons;
    private javax.swing.JButton bprueba;
    private javax.swing.JButton bprueba10;
    private javax.swing.JButton bprueba11;
    private javax.swing.JButton bprueba12;
    private javax.swing.JButton bprueba13;
    private javax.swing.JButton bprueba14;
    private javax.swing.JButton bprueba15;
    private javax.swing.JButton bprueba16;
    private javax.swing.JButton bprueba17;
    private javax.swing.JButton bprueba18;
    private javax.swing.JButton bprueba19;
    private javax.swing.JButton bprueba2;
    private javax.swing.JButton bprueba20;
    private javax.swing.JButton bprueba3;
    private javax.swing.JButton bprueba4;
    private javax.swing.JButton bprueba5;
    private javax.swing.JButton bprueba6;
    private javax.swing.JButton bprueba7;
    private javax.swing.JButton bprueba8;
    private javax.swing.JButton bprueba9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    // End of variables declaration//GEN-END:variables
}
