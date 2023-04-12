import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Personaje extends Thread {

    private int x, y;
    public static int VelocidadX, VelocidadY;
    private boolean saltando;

    private static final int GRAVEDAD = 1;
    private int limiteDerecho;
    private int limiteIzquierdo;

    private boolean caminando;
    private boolean gano;
    private boolean derrotado;

    private Image imagenParado;
    private Image imagenCaminando;
    private Image imagenSaltando;
    private Image imagenDerrotado;
    private Image imagenGano;

    private Sonido sonidoSaltar;

    public Personaje() {
        ImageIcon ii1 = new ImageIcon(getClass().getResource("/Resources/player1.png"));
        Image img1 = ii1.getImage();
        imagenParado = img1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        ImageIcon ii2 = new ImageIcon(getClass().getResource("/Resources/player2.png"));
        Image img2 = ii2.getImage();
        imagenCaminando = img2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        ImageIcon ii3 = new ImageIcon(getClass().getResource("/Resources/playerJump.png"));
        Image img3 = ii3.getImage();
        imagenSaltando = img3.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        ImageIcon ii4 = new ImageIcon(getClass().getResource("/Resources/playerDefeated.png"));
        Image img4 = ii4.getImage();
        imagenDerrotado = img4.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        ImageIcon ii5 = new ImageIcon(getClass().getResource("/Resources/playerWin.png"));
        Image img5 = ii5.getImage();
        imagenGano = img5.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        caminando = false;
        derrotado = false;
        gano = false;

        x = 0;
        y = 0;
        VelocidadX = 0;
        VelocidadY = 0;
        saltando = false;
        limiteIzquierdo = 0;
        limiteDerecho = 300;

        sonidoSaltar = new Sonido("Resources/jumpingSound.wav");
    }

    public void reset() {
        caminando = false;
        derrotado = false;
        gano = false;
        x = 0;
        y = 0;
        VelocidadX = 0;
        VelocidadY = 0;
        saltando = false;
        limiteIzquierdo = 0;
        limiteDerecho = 300;
    }

    public synchronized void mover(int posX) {

        y += VelocidadY;
        x += VelocidadX;

        System.out.println(posX);

        if (x >= limiteDerecho) {
            x = limiteDerecho;
        }

        if (x <= limiteIzquierdo) {
            x = limiteIzquierdo;
        }

        if (y >= 185) {
            y = 185;
            VelocidadY = 0;
            saltando = false;
        } else {
            VelocidadY += GRAVEDAD; // Aplicamos efecto de gravedad
        }

        if (posX == -2330) {
            y = 100;
            VelocidadY = 0;
            saltando = false;
            gano = true;
        } else {
            gano = false;
        }

        // Resetear la velocidad X del personaje cuando no se está moviendo
        if (VelocidadX != 0) {
            caminando = false;
            VelocidadX = 0;
        }

    }

    public synchronized void dibujar(Graphics g) {
        if (derrotado) {
            g.drawImage(imagenDerrotado, x, y, null);
        } else if (saltando) {
            g.drawImage(imagenSaltando, x, y, null);
        } else if (caminando) {
            g.drawImage(imagenCaminando, x, y, null);
        } else if (gano) {
            g.drawImage(imagenGano, x, y, null);
        } else {
            g.drawImage(imagenParado, x, y, null);
        }
    }

    public synchronized void avanzar() {
        VelocidadX = 5;
        caminando = true;

    }

    public synchronized void retroceder() {
        VelocidadX = -5;
        caminando = true;

    }

    public synchronized void saltar() {

        if (!saltando) {
            sonidoSaltar.reproducir(false);
            VelocidadY = -18; // Velocidad hacia arriba
            saltando = true;
        }
    }

    public synchronized void saltarDerecha() {
        if (!saltando) {
            sonidoSaltar.reproducir(false);
            VelocidadY = -18; // Velocidad hacia arriba
            VelocidadX = 30; // Velocidad hacia la derecha
            saltando = true;
        }
    }

    public synchronized void saltarIzquierda() {
        if (!saltando) {
            sonidoSaltar.reproducir(false);
            VelocidadY = -18; // Velocidad hacia arriba
            VelocidadX = -30; // Velocidad hacia la derecha
            saltando = true;
        }
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized int getY() {
        return y;
    }

    public synchronized int getVelocidadX() {
        return VelocidadX;
    }

    public synchronized int getVelocidadY() {
        return VelocidadY;
    }

    public synchronized void derrotado(Boolean status) {
        if (status) {
            derrotado = true;
        }
    }

    public synchronized Boolean ganador() {
        return gano;
    }

}
