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

    public void iniciarJuego() {
        ventana = new JFrame("Mi juego");
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        sonidoFondo = new Sonido("Resources/Music.wav");
        sonidoFondo.reproducir(true);

        fondo = new Fondo();
        personaje = new Personaje();

        ventana.addKeyListener(this);
        ventana.add(this);
        ventana.setVisible(true);

        temporizador = new Timer(1000 / 60, this);
        temporizador.start();
    }

    public void actionPerformed(ActionEvent e) {
        mover();
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_D:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    personaje.saltarDerecha();
                }
                personaje.avanzar();
                break;

            case KeyEvent.VK_A:
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    personaje.saltarIzquierda();
                }
                personaje.retroceder();
                break;

            case KeyEvent.VK_SPACE:
                personaje.saltar();
                break;

        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {
    }

    public void mover() {
        fondo.mover(personaje.getVelocidadX());
        personaje.mover();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        fondo.dibujar(g, personaje.getX());
        personaje.dibujar(g);
    }

}
