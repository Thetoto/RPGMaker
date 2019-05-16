package Model.World;

import Model.Editor.ImportedTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Vector;

public class Map {
    String name;
    Dimension dimension;
    Vector<Tile> foreground;
    Vector<Tile> background;

    public Map(Dimension dimension, String name) {
        this.name = name;
        this.dimension = dimension;
        this.foreground = new Vector<Tile>(dimension.width * dimension.height);
        this.background = new Vector<Tile>(dimension.width * dimension.height);
        BufferedImage placeholder = getPlaceholder();
        for (int i = 0; i < dimension.width * dimension.height; i++) {
            background.add(i, new Tile("PlaceHolder", placeholder));
        }
    }

    public BufferedImage getPlaceholder() {
        BufferedImage bi = new BufferedImage(16, 16,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D ig2 = bi.createGraphics();
        ig2.setBackground(Color.WHITE);
        ig2.clearRect(0, 0, 16, 16);
        return bi;
    }

    @Override
    public String toString() {
        return name;
    }

    public void draw(Tile currentTile, Point in, Point out) {
        if (in != null && out != null) {
            for (int x = in.x; x <= out.x; x++) {
                for (int y = in.y; y <= out.y; y++) {
                    if (currentTile instanceof ImportedTile) {
                        // Foreground tile handling
                        // TODO : Need to change vector to another thing
                    } else {
                        System.out.println(x + " - " + y);
                        if (x > dimension.width || y > dimension.height)
                            continue;
                        int pos = x + y * dimension.width;
                        background.set(pos, currentTile);
                    }
                }
            }
        }
    }

    public Tile getTile(int x, int y) {
        return background.get(x + dimension.width * y);
    }

    public Dimension getDim() {
        return dimension;
    }
}
