package Model.World;

import Model.Editor.EditorState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class Foreground {
    Tile t;
    String name;
    public boolean isBreakable;
    public boolean isHided;
    public boolean isPickable;
    public boolean isRemoved;
    public String breaker;

    public Foreground(Tile t) {
        this.t = t;
        name = t.toString();
        isBreakable = false;
        isHided = false;
        isPickable = false;
        isRemoved = false;
        breaker = "";
    }

    public BufferedImage get() {
        return t.get();
    }

    public Tile getTile() {
        return t;
    }

    public String getTileName() {
        return t.toString();
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public Point getPoint() {
        var forSet = EditorState.getInstance().mapState.currentMap.getForegroundSet();
        for(Point p : forSet.keySet()) {
            if (forSet.get(p) == this)
                return p;
        }
        return null;
    }
}
