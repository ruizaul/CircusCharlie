import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Fondo {
    private Image imagen;
    private int posX, limizq, limder;

    public Fondo() {
        imagen = new ImageIcon(getClass().getResource("/Resources/background.png")).getImage();
        limizq = 0;
        limder = -2330;
        posX = 0;
    }

    public void mover(int velocidad, int posicion) {
        // Movemos el fondo a la izquierda o a la derecha, según la posición del
        // personaje
        if (velocidad != 0) {
            posX -= velocidad + 0.2;
        }

        if (posX >= limizq) {
            posX = 0;
        }

        if (posX <= limder) {
            posX = limder;
        }

        if (posX == -2330) {
            System.out.println("Ganaste");
        }
    }

    public void dibujar(Graphics g, int personajeX) {
        g.drawImage(imagen, posX, -60, null);
    }

    public int getposX() {
        return posX;
    }

}
