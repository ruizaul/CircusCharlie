import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Personaje {
    private Image imagen;
    private int x, y;
    public static  int VelocidadX, VelocidadY;
    private boolean saltando;
    private static final int GRAVEDAD = 1;
    

    public Personaje() {
        imagen = new ImageIcon(getClass().getResource("/Resources/player.png")).getImage();
        x = 50;
        y = 300;
        VelocidadX = 0;
        VelocidadY = 0;
        saltando = false;
    }

    public void mover() {
        x += VelocidadX;
        y += VelocidadY;
        if (y >= 300) {
            y = 300;
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
            VelocidadY = -30; // Velocidad hacia arriba
            saltando = true;
        }
    }

    public void saltarDerecha() {
        if (!saltando) {
            VelocidadX = 40; // Velocidad hacia la derecha
            VelocidadY = -5; // Velocidad hacia arriba
            saltando = true;
        }
    }

    public void saltarIzquierda() {
        if (!saltando) {
            VelocidadX = -40; // Velocidad hacia la derecha
            VelocidadY = -5; // Velocidad hacia arriba
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
