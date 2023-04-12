import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class Personaje {

    private int x, y;
    public static int VelocidadX, VelocidadY;
    private boolean saltando;
    private static final int GRAVEDAD = 1;
    private int limiteDerecho;
    private int limiteIzquierdo;

    private boolean caminando;
    private Image imagenParado;
    private Image imagenCaminando;
    private Image imagenSaltando;

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

        caminando = false;
        x = 0;
        y = 0;
        VelocidadX = 0;
        VelocidadY = 0;
        saltando = false;
        limiteIzquierdo = 0;
        limiteDerecho = 300;

    }

    public void mover() {

        y += VelocidadY;
        x += VelocidadX;

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

        // Resetear la velocidad X del personaje cuando no se está moviendo
        if (VelocidadX != 0) {
            caminando = false;
            VelocidadX = 0;
        }

    }

    public void dibujar(Graphics g) {
        if (caminando) {
            g.drawImage(imagenParado, x, y, null);
        }

        if (!caminando) {
            g.drawImage(imagenCaminando, x, y, null);
        }

        if (saltando) {
            g.drawImage(imagenSaltando, x, y, null);
        }

    }

    public void avanzar() {
        VelocidadX = 5;
        caminando = true;

    }

    public void retroceder() {
        VelocidadX = -5;
        caminando = true;

    }

    public void saltar() {

        if (!saltando) {
            VelocidadY = -18; // Velocidad hacia arriba
            saltando = true;
        }
    }

    public void saltarDerecha() {
        if (!saltando) {
            VelocidadY = -18; // Velocidad hacia arriba
            VelocidadX = 30; // Velocidad hacia la derecha
            saltando = true;
        }
    }

    public void saltarIzquierda() {
        if (!saltando) {
            VelocidadY = -18; // Velocidad hacia arriba
            VelocidadX = -30; // Velocidad hacia la derecha
            saltando = true;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelocidadX() {
        return VelocidadX;
    }

    public int getVelocidadY() {
        return VelocidadY;
    }

}
