package Model.Editor;

import Model.World.Map;
import Tools.ActionManager;
import Tools.ThreadLauncher;

import java.awt.*;
import java.util.Observable;

public class MapState extends Observable {
    public Map currentMap;

    public Point selectionIn;
    public Point selectionOut;

    public Point mousePos;

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
            updateMap();
        }
    }

    public void updateMap() {
        ThreadLauncher.execute(() -> {
            mousePreview(null, null);
            setChanged();
            notifyObservers("Load Me");
        });
    }

    public void updateMap(Map map) {
        this.currentMap = map;
        updateMap();
    }

    public void mouseOver(Point over) {
        mousePos = over;
        setChanged();
        notifyObservers("mouseOver");
    }

    public void undo() {
        Map map = ActionManager.undo(new Map(currentMap));
        if (map != null)
            updateMap(map);
    }

    public void redo() {
        Map map = ActionManager.redo(new Map(currentMap));
        if (map != null)
            updateMap(map);
    }
}
