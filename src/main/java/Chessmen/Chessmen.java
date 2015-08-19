package Chessmen;

import java.awt.image.BufferedImage;

/**
 * Created by Nico on 14.08.2015.
 */
public class Chessmen {
    ChessmenEnum type;
    Sounds sound;
    BufferedImage image;

    public Chessmen(ChessmenEnum type) {
        this.type = type;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Sounds getSound() {
        return sound;
    }

    public void setSound(Sounds sound) {
        this.sound = sound;
    }

    public ChessmenEnum getType() {
        return type;
    }

    public void setType(ChessmenEnum type) {
        this.type = type;
    }

    public void playSound() {
        if (type != ChessmenEnum.free)
            sound.playSound();
    }


}
