import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;
import java.util.ArrayList;

public class Obstaculo {

    private int x, y;
    public static int VelocidadX, VelocidadY;
    private boolean saltando;
    private static final int GRAVEDAD = 1;
    private int contador = 0;
    private static final int INTERVALO = 10;

    private boolean caminando;
    private Image imagenParado;
    private Image imagenCaminando;
    private Image imagenSaltando;
    public static ArrayList<Obstaculo> obstaculos = new ArrayList<Obstaculo>();

    public Obstaculo() {
        ImageIcon ii1 = new ImageIcon(getClass().getResource("/Resources/obstacle1.png"));
        Image img1 = ii1.getImage();
        imagenParado = img1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        ImageIcon ii2 = new ImageIcon(getClass().getResource("/Resources/obstacle2.png"));
        Image img2 = ii2.getImage();
        imagenCaminando = img2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        ImageIcon ii3 = new ImageIcon(getClass().getResource("/Resources/obstacleJump.png"));
        Image img3 = ii3.getImage();
        imagenSaltando = img3.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        caminando = false;
        x = 800;
        y = 185;
        VelocidadX = 0;
        VelocidadY = 0;
        saltando = false;
    }

    public void mover() {
        y += VelocidadY;
        x -= VelocidadX;

        if (y >= 185) {
            y = 185;
            VelocidadY = 0;
            saltando = false;
        } else {
            VelocidadY += GRAVEDAD; // Aplicamos efecto de gravedad
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

    public void actualizarObstaculos() {
        VelocidadX = 5;
        caminando = true;
        contador++;

        if (contador >= INTERVALO) {
            Random rnd = new Random();
            if (rnd.nextDouble() < 0.1) {
                obstaculos.add(new Obstaculo());
            }
            contador = 0;
        }

        for (int i = obstaculos.size() - 1; i >= 0; i--) {
            Obstaculo obs = obstaculos.get(i);
            obs.mover();
            if (obs.getX() <= 0) {
                obstaculos.remove(i);
            }
            // Determinar si el obstáculo actual saltará hacia la izquierda

        }
    }

    public void saltar() {
        if (!saltando) {
            VelocidadY = -1;
            VelocidadX = -40; // Velocidad hacia la derecha
            saltando = true;
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
