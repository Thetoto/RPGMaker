package Model.World;

import java.awt.*;
import java.util.Vector;

public class Player {
    private Map map;
    private Point position;
    private Animation anim;

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

    public void setPosition(Point point, Map map) {
        position = point;
        this.map = map;
    }

    public void setAnim() {
        anim = new Animation();
        if (anim != null && anim.tiles == null)
            anim = null;
    }
}
