package Model.World;

import java.awt.image.BufferedImage;

public class Foreground {
    Tile t;
    String name;
    public boolean isBreakable;
    public boolean isHided;
    public boolean isPickable;

    public Foreground(Tile t) {
        this.t = t;
        name = t.getName();
        isBreakable = false;
        isHided = false;
        isPickable = false;
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
