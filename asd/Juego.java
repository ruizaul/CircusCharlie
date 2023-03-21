package asd;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Juego extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Image jugador;
    private Image fondo;
    private int jugadorX, jugadorY;
    private int fondoX, fondoY;
    private int anchoPantalla = 800; // ancho de la pantalla
    private int altoPantalla = 600; // alto de la pantalla
    private int mitadPantalla = anchoPantalla / 2; // mitad de la pantalla
    private boolean izquierdaPresionada, derechaPresionada;

    public Juego() {
        jugador = new ImageIcon("Resources/player.png").getImage(); // cargar imagen del jugador
        fondo = new ImageIcon("Resources/background.png").getImage(); // cargar imagen de fondo
        jugadorX = mitadPantalla - jugador.getWidth(null) / 2; // centrar jugador en la mitad de la pantalla
        jugadorY = altoPantalla - jugador.getHeight(null) - 50; // colocar jugador en la parte inferior de la pantalla
        fondoX = 0; // colocar fondo en la posición inicial
        fondoY = altoPantalla - fondo.getHeight(null);
        timer = new Timer(20, this); // crear timer para actualizar el juego cada 20 milisegundos
        timer.start(); // iniciar el timer
        addKeyListener(this); // agregar KeyListener al panel del juego
        setFocusable(true); // permitir que el panel del juego tenga el foco
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(fondo, fondoX, fondoY, null); // dibujar fondo
        g2d.drawImage(jugador, jugadorX, jugadorY, null); // dibujar jugador
    }

    public void actionPerformed(ActionEvent e) {
        // mover jugador
        if (izquierdaPresionada && jugadorX > 0) {
            jugadorX -= 5;
        }
        if (derechaPresionada && jugadorX < anchoPantalla - jugador.getWidth(null)) {
            jugadorX += 5;
        }
        // mover fondo
        fondoX -= 2;
        if (fondoX == -fondo.getWidth(null)) {
            fondoX = 0;
        }
        repaint(); // repintar pantalla
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            izquierdaPresionada = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            derechaPresionada = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            izquierdaPresionada = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            derechaPresionada = false;
        }
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Juego");
        frame.add(new Juego());
        frame.setSize(800,600); // establecer tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // centrar la ventana en la pantalla
        frame.setVisible(true); // hacer la ventana visible
        }
        } 
