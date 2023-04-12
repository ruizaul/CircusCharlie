import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;

public class Juego extends JPanel implements KeyListener, ActionListener {
    private JFrame ventana;
    private JButton botonStart;

    private Fondo fondo;
    private Personaje personaje;
    private Obstaculo obstaculo;
    private ObstaculoSaltarin obstaculosaltarin;
    private Timer temporizador;
    private Sonido sonidoFondo;
    private Sonido sonidoMuerte;
    private Sonido SonidoGanador;

    private Thread movimientoPersonaje;
    private Thread movimientoObstaculo;
    private Thread movimientoObstaculoSaltarin;
    private Thread movimientoFondo;
    private Thread musicaFondo;

    private boolean EstadoJuego = false;
    private boolean juegoPausado = false;

    public void iniciarJuego() {
        ventana = new JFrame("Mi juego");
        ventana.setSize(800, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        botonStart = new JButton("Start");
        botonStart.setBounds(350, 150, 100, 40); // Colocar el botón en el centro de la pantalla
        add(botonStart);
        botonStart.setFocusable(false);
        botonStart.addActionListener(this);

        sonidoFondo = new Sonido("Resources/Music.wav");
        sonidoMuerte = new Sonido("Resources/defeatSound.wav");
        SonidoGanador = new Sonido("Resources/winSound.wav");

        fondo = new Fondo();
        personaje = new Personaje();
        obstaculo = new Obstaculo();
        obstaculosaltarin = new ObstaculoSaltarin();

        ventana.addKeyListener(this);
        ventana.add(this);
        ventana.setVisible(true);

        temporizador = new Timer(1000 / 60, this);
        temporizador.start();

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == botonStart) {
            EstadoJuego = true;
            System.out.println("¡Juego empezado!");
            // Crear el hilo para el movimiento del personaje
            movimientoPersonaje = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (!juegoPausado) {
                            personaje.mover(fondo.getposX());
                        }
                        try {
                            Thread.sleep(16); // 60 fps
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            movimientoPersonaje.start();

            // Crear el hilo para el movimiento del personaje
            movimientoObstaculo = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (!juegoPausado) {
                            obstaculo.actualizarObstaculos(personaje.getX(), personaje.getY());
                            obstaculo.colisionaConPersonaje(personaje.getX(), personaje.getY());
                        }
                        try {
                            Thread.sleep(16); // 60 fps
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            movimientoObstaculo.start();

            // Crear el hilo para el movimiento del personaje
            movimientoObstaculoSaltarin = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (!juegoPausado) {
                            obstaculosaltarin.actualizarObstaculos();
                        }
                        try {
                            Thread.sleep(16); // 60 fps
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            movimientoObstaculoSaltarin.start();

            // Crear el hilo para el movimiento del personaje
            movimientoFondo = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (!juegoPausado) {
                            fondo.mover(personaje.getVelocidadX(), personaje.getX());
                        }
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

        if (EstadoJuego) {
            botonStart.setEnabled(false);
            // Si el juego está activo, actualizamos los componentes
            obstaculo.colisionaConPersonaje(personaje.getX(), personaje.getY());
            repaint();

            if (!obstaculo.isActivo()) {
                // Si el obstáculo ha colisionado, detenemos el juego
                personaje.derrotado(true);

                Timer timer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Aquí colocas las acciones que deseas realizar después de un segundo
                        temporizador.stop();
                        juegoPausado = false;
                        movimientoPersonaje.interrupt();
                        movimientoObstaculo.interrupt();
                        movimientoObstaculoSaltarin.interrupt();
                        movimientoFondo.interrupt();
                        sonidoFondo.detener();
                        sonidoMuerte.reproducir(false);
                        EstadoJuego = false;
                        botonStart.setEnabled(true);
                        System.out.println("¡Juego terminado!");
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }

        if (personaje.ganador()) {
            Timer timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Aquí colocas las acciones que deseas realizar después de un segundo
                    temporizador.stop();
                    movimientoPersonaje.interrupt();
                    movimientoObstaculo.interrupt();
                    movimientoObstaculoSaltarin.interrupt();
                    movimientoFondo.interrupt();
                    sonidoFondo.detener();
                    sonidoMuerte.reproducir(false);
                    EstadoJuego = false;
                    botonStart.setEnabled(true);
                    SonidoGanador.reproducir(false);
                    System.out.println("¡Juego terminado!");
                }
            });
            timer.setRepeats(false);
            timer.start();
        }

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
        for (Obstaculo obs : Obstaculo.getObstaculos()) {
            obs.dibujar(g);
        }
        for (ObstaculoSaltarin obs : ObstaculoSaltarin.getObstaculos()) {
            obs.dibujar(g);
        }
    }

}
