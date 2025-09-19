package Resources;




/**
 *
 * @author HOLA
 */
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sonido {
    public static void reproducir(String ruta) {
        try {
            File archivo = new File(ruta); // ejemplo: "src/recursos/sonidos/gema.wav"
            AudioInputStream audio = AudioSystem.getAudioInputStream(archivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
