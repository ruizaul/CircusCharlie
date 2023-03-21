import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Fondo {
    private Image imagen;
    private int posX;
    private int imagenAncho;
    private int limiteIzquierdo;
 

    public Fondo() {
        imagen = new ImageIcon(getClass().getResource("/Resources/background.png")).getImage();
        imagenAncho = imagen.getWidth(null);
        posX = 0;
        limiteIzquierdo = 0;
    }

    public void mover(int velocidad, int posicion) {
        // Detener el fondo en el límite izquierdo
        if (velocidad != 0) {
            posX -= velocidad * 2;
        } 

        if (posicion <= 0) {
            posX = posicion;
        }

        System.out.println(velocidad);
        System.out.println(posicion);

        if (posX == -1000) {
            System.out.println("felicidades 1000 puntos");
        }
        System.out.println(posX);

    }

    public void dibujar(Graphics g, int personajeX) {
        int offsetX = posX - personajeX;
        while (offsetX < 0) {
            offsetX += imagenAncho;
        }
        g.drawImage(imagen, offsetX - imagenAncho, 0, null);
        g.drawImage(imagen, offsetX, 0, null);
        g.drawImage(imagen, offsetX + imagenAncho, 0, null);
    }
}
