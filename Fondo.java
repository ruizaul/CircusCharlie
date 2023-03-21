import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Fondo {
    private Image imagen;
    private Image imagen2;
    private int posX;

    public Fondo() {
        imagen = new ImageIcon(getClass().getResource("/Resources/background.png")).getImage();
        imagen2 = new ImageIcon(getClass().getResource("/Resources/background.png")).getImage();
        posX = 0;
    }

    public void mover(int velocidad) {
        posX -= velocidad;
        if (posX <= -imagen.getWidth(null)) {
            posX += imagen.getWidth(null);
        }
    }

    public void dibujar(Graphics g, int personajeX) {
        int offsetX = posX - personajeX;
        g.drawImage(imagen, offsetX, 0, null);
        g.drawImage(imagen2, offsetX + imagen.getWidth(null), 0, null);
    }
}
