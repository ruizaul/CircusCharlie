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

        System.out.println("posicion: " + posicion);
        System.out.println("posX: " + posX);

        if (posX == -1000) {
            System.out.println("Felicidades 1000 puntos");
        }
    }

    public void dibujar(Graphics g, int personajeX) {
        g.drawImage(imagen, posX, -80, null);
    }
}
