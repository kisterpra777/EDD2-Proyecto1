
package Resources;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public  class MusicaFondo {
    private Clip clip;

    public void reproducir(String ruta) {
        try {
            File archivo = new File(ruta); // ej: "src/recursos/sonidos/fondo.wav"
            AudioInputStream audio = AudioSystem.getAudioInputStream(archivo);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // 🔁 música infinita
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
