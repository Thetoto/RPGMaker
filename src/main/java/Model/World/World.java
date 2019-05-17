package Model.World;

import Model.Editor.EditorState;

import java.awt.*;
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

    public void setUpLoad() {
        var backTiles = EditorState.getInstance().tilesState.backgroundTiles;
        var foreTiles = EditorState.getInstance().tilesState.foregroundTiles;
        for (Map map: maps) {
            for (int i = 0; i < map.background.size(); i++) {
                map.background.set(i, backTiles.get(map.background.get(i).geName()));
            }
            for (Point pt : map.foreground.keySet()) {
                map.foreground.put(pt, foreTiles.get(map.foreground.get(pt).geName()));
            }
        }

    }
}
