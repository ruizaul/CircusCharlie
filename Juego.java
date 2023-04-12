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
    private JButton botonPausar; // Nuevo botón para pausar/reanudar el juego
    private JButton botonReanudar;
    private JButton botonReset; // Nuevo botón para pausar/reanudar el juego

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
    private boolean pausa = false; // Variable para controlar la pausa
    private boolean reinicio = false;

    public void Ventana() {
        ventana = new JFrame("Circus Charlie");
        ventana.setSize(800, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        botonStart = new JButton("Start");
        botonStart.setBounds(350, 150, 100, 40);
        add(botonStart);
        botonStart.setFocusable(false);
        botonStart.addActionListener(this);

        botonPausar = new JButton("Pausar");
        botonPausar.setBounds(350, 200, 100, 40); // Colocar el botón en la pantalla
        add(botonPausar);
        botonPausar.setFocusable(false);
        botonPausar.addActionListener(this); // Agregar un ActionListener para controlar la pausa

        botonReanudar = new JButton("Reanudar");
        botonReanudar.setBounds(350, 250, 100, 40); // Colocar el botón en la pantalla
        add(botonReanudar);
        botonReanudar.setFocusable(false);
        botonReanudar.addActionListener(this); // Agregar un ActionListener para controlar la pausa

        botonReset = new JButton("Reiniciar");
        botonReset.setBounds(350, 300, 100, 40); // Colocar el botón en la pantalla
        add(botonReset);
        botonReset.setFocusable(false);
        botonReset.addActionListener(this); // Agregar un ActionListener para controlar la pausa

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

    public synchronized void IniciarJuego() {
        // Crear el hilo para el movimiento del personaje
        movimientoPersonaje = new Thread(new Runnable() {
            public void run() {
                while (EstadoJuego) {
                    if (!pausa) {
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
                while (EstadoJuego) {
                    if (!pausa) {
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
                while (EstadoJuego) {
                    if (!pausa) {
                        obstaculosaltarin.actualizarObstaculos(personaje.getX(), personaje.getY());
                        obstaculosaltarin.colisionaConPersonaje(personaje.getX(), personaje.getY());
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
                while (EstadoJuego) {
                    if (!pausa) {
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
                if (!pausa) {
                    sonidoFondo.reproducir(true);
                }
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        musicaFondo.start();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == botonStart) {
            EstadoJuego = true;
            pausa = false;
            IniciarJuego();
            System.out.println("¡A darle!");
        }
        if (e.getSource() == botonPausar) {
            System.out.println("¡Pausa!");
            sonidoFondo.detener();
            pausa = true;
        }
        if (e.getSource() == botonReanudar) {
            System.out.println("!Reanuda!");
            if (EstadoJuego) {
                sonidoFondo.reproducir(true);
            }
            pausa = false;
        }
        if (e.getSource() == botonReset) {
            movimientoPersonaje.interrupt();
            movimientoObstaculo.interrupt();
            movimientoObstaculoSaltarin.interrupt();
            movimientoFondo.interrupt();

            EstadoJuego = true;
            pausa = false;
            reinicio = true;

            fondo.reset();
            personaje.reset();
            obstaculo.reset();
            obstaculosaltarin.reset();
        }

        if (EstadoJuego) {
            botonStart.setEnabled(false);
            // Si el juego está activo, actualizamos los componentes
            obstaculo.colisionaConPersonaje(personaje.getX(), personaje.getY());
            repaint();

            if (!obstaculo.isActivo() || !obstaculosaltarin.isActivo()) {
                // Si el obstáculo ha colisionado, detenemos el juego
                personaje.derrotado(true);
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
                        System.out.println("¡Ouch!");
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }

        if (personaje.ganador()) {
            repaint();
            Timer timer = new Timer(100, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // Aquí colocas las acciones que deseas realizar después de un segundo
                    EstadoJuego = false;
                    temporizador.stop();
                    movimientoPersonaje.interrupt();
                    movimientoObstaculo.interrupt();
                    movimientoObstaculoSaltarin.interrupt();
                    movimientoFondo.interrupt();
                    sonidoFondo.detener();
                    sonidoMuerte.reproducir(false);
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
                if (!EstadoJuego) {
                    break;
                }
                personaje.saltar();
                break;

            case KeyEvent.VK_E:
                if (!EstadoJuego) {
                    break;
                }
                personaje.saltarDerecha();
                break;

            case KeyEvent.VK_Q:
                if (!EstadoJuego) {
                    break;
                }
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
