import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.Random;
import java.util.ArrayList;
import java.awt.Rectangle;

public class ObstaculoSaltarin {

    private int x, y;
    public static int VelocidadX, VelocidadY;
    private boolean saltando;
    private static final int GRAVEDAD = 1;
    private int contador = 0;
    private static final int INTERVALO = 30;

    private boolean caminando;
    private Image imagenParado;
    private Image imagenCaminando;
    private Image imagenSaltando;

    private boolean activo = true;
    private boolean saltarEnPosicionInicial = false; // Nueva variable

    public static ArrayList<ObstaculoSaltarin> obstaculosSaltarines = new ArrayList<ObstaculoSaltarin>();

    public ObstaculoSaltarin() {
        ImageIcon ii1 = new ImageIcon(getClass().getResource("/Resources/obstacleSaltarin1.png"));
        Image img1 = ii1.getImage();
        imagenParado = img1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        ImageIcon ii2 = new ImageIcon(getClass().getResource("/Resources/obstacleSaltarin2.png"));
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

    public synchronized void mover() {
        y += VelocidadY;
        x -= VelocidadX;

        if (y >= 185) {
            y = 185;
            VelocidadY = 0;
            saltando = false;
        } else {
            VelocidadY += GRAVEDAD; // Aplicamos efecto de gravedad
        }

        // Establecer saltarEnPosicionInicial en true cuando el obstáculo esté en la
        // mitad de la pantalla
        if (x < 400 && !saltarEnPosicionInicial) {
            saltarEnPosicionInicial = true;
            saltar();
        }

    }

    public synchronized void dibujar(Graphics g) {
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

    public synchronized void actualizarObstaculos(int PosicionPersonajeX, int PosicionPersonajeY) {

        contador++;

        if (contador >= INTERVALO) {
            Random rnd = new Random();
            if (rnd.nextDouble() < 0.05) {
                obstaculosSaltarines.add(new ObstaculoSaltarin());
                VelocidadX = 5;
                caminando = true;
            }
            contador = 0;
        }

        for (int i = obstaculosSaltarines.size() - 1; i >= 0; i--) {
            ObstaculoSaltarin obs = obstaculosSaltarines.get(i);
            obs.mover();
            if (obs.getX() <= 0) {
                obstaculosSaltarines.remove(i);
            }

            if (obs.colisionaConPersonaje(PosicionPersonajeX, PosicionPersonajeY)) {
                setActivo(false);
            }
        }
    }

    public synchronized void saltar() {
        if (saltarEnPosicionInicial && !saltando) {
            VelocidadY = -18;
            VelocidadX = 10; // Velocidad hacia la derecha
            saltando = true;
        }
    }

    public synchronized static ArrayList<ObstaculoSaltarin> getObstaculos() {
        return obstaculosSaltarines;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized int getY() {
        return y;
    }

    public synchronized int getVelocidadX() {
        return VelocidadX;
    }

    public synchronized int getVelocidadY() {
        return VelocidadY;
    }

    public synchronized boolean colisionaConPersonaje(int PosicionPersonajeX, int PosicionPersonajeY) {
        Rectangle rectanguloObstaculo = new Rectangle(x, y, 50, 50);
        Rectangle rectanguloPersonaje = new Rectangle(PosicionPersonajeX, PosicionPersonajeY, 30, 30);

        if (rectanguloObstaculo.intersects(rectanguloPersonaje)) {
            System.out.println("Colisión detectada");
            return true;
        }
        return false;
    }

    public synchronized boolean isActivo() {
        return activo;
    }

    public synchronized void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void reset() {
        obstaculosSaltarines.clear();
        VelocidadX = 0;
        activo = true;

        for (int i = obstaculosSaltarines.size() - 1; i >= 0; i--) {
            ObstaculoSaltarin obs = obstaculosSaltarines.get(i);

            if (obs.getX() <= 0) {
                obstaculosSaltarines.remove(i);
            }
        }
    }

}