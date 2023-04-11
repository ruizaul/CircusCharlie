import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Personaje {
    private Image imagen;
    private int x, y;
    public static int VelocidadX, VelocidadY;
    private boolean saltando;
    private static final int GRAVEDAD = 1;
    private int limiteDerecho;
    private int limiteIzquierdo;

    public Personaje() {
        ImageIcon ii = new ImageIcon(getClass().getResource("/Resources/player1.png"));
        Image img = ii.getImage();
        imagen = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Cambia el tamaño a 50x50

        x = 0;
        y = 100;
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

        if (y >= 165) {
            y = 165;
            VelocidadY = 0;
            saltando = false;
        } else {
            VelocidadY += GRAVEDAD; // Aplicamos efecto de gravedad
        }
        // Resetear la velocidad X del personaje cuando no se está moviendo
        if (VelocidadX != 0) {
            VelocidadX = 0;
        }
    }

    public void dibujar(Graphics g) {
        g.drawImage(imagen, x, y, null);
    }

    public void avanzar() {
        VelocidadX = 5;
    }

    public void retroceder() {
        VelocidadX = -5;
    }

    public void saltar() {
        if (!saltando) {
            VelocidadY = -20; // Velocidad hacia arriba
            saltando = true;
        }
    }

    public void saltarDerecha() {
        if (!saltando) {
            VelocidadX = 40; // Velocidad hacia la derecha
            VelocidadY = -20; // Velocidad hacia arriba
            saltando = true;
        }
    }

    public void saltarIzquierda() {
        if (!saltando) {
            VelocidadX = -40; // Velocidad hacia la derecha
            VelocidadY = -20; // Velocidad hacia arriba
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
