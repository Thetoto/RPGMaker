package Model.World;

import Tools.ActionManager;

import java.awt.*;
import java.util.HashMap;
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
        for (int i = 0; i < dimension.width * dimension.height; i++) {
            background.add(i, Tile.getPlaceholder());
        }
    }

    public Map(Map map) {
        this.name = map.name;
        this.dimension = new Dimension(map.dimension);
        this.background = new Vector<Tile>(map.background);
        this.foreground = new HashMap<Point, Tile>(map.foreground);
    }

    @Override
    public String toString() {
        return name;
    }

    public void draw(Tile currentTile, Point in, Point out) {
        ActionManager.saveAction(new Map(this));
        if (out == null)
            out = in;
        if (in != null) {
            for (int x = in.x; x <= out.x; x++) {
                for (int y = in.y; y <= out.y; y++) {
                    if (x < 0 || y < 0)
                        continue;
                    if (x >= dimension.width || y >= dimension.height)
                        continue;
                    if (currentTile instanceof ImportedTile) {
                        Point isOccupied = isOccupied(x, y, ((ImportedTile)currentTile).getDimention());
                        if (currentTile.geName().equals("eraser.png")) {
                            if (isOccupied != null)
                                foreground.remove(isOccupied);
                            continue;
                        }
                        if (isOccupied != null) {
                            continue;
                        }
                        foreground.put(new Point(x, y), currentTile);
                    } else {
                        int pos = x + y * dimension.width;
                        if (currentTile.geName().equals("eraser.png"))
                            background.set(pos, Tile.getPlaceholder());
                        else
                            background.set(pos, currentTile);
                    }
                }
            }
        }
    }

    public Point isOccupied(int x, int y, Dimension dimension) {
        for (int i = x; i < x + dimension.width; i++) {
            for (int j = y; j < y + dimension.height; j++) {
                for (var item : foreground.entrySet()) {
                    ImportedTile tile = (ImportedTile)item.getValue();
                    Point pt = item.getKey();
                    if (i >= pt.x && i < pt.x + tile.getWidth() && j >= pt.y && j < pt.y + tile.getHeight()) {
                        return pt;
                    }
                }
            }
        }
        return null;
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
    public Vector<Tile> getBackgroundSet() {
        return background;
    }

    public Dimension getDim() {
        return dimension;
    }
}
