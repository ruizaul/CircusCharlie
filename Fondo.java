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
        limiteIzquierdo = 200;
    }

    public void mover(int velocidad) {
        if (posX > limiteIzquierdo) {
            posX -= velocidad;
        }
        if (posX < limiteIzquierdo - imagenAncho) {
            posX = limiteIzquierdo - imagenAncho;
        }
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
