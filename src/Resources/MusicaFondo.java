
package Resources;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public  class MusicaFondo {
    private Clip clip;

    // Metodo creado para reproducir musica infinitimante como musica de fondo
    public void reproducir(String ruta) {
        try {
            File archivo = new File(ruta); // ej: "src/recursos/sonidos/fondo.wav"
            AudioInputStream audio = AudioSystem.getAudioInputStream(archivo);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // música infinita
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Metodo para detener la musica
    public void detener() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
