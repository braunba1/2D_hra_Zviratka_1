package music;

import javax.sound.sampled.*;
import java.io.IOException;

public class Sound {

  private Clip clip;

  public Sound(String resourcePath) {
    try {
      AudioInputStream audio =
          AudioSystem.getAudioInputStream(
              Sound.class.getResource(resourcePath)
          );

      clip = AudioSystem.getClip();
      clip.open(audio);

    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  public void playLoop() {
    if (clip == null) return;
    clip.setFramePosition(0);
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void playOnce() {
    if (clip == null) return;

    if (clip.isRunning()) return;

    clip.setFramePosition(0);
    clip.start();
  }

}
