package Engine;

import Model.World.*;
import Tools.Tools;

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
    public NPC currentMessage;

    static EngineState engineState;

    public EngineState(World world) {
        engineState = this;
        this.world = world;
        this.player = new Player(world.getPlayer());
        this.currentMessage = null;
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
        Optional<NPC> npc = currentMap.getNpcs().stream().min((o1, o2) -> {
            double dist1 = o1.getPoint().distance(player.getPosition());
            double dist2 = o2.getPoint().distance(player.getPosition());
            return (int) (dist1 - dist2);
        });
        if (npc.isPresent() && npc.get().getPoint().distance(player.getPosition()) < 1) {
            currentMessage = npc.get();
            setChanged();
            notifyObservers("Update Message");
            does_action = true;
        }
        return does_action;
    }

    public boolean pickObject() {
        Optional<Point> pt = currentMap.getForegroundSet().keySet().stream().min((o1, o2) -> {
            double dist1 = o1.distance(player.getPosition());
            double dist2 = o2.distance(player.getPosition());
            return (int) (dist1 - dist2);
        });

        if (pt.isPresent()) {
            Point2D.Double newPt = new Point2D.Double(pt.get().x, pt.get().y);
            if (newPt.distance(player.getPosition()) < 1) {
                Foreground f = currentMap.getForegroundSet().get(pt.get());
                if (f.isPickable) {
                    System.out.println("Pick object " + currentMap.getForegroundSet().get(pt.get()).getName());
                    player.getItems().add(f);
                    f.isRemoved = true;
                    setChanged();
                    notifyObservers("Update foreground");
                    return true;
                }
            }
        }
        return false;
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

    public void setPause(boolean b) {
        setChanged();
        if (b)
            notifyObservers("Pause");
        else
            notifyObservers("Resume");
    }

    public int switchTime() {
        world.timeCycle.switchTime();
        setChanged();
        notifyObservers("Switch Time");
        return  world.timeCycle.getNextDelay();
    }

    public void redrawNPC() {
        setChanged();
        notifyObservers("Update NPC");
    }

    public boolean showInv() {
        setChanged();
        notifyObservers("Show Inventory");
        return true;
    }

    public boolean hideInv() {
        setChanged();
        notifyObservers("Hide Inventory");
        return true;
    }
}
