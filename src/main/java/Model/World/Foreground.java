package Model.World;

import java.awt.image.BufferedImage;

public class Foreground {
    Tile t;
    String name;
    public boolean isBreakable;
    public boolean isHided;
    public boolean isPickable;
    public String breaker;

    public Foreground(Tile t) {
        this.t = t;
        name = t.toString();
        isBreakable = false;
        isHided = false;
        isPickable = false;
        breaker = "";
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
