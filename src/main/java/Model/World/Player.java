package Model.World;

import java.awt.*;
import java.util.Vector;

public class Player {
    private Map map;
    private Point position;
    private Vector<Tile> anim;

    public Player() {
        position = new Point(0,0);
        anim = null;
    }

    public Point getPosition() {
        return position;
    }

    public Map getMap() {
        return map;
    }
}
