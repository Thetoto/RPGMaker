package Model.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    String image;
    // transient = not serialized in Gson
    transient BufferedImage refImage = null;

    public Tile(String name, BufferedImage image) {
        this.image = name;
        this.refImage = image;
    }

    public Tile(BufferedImage image) {
        this.image = "Internal Image";
        this.refImage = image;
    }

    public String geName() {
        return image;
    }

    public BufferedImage get() {
        return refImage;
    }
}
