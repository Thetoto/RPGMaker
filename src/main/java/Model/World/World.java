package Model.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class World extends Observable {
    List<Map> maps;
    Player player;

    public World() {
        maps = new ArrayList<Map>();
        player = new Player();
    }
}
