package Model.World;

import java.awt.*;
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
    }

    @Override
    public String toString() {
        return name;
    }
}
