package Model.World;

import java.awt.*;

public class Teleporter {
    String name;
    String mapDestName;
    Point position;
    Point destPosition;

    public Teleporter(Map map, String name, Point p) {
        this.name = map.toString() + " | " + name;
        this.position = p;
        this.destPosition = null;
        this.mapDestName = null;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void setDest(Map map, Point point) {
        mapDestName = map.toString();
        destPosition = new Point(point);
    }

    public String getMapDestName() {
        return mapDestName;
    }

    public Point getPointDest() {
        return destPosition;
    }
}
