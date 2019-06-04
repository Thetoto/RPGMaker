package Model.Editor;

import Model.World.*;
import Tools.ActionManager;
import Tools.CursorManager;
import Tools.ThreadLauncher;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Observable;

public class MapState extends Observable {
    public Map currentMap;

    public Point selectionIn;
    public Point selectionOut;
    public Point updateRequestIn;
    public Point updateRequestOut;

    public Point mousePos;
    private Mode mode;
    private Player player;
    private Teleporter currentTeleporter;
    private NPC currentNPC;

    public double zoomPercent = 1.;

    public MapState() {
        mode = Mode.DEFAULT;
        player = null;
        currentTeleporter = null;
        currentNPC = null;
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
                player.setPosition(new Point2D.Double(selectionIn.x, selectionIn.y), currentMap.id);
                setMode(Mode.DEFAULT);
                deselect();
                EditorState.getInstance().toolsState.setCurrentTools(ToolsEnum.PLAYER);
            }
            return;
        }
        if (mode == Mode.TELEPORTER) {
            if (selectionIn.equals(selectionOut)) {
                Teleporter res = currentMap.addTeleporter(selectionIn);
                setMode(Mode.DEFAULT);
                deselect();
                setChanged();
                notifyObservers("Update Map");
                if (res != null) {
                    setCurrentTeleporter(res);
                    EditorState.getInstance().toolsState.setCurrentTools(ToolsEnum.TELEPORTER);
                }
            }
            return;
        }
        if (mode == Mode.TELEPORTERDEST) {
            if (selectionIn.equals(selectionOut) && currentTeleporter != null) {
                currentTeleporter.setDest(currentMap, selectionIn);
                setMode(Mode.DEFAULT);
                deselect();
                setChanged();
                notifyObservers("Set Teleporter Dest");
            }
            return;
        }
        if (EditorState.getInstance().toolsState.currentTools == ToolsEnum.TILES) {
            updateRequestIn = selectionIn;
            updateRequestOut = selectionOut;
            currentMap.draw(EditorState.getInstance().tilesState.currentTile, selectionIn, selectionOut);
            mousePreview(null, null);
            updateMap(false);
            if (EditorState.getInstance().tilesState.currentTile instanceof Animation) {
                setChanged();
                notifyObservers("Update Map");
            }
        }
    }

    public void updateMap(boolean forceRedraw) {
        ThreadLauncher.execute(() -> {
            mousePreview(null, null);
            setChanged();
            if (forceRedraw)
                notifyObservers("Load Me");
            else
                notifyObservers("Update Me");
            updateRequestIn = null;
            updateRequestOut = null;
        });
    }

    public void updateMap(Map map) {
        if (currentMap != null && currentMap.toString() == map.toString()) {
            var world = EditorState.getInstance().world;
            int i = world.maps.indexOf(currentMap);
            world.maps.set(i, map);
            setChanged();
            notifyObservers("Update Map");
        } else {
            ActionManager.reset();
        }
        this.currentMap = map;
        updateMap(true);
    }

    public void setShowWalk(boolean b) {
        EditorState.getInstance().showWalk = b;
        if (currentMap == null)
            return;
        setChanged();
        if (b)
            notifyObservers("Show Walk");
        else
            notifyObservers("Hide Walk");
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

    public void deselect() {
        selectionIn = null;
        selectionOut = null;
        mousePreview(null, null);
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
    public void setCurrentTeleporter(Teleporter teleporter) {
        currentTeleporter = teleporter;
    }

    public Teleporter getCurrentTeleporter() {
        return currentTeleporter;
    }

    public void forceWalkable(boolean b) {
        if (currentMap == null || selectionIn == null || selectionOut == null)
            return;
        for (int x = selectionIn.x; x <= selectionOut.x; x++) {
            for (int y = selectionIn.y; y <= selectionOut.y; y++) {
                currentMap.setWalkable(x, y, b);
            }
        }
        if (EditorState.getInstance().showWalk) {
            setChanged();
            notifyObservers("Show Walk");
        }
        mousePreview(null, null);
    }

    public void setBackgroundCurrentTile() {
        currentMap.setBackgroundTile(EditorState.getInstance().tilesState.currentTile);
        setChanged();
        notifyObservers("Update Background");
    }

    public void zoomChange(boolean positive) {
        if (positive)
            zoomPercent *= 1.3;
        else
            zoomPercent *= 0.7;
        setChanged();
        notifyObservers("Zoom Update");
    }

    public NPC getCurrentNPC() {
        return currentNPC;
    }

    public void setCurrentNPC(NPC npc) {
        currentNPC = npc;
    }

    public void deleteTeleport() {
        currentMap.getTeleporters().remove(currentTeleporter);
        EditorState.getInstance().toolsState.setCurrentTools(ToolsEnum.NONE);
        setChanged();
        notifyObservers("Update Map");
    }

    public void deleteNPC() {
        currentMap.getNpcs().remove(currentNPC);
        updateRequestIn = new Point((int)currentNPC.getPoint().x, (int)currentNPC.getPoint().y);
        updateRequestOut = new Point(updateRequestIn.x + currentNPC.getAnimation().getSize(), updateRequestIn.y + currentNPC.getAnimation().getSize());
        EditorState.getInstance().toolsState.setCurrentTools(ToolsEnum.NONE);
        setChanged();
        notifyObservers("Update Map");
        updateMap(false);
    }
}
