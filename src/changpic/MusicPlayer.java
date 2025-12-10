package changpic;

import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer extends JFrame {

    private Clip clip;

    public MusicPlayer(String s) {
        playMusic(s + ".wav");
    }

    private void playMusic(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

}
