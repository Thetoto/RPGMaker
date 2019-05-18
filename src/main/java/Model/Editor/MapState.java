package Model.Editor;

import Model.World.Map;
import Model.World.Player;
import Tools.ActionManager;
import Tools.CursorManager;
import Tools.ThreadLauncher;

import java.awt.*;
import java.util.Observable;

public class MapState extends Observable {
    public Map currentMap;

    public Point selectionIn;
    public Point selectionOut;

    public Point mousePos;
    private Mode mode;
    private Player player;

    public MapState() {
        mode = Mode.DEFAULT;
        player = null;
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
        if (mode == Mode.PLAYER) {
            if (selectionIn.equals(selectionOut)) {
                player.setPosition(new Point(selectionIn), currentMap);
                setMode(Mode.DEFAULT);
            }
            return;
        }
        if (mode == Mode.TELEPORTER) {
            if (selectionIn.equals(selectionOut)) {
                currentMap.addTeleporter(selectionIn);
                setMode(Mode.DEFAULT);
            }
            return;
        }
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
        if (selectionIn != null)
            return;
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

    public void setMode(Mode mode) {
        if (mode == Mode.DEFAULT) {
            CursorManager.setCursor(Cursor.DEFAULT_CURSOR);
        }
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
