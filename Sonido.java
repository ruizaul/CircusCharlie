import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sonido {

    private Clip clip;

    public Sonido(String nombreArchivo) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(nombreArchivo)));
        } catch (Exception e) {
            System.out.println("Error al cargar el sonido: " + e.getMessage());
        }
    }

    public void reproducir(boolean enBucle) {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            if (enBucle) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        }
    }

    public void detener() {
        if (clip != null) {
            clip.stop();
        }
    }

}
