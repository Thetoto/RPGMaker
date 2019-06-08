package Model.World;

import Tools.Pair;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Vector;

public class SaveState {
    private int playerMapId;
    private Point2D.Double playerPosition;
    private Direction playerDirection;
    private HashMap<Integer, Point> isShowedMap;
    private HashMap<Integer, Point> isRemovedMap;

    public SaveState(World world) {
        playerMapId = world.getPlayer().getMapId();
        playerPosition = world.getPlayer().getPosition();
        playerDirection = world.getPlayer().getDirection();
        for (Map map: world.getMaps()) {
            for (var entry: map.getForegroundSet().entrySet()) {
                if (entry.getValue().isRemoved)
                    isRemovedMap.put(map.id, entry.getKey());
                if (entry.getValue().isShowed)
                    isShowedMap.put(map.id, entry.getKey());
            }
        }
    }

    public void updateWorld(World world) {
        // Load Save...
    }
}
