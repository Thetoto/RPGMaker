package Model.World;

import java.awt.image.BufferedImage;

public class Foreground {
    Tile t;
    String name;
    boolean is_breakable;
    boolean is_hided;
    boolean is_pickable;

    public Foreground(Tile t) {
        this.t = t;
        name = t.getName();
        is_breakable = false;
        is_hided = false;
        is_pickable = false;
    }

    public BufferedImage get() {
        return t.get();
    }

    public Tile getTile() {
        return t;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
