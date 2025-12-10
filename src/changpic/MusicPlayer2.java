package changpic;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer2{

    private Clip clip;

    public MusicPlayer2(String s) {
        playMusic(s + ".wav");
    }

    private void playMusic(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.setFramePosition(0); 
                    clip.start();
                }
            });

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
}
