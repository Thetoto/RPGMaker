package Model.World;

import javax.sound.sampled.*;

public class Music {
    String name;
    AudioInputStream audioStream;
    static Clip clip = null;

    public Music(String name, AudioInputStream inputStream) throws Exception {
        this.name = name;
        this.audioStream = inputStream;
        audioStream.mark(Integer.MAX_VALUE);
        if (clip == null)
            clip = AudioSystem.getClip();
    }

    public static void pause() {
        clip.stop();
    }

    public static void restart() {
            clip.start();
    }

    public static void destroy() {
        if (clip != null)
            clip.close();
        clip = null;
    }

    public static void stop() {
        try {
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public void play() throws Exception {
        clip = null;
        audioStream.reset();
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.setFramePosition(0);
        clip.setMicrosecondPosition(0);
        clip.start();
    }
}
