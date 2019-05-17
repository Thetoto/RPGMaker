package Model.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    transient public static Tile placeholder = null;
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

    public void setRefImage(BufferedImage refImage) {
        this.refImage = refImage;
    }

    public static Tile getPlaceholder() {
        if (placeholder == null) {
            BufferedImage bi = new BufferedImage(16, 16,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D ig2 = bi.createGraphics();
            ig2.setBackground(Color.WHITE);
            ig2.clearRect(0, 0, 16, 16);
            placeholder = new Tile("PlaceHolder", bi);
        }
        return placeholder;
    }
}
