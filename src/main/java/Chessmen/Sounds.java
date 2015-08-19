package Chessmen; /**
 * Created by Nico on 13.08.2015.
 */

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sounds {

    int i = 0;
    private HashMap<String, ArrayList<Clip>> sounds = new HashMap<>();
    private List<Clip> clips = new ArrayList<>();

    public Sounds(String str) {
        File file = new File(str);
        try {
            loadSound(file);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public Sounds(File file) {
        try {
            loadSound(file);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public Sounds() {
        /*try {
            loadSounds(new File("C:\\Users\\Nico\\IdeaProjects\\Chess\\src\\main\\resources\\sounds\\drums"));
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }*/
    }

    public void playSound() {
        clips.get(i % 10).stop();
        clips.get(i % 10).setFramePosition(0);
        clips.get(i % 10).start();
        i++;
    }

    private void loadSound(File file) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (file.isFile()) {
            for (int i = 0; i < 10; i++) {
                AudioInputStream stream;
                AudioFormat format;
                DataLine.Info info;
                Clip clip;

                stream = AudioSystem.getAudioInputStream(file);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);

                clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);

                clips.add(clip);
            }
        }
    }

    private void loadSounds(File file) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (file.isFile()) {
            for (int i = 0; i < 10; i++) {
                AudioInputStream stream;
                AudioFormat format;
                DataLine.Info info;
                Clip clip;

                stream = AudioSystem.getAudioInputStream(file);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);

                clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);

                ArrayList<Clip> list;
                if ((list = sounds.get(file.getParentFile().getName())) == null) {
                    list = new ArrayList<Clip>();
                    list.add(clip);
                    sounds.put(file.getParentFile().getName(), list);
                } else {
                    list.add(clip);
                }
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    loadSounds(f);
                }
            }
        }
    }
}
