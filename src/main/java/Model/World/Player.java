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
}
