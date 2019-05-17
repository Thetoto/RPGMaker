package Model.World;

import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class Map {
    String name;
    Dimension dimension;
    Vector<Tile> background;
    HashMap<Point, Tile> foreground; // Tile but it's a ImportedTile. Point is top left corner.

    public Map(Dimension dimension, String name) {
        this.name = name;
        this.dimension = dimension;
        this.background = new Vector<>(dimension.width * dimension.height);
        this.foreground = new HashMap<>();
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
        if (out == null)
            out = in;
        if (in != null) {
            for (int x = in.x; x <= out.x; x++) {
                for (int y = in.y; y <= out.y; y++) {
                    if (currentTile instanceof ImportedTile) {
                        if (isOccupied(x, y, ((ImportedTile)currentTile).getDimention()))
                            continue;
                        foreground.put(new Point(x, y), currentTile);
                    } else {
                        if (x < 0 || y < 0)
                            continue;
                        if (x >= dimension.width || y >= dimension.height)
                            continue;
                        int pos = x + y * dimension.width;
                        background.set(pos, currentTile);
                    }
                }
            }
        }
    }

    public boolean isOccupied(int x, int y, Dimension dimension) {
        for (int i = x; i < x + dimension.width; i++) {
            for (int j = y; j < y + dimension.height; j++) {
                for (var item : foreground.entrySet()) {
                    ImportedTile tile = (ImportedTile)item.getValue();
                    Point pt = item.getKey();
                    if (i >= pt.x && i < pt.x + tile.getWidth() && j >= pt.y && j < pt.y + tile.getHeight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Tile getTile(Point pt) {
        return getTile(pt.x, pt.y);
    }
    public Tile getTile(int x, int y) {
        return background.get(x + dimension.width * y);
    }

    public HashMap<Point, Tile> getForegroundSet() {
        return foreground;
    }

    public Dimension getDim() {
        return dimension;
    }
}
