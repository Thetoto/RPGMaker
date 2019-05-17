package Model.Editor;

import Model.World.Map;

import java.awt.*;
import java.util.Observable;

public class MapState extends Observable {
    public Map currentMap;

    public Point selectionIn;
    public Point selectionOut;

    public MapState() {
    }

    public void mousePreview(Point in, Point out) {
        if (in != null) {
            int minX = in.x < out.x ? in.x : out.x;
            int maxX = in.x > out.x ? in.x : out.x;
            int minY = in.y < out.y ? in.y : out.y;
            int maxY = in.y > out.y ? in.y : out.y;
            selectionIn = new Point(minX, minY);
            selectionOut = new Point(maxX, maxY);
        } else {
            selectionIn = null;
            selectionOut = null;
        }
        setChanged();
        notifyObservers("mousePreview");
    }

    public void mouseClick() {
        if (EditorState.getInstance().toolsState.currentTools == ToolsEnum.TILES) {
            currentMap.draw(EditorState.getInstance().tilesState.currentTile, selectionIn, selectionOut);
            mousePreview(null, null);
            setChanged();
            notifyObservers("Load Me");
        }
    }

    public void updateMap() {
        setChanged();
        notifyObservers("Load Me");
    }

    public void updateMap(Map map) {
        this.currentMap = map;
        updateMap();
    }
}
