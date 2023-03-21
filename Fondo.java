import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Fondo {
    private Image imagen;
    private int posX;
    private int imagenAncho;
    

    public Fondo() {
        imagen = new ImageIcon(getClass().getResource("/Resources/background.png")).getImage();
        imagenAncho = imagen.getWidth(null);
        posX = 0;
        
    }

    public void mover(int velocidad) {

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
