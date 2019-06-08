package Model.World;

import Tools.Pair;
import Tools.PopUpManager;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Vector;

public class SaveState {
    private int playerMapId;
    private Point2D.Double playerPosition;
    private Direction playerDirection;
    private HashMap<Integer, Point> isShowedMap = new HashMap<>();
    private HashMap<Integer, Point> isRemovedMap = new HashMap<>();

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
        if (world.getMapById(playerMapId) == null) {
            PopUpManager.Alert("This save is not compatible with this save state");
            return;
        }
        world.getPlayer().setPosition(playerPosition, playerMapId);
        world.getPlayer().move(playerDirection, 0);

        for (var entry : isShowedMap.entrySet()) {
            Map map = world.getMapById(entry.getKey());
            if (!testEntry(map, entry))
                return;
            map.getForegroundSet().get(entry.getValue()).isShowed = true;
        }

        for (var entry : isRemovedMap.entrySet()) {
            Map map = world.getMapById(entry.getKey());
            if (!testEntry(map, entry))
                return;
            map.getForegroundSet().get(entry.getValue()).isRemoved = true;

            world.getPlayer().getItems().add(map.getForegroundSet().get(entry.getValue()));
        }
    }

    private boolean testEntry(Map map, java.util.Map.Entry<Integer, Point> entry) {
        if (map == null || !map.getForegroundSet().containsKey(entry.getValue())) {
            PopUpManager.Alert("This save is not compatible with this save state");
            return false;
        }
        return true;
    }
}
