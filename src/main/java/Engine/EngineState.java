package Engine;

import Model.World.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.Observable;
import java.util.Optional;
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
        this.player = new Player(world.getPlayer());
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

    public boolean talk() {
        boolean does_action = false;
        Optional<java.util.Map.Entry<Point, NPC>> npc = currentMap.getNpcSet().entrySet().stream().min((o1, o2) -> {
            double dist1 = o1.getKey().distance(player.getPosition());
            double dist2 = o2.getKey().distance(player.getPosition());
            return (int) (dist1 - dist2);
        });
        if (npc.isPresent() && npc.get().getKey().distance(player.getPosition()) < 1) {
            currentMessage = npc.get().getValue().getMessage();
            setChanged();
            notifyObservers("Update Message");
            does_action = true;
        }
        return does_action;
    }

    public void shutUp() {
        setChanged();
        notifyObservers("Remove Message");
    }

    public void checkTeleporter() {
        var realPos = new Point2D.Double(player.getPosition().getX(), player.getPosition().getY() + 1);
        for (Teleporter t : currentMap.getTeleporters()) {
            if (t.getPosition().distance(realPos) < 0.5f) {
                player.setPosition(new Point2D.Double(t.getPointDest().getX(), t.getPointDest().getY()), t.getMapDestId());
                if (currentMap.id != t.getMapDestId())
                    changeMap(world.getMapById(t.getMapDestId()));
                System.out.println("Teleport to : " + currentMap.toString());
            }
        }
    }
}
