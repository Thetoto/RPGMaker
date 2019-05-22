package Engine;

import Model.World.*;

import java.awt.*;
import java.util.Observable;
import java.util.function.BiConsumer;

public class EngineState extends Observable {
    public World world;
    public Map currentMap;
    public Player player;
    public String currentMessage;

    static EngineState engineState;

    public EngineState(World world) {
        engineState = this;
        this.world = world;
        this.player = world.getPlayer();
        this.currentMessage = "";
    }

    public static EngineState getInstance() {
        return engineState;
    }

    public void init() {
        Map newMap = world.getMapById(world.getPlayer().getMapId());
        changeMap(newMap);
    }

    public void changeMap(Map map) {
        currentMap = map;
        setChanged();
        notifyObservers("Change Map");
    }

    public void redrawPerso() {
        setChanged();
        notifyObservers("Update Perso");
    }

    public boolean movePerso(Direction dir, int delta_time) {
        if (!currentMap.checkBoundsPerso(player, dir, player.getPosition(), delta_time)) {
            player.move(dir, delta_time);
            return true;
        }
        return false;
    }

    public void talk() {
        currentMap.getNpcSet().forEach((point, npc) -> {
            if (point.distance(player.getPosition()) < 1) {
                currentMessage = npc.getMessage();
                setChanged();
                notifyObservers("Update Message");
            }
        });
    }
}
