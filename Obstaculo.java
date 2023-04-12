import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;
import java.util.ArrayList;
import java.awt.Rectangle;

public class Obstaculo {

    private int x, y;
    public static int VelocidadX, VelocidadY;

    private int contador = 0;
    private static final int INTERVALO = 15;
    private boolean caminando;
    private Image imagenParado;
    private Image imagenCaminando;

    private boolean activo = true;

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

    public static void resetear() {
        obstaculos.clear();
        VelocidadX = 0;
    }

    public void mover() {
        x -= VelocidadX;
        caminando = !caminando; // cambia el valor de caminando
    }

    public void dibujar(Graphics g) {
        if (caminando) {
            g.drawImage(imagenCaminando, x, y, null);
        } else if (!caminando) {
            g.drawImage(imagenParado, x, y, null);
        }
    }

    public void actualizarObstaculos(int PosicionPersonajeX, int PosicionPersonajeY) {
        contador++;

        if (contador >= INTERVALO) {
            Random rnd = new Random();
            if (rnd.nextDouble() < 0.14) {
                obstaculos.add(new Obstaculo());
                VelocidadX = 5;
                caminando = true;
            }
            contador = 0;
            caminando = !caminando; // cambio de imagen
        }

        for (int i = obstaculos.size() - 1; i >= 0; i--) {
            Obstaculo obs = obstaculos.get(i);
            obs.mover();

            if (obs.getX() <= 0) {
                obstaculos.remove(i);
            }

            if (obs.colisionaConPersonaje(PosicionPersonajeX, PosicionPersonajeY)) {
                setActivo(false);
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

    public boolean colisionaConPersonaje(int PosicionPersonajeX, int PosicionPersonajeY) {
        Rectangle rectanguloObstaculo = new Rectangle(x, y, 50, 50);
        Rectangle rectanguloPersonaje = new Rectangle(PosicionPersonajeX, PosicionPersonajeY, 30, 30);

        if (rectanguloObstaculo.intersects(rectanguloPersonaje)) {
            System.out.println("Colisión detectada");
            return true;
        }
        return false;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}