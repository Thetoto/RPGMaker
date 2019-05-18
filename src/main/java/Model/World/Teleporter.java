package Model.World;

import java.awt.*;

public class Teleporter {
    String name;
    Point position;
    Teleporter dest;

    public Teleporter(Map map, String name, Point p) {
        this.name = map.toString() + "_" + name;
        this.position = p;
        this.dest = null;
    }

    public Teleporter(Map map, String name, Point p, Teleporter t) {
        this.name = map.toString() + "_" + name;
        this.position = p;
        this.dest = t;
    }
}
