import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;
import java.util.ArrayList;

public class Obstaculo {

    private int x, y;
    public static int VelocidadX, VelocidadY;

    private int contador = 0;
    private static final int INTERVALO = 10;

    private boolean caminando;
    private Image imagenParado;
    private Image imagenCaminando;

    public static ArrayList<Obstaculo> obstaculos = new ArrayList<Obstaculo>();

    public Obstaculo() {
        ImageIcon ii1 = new ImageIcon(getClass().getResource("/Resources/obstacle1.png"));
        Image img1 = ii1.getImage();
        imagenParado = img1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        ImageIcon ii2 = new ImageIcon(getClass().getResource("/Resources/obstacle2.png"));
        Image img2 = ii2.getImage();
        imagenCaminando = img2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        caminando = false;
        x = 800;
        y = 185;
        VelocidadX = 0;

    }

    public void mover() {
        x -= VelocidadX;
    }

    public void dibujar(Graphics g) {
        if (caminando) {
            g.drawImage(imagenParado, x, y, null);
        }

        if (!caminando) {
            g.drawImage(imagenCaminando, x, y, null);
        }
    }

    public void actualizarObstaculos() {
        contador++;

        if (contador >= INTERVALO) {
            Random rnd = new Random();
            if (rnd.nextDouble() < 0.11) {
                obstaculos.add(new Obstaculo());
                VelocidadX = 5;
                caminando = true;
            }
            contador = 0;
        }

        for (int i = obstaculos.size() - 1; i >= 0; i--) {
            Obstaculo obs = obstaculos.get(i);
            obs.mover();
            if (obs.getX() <= 0) {
                obstaculos.remove(i);
            }

        }
    }

    public static ArrayList<Obstaculo> getObstaculos() {
        return obstaculos;
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
