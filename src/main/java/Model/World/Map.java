package Model.World;

import Tools.ActionManager;

import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class Map {
    String name;
    Dimension dimension;
    Vector<Tile> background;
    Vector<Boolean> walkable;
    HashMap<Point, Tile> foreground; // Tile but it's a ImportedTile. Point is top left corner.
    Vector<Teleporter> teleporters;

    public Map(Dimension dimension, String name) {
        this.name = name;
        this.dimension = dimension;
        this.background = new Vector<>(dimension.width * dimension.height);
        this.walkable = new Vector<>(dimension.width * dimension.height);
        this.foreground = new HashMap<>();
        this.teleporters = new Vector<>();
        for (int i = 0; i < dimension.width * dimension.height; i++) {
            background.add(i, Tile.getPlaceholder());
            walkable.add(true);
        }
    }

    public Map(Map map) {
        this.name = map.name;
        this.dimension = new Dimension(map.dimension);
        this.background = new Vector<Tile>(map.background);
        this.foreground = new HashMap<Point, Tile>(map.foreground);
        this.teleporters = new Vector<Teleporter>(map.teleporters);
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
                        if (currentTile.getName().equals("eraser.png")) {
                            if (isOccupied != null)
                                removeFore(isOccupied);
                            continue;
                        }
                        if (isOccupied != null) {
                            continue;
                        }
                        setFore(x, y, (ImportedTile)currentTile);
                    } else {
                        if (currentTile.getName().equals("eraser.png"))
                            setTile(x, y, Tile.getPlaceholder());
                        else
                            setTile(x, y, currentTile);
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

    public Tile getTile(int x, int y) {
        return background.get(x + dimension.width * y);
    }
    public void setTile(int x, int y, Tile tile) {
        background.set(x + dimension.width * y, tile);
        setWalkable(x, y, tile.defaultWalkable);
    }

    public void setFore(int x, int y, ImportedTile tile) {
        foreground.put(new Point(x, y), tile);

        for (int i = x; i < x + tile.getWidth(); i++) {
            for (int j = y; j < y + tile.getHeight(); j++) {
                setWalkable(i, j, false);
            }
        }
    }
    private void removeFore(Point pt) {
        ImportedTile imp = (ImportedTile)foreground.get(pt);
        Dimension dim = new Dimension(imp.getWidth(), imp.getHeight());
        foreground.remove(pt);

        for (int i = pt.x; i < pt.x + dim.getWidth(); i++) {
            for (int j = pt.y; j < pt.y + dim.getHeight(); j++) {
                setWalkable(i, j, true);
            }
        }
    }


    public boolean getWalkable(int x, int y) {
        return walkable.get(x + dimension.width * y);
    }

    public void setWalkable(int x, int y, boolean bool) {
        if (x < 0 || y < 0)
            return;
        if (x >= dimension.width || y >= dimension.height)
            return;
        walkable.set(x + dimension.width * y, bool);
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

    public void addTeleporter(Point point) {
        teleporters.add(new Teleporter(this, "first", point));
    }

    public Vector<Teleporter> getTeleporters() {
        return this.teleporters;
    }
}
