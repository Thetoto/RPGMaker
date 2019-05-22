package Model.World;

import Model.Editor.EditorState;

import java.awt.*;
import java.util.Vector;

public class Player {
    private int mapId;
    private Point position;
    private Animation anim;
    private Direction direction;

    public Player() {
        position = new Point(0,0);
        direction = Direction.UP;
        anim = null;
    }

    public Point getPosition() {
        return position;
    }

    public int getMapId() {
        return mapId;
    }

    public void setPosition(Point point, int map) {
        position = point;
        this.mapId = map;
    }

    public void setAnim() {
        if (EditorState.getInstance().tilesState.currentTile instanceof Animation)
            anim = (Animation)EditorState.getInstance().tilesState.currentTile;
    }
    public void setAnim(Animation anim) {
        this.anim = anim;
    }

    public Animation getAnim() {
        return anim;
    }

    public void move(Direction dir) {
        System.out.println(dir + " : " + position);
        this.direction = dir;
        switch (dir) {
            case DOWN:
                position.translate(0, 1);
                break;
            case LEFT:
                position.translate(-1, 0);
                break;
            case RIGHT:
                position.translate(1, 0);
                break;
            case UP:
                position.translate(0, -1);
                break;
        }
        System.out.println("After move " + dir + " : " + position);
    }
}
