package Model.World;

import java.awt.image.BufferedImage;

public class Tile {
    BufferedImage image;

    public Tile(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage get() {
        return image;
    }
}
