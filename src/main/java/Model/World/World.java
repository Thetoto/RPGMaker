package Model.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class World extends Observable {
    String name;
    List<Map> maps;
    Player player;

    public World(String name) {
        this.name = name;
        maps = new ArrayList<Map>();
        player = new Player();
    }

    public List<Map> getMaps() {
        return maps;
    }

    public void addMap(Map maps) {
        this.maps.add(maps);
    }

    @Override
    public String toString() {
        return name;
    }
}
