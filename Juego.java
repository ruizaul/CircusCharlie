import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Juego extends JPanel implements KeyListener, ActionListener {
    private JFrame ventana;
    private Fondo fondo;
    private Personaje personaje;
    private Timer temporizador;
    private Sonido sonidoFondo;

    private Thread movimientoPersonaje;
    private Thread movimientoFondo;
    private Thread musicaFondo;

    public void iniciarJuego() {
        ventana = new JFrame("Mi juego");
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        sonidoFondo = new Sonido("Resources/Music.wav");

        fondo = new Fondo();
        personaje = new Personaje();

        ventana.addKeyListener(this);
        ventana.add(this);
        ventana.setVisible(true);

        temporizador = new Timer(1000 / 60, this);
        temporizador.start();

        // Crear el hilo para el movimiento del personaje
        movimientoPersonaje = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    personaje.mover();
                    try {
                        Thread.sleep(6); // 60 fps
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        movimientoPersonaje.start();

                // Crear el hilo para el movimiento del personaje
                movimientoFondo = new Thread(new Runnable() {
                    public void run() {
                        while (true) {
                            fondo.mover(personaje.getVelocidadX(), personaje.getX());
                            try {
                                Thread.sleep(6); // 60 fps
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                movimientoFondo.start();

        // Crear el hilo para la música de fondo
        musicaFondo = new Thread(new Runnable() {
            public void run() {
                sonidoFondo.reproducir(true);
            }
        });
        musicaFondo.start();
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_D:
                personaje.avanzar();
                break;

            case KeyEvent.VK_A:
                personaje.retroceder();
                break;

            case KeyEvent.VK_SPACE:
                personaje.saltar();
                break;

            case KeyEvent.VK_E:
                personaje.saltarDerecha();
                break;

            case KeyEvent.VK_Q:
                personaje.saltarIzquierda();
                break;
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        fondo.dibujar(g, personaje.getX());
        personaje.dibujar(g);
    }

}
