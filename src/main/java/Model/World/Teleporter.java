package Model.World;

import java.awt.*;

public class Teleporter {
    String name;
    String map_dest_name;
    Point position;
    Point dest_position;

    public Teleporter(Map map, String name, Point p) {
        this.name = map.toString() + " | " + name;
        this.position = p;
        this.dest_position = null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
