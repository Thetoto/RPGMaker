package Model.Editor;

import Editor.FileManager;
import Model.World.Map;
import Model.World.World;
import com.google.gson.Gson;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.util.Observable;

public class EditorState extends Observable {
    public static EditorState instance;

    public ToolsState toolsState;
    public TilesState tilesState;
    public MapState mapState;

    public World world;

    public boolean showGrid = true;

    public EditorState() {
        instance = this;
        toolsState = new ToolsState();
        tilesState = new TilesState();
        mapState = new MapState();
    }

    public static EditorState getInstance() {
        return instance;
    }

    public void getWorld() {
        File file = FileManager.getFile();
        Gson gson = new Gson();
        System.out.println(file.toString() + " " + gson);
        try {
            world = gson.fromJson(new FileReader(file), World.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
        world.setUpLoad();
        setChanged();
        notifyObservers("New World");
    }

    public void defaultWorld() {
        world = new World("New World");
        world.addMap(new Map(new Dimension(20, 20), "Nice"));
        mapState.updateMap(world.getMaps().get(0));
        setChanged();
        notifyObservers("New World");
    }

    public void addMap(Map map) {
        world.addMap(map);
        setChanged();
        notifyObservers("New Map");
    }

    public void saveWorld() {
        FileManager.saveFile(world);
    }

    public void invertGrid() {
        showGrid = !showGrid;
        mapState.mousePreview(mapState.selectionIn, mapState.selectionOut);
        mapState.updateMap();
    }
}
