package Model.World;

import Model.Editor.EditorState;
import Tools.Tools;

import java.awt.geom.Point2D;

public class Player {
    private int mapId;
    private Point2D.Double position;
    private Animation anim;
    private Direction direction;

    public Player() {
        position = new Point2D.Double(0,0);
        direction = Direction.DOWN;
        anim = null;
    }

    public Player(Player player) {
        this.mapId = player.mapId;
        this.position = (Point2D.Double)player.position.clone();
        this.anim = player.anim;
        this.direction = player.direction;
    }

    public Point2D getPosition() {
        return position;
    }

    public int getMapId() {
        return mapId;
    }

    public void setPosition(Point2D.Double point, int map) {
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

    public void move(Direction dir, int delta_time) {
        this.direction = dir;
        switch (dir) {
            case DOWN:
                Tools.translate2D(position, 0, 0.01 * delta_time);
                break;
            case LEFT:
                Tools.translate2D(position,-0.01 * delta_time, 0);
                break;
            case RIGHT:
                Tools.translate2D(position,0.01 * delta_time, 0);
                break;
            case UP:
                Tools.translate2D(position,0, -0.01 * delta_time);
                break;
        }
    }

    public Direction getDirection() {
        return direction;
    }
}
