package Model.World;

import Tools.Tools;

import java.awt.*;
import java.awt.geom.Point2D;

public class NPC {
    private String name;
    private String message;
    private Direction direction;
    private Animation anim;
    private Point2D.Double coordinates;
    private boolean isMoving;

    public NPC(Animation anim, Point coordinates) {
        this.anim = anim;
        this.direction = Direction.DOWN;
        this.message = "Hello!";
        this.coordinates = new Point2D.Double(coordinates.x, coordinates.y);
        isMoving = false;
    }

    @Override
    public String toString() {
        if (name == null)
            return anim.getName();
        return name;
    }

    public Animation getAnimation() {
        return anim;
    }

    public String getName() {
        if (name == null)
            return anim.getName();
        return name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Point2D.Double getPoint() {
        return this.coordinates;
    }

    public void setAnimation(Animation animation) {
        anim = animation;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
        System.out.println(moving);
    }

    public void move(Direction dir, int delta_time) {
        this.direction = dir;
        switch (dir) {
            case DOWN:
                Tools.translate2D(coordinates, 0, 0.01 * delta_time);
                break;
            case LEFT:
                Tools.translate2D(coordinates,-0.01 * delta_time, 0);
                break;
            case RIGHT:
                Tools.translate2D(coordinates,0.01 * delta_time, 0);
                break;
            case UP:
                Tools.translate2D(coordinates,0, -0.01 * delta_time);
                break;
        }
    }

    public boolean moveNPC(Direction dir, int delta_time, Map map) {
        if (!map.checkBoundsNPC(this, dir, coordinates, delta_time)) {
            this.move(dir, delta_time);
            return true;
        }
        return false;
    }
}
