package Model.Editor;

import Model.World.Animation;
import Model.World.BigTile;
import Model.World.ImportedTile;
import Model.World.Tile;
import Tools.Tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class TilesState extends Observable {
    public Tile currentTile;

    public Map<String, Tile> backgroundTiles = new HashMap<>();
    public Map<String, ImportedTile> foregroundTiles = new HashMap<>();
    public Map<String, Animation> npcTile = new HashMap<>();

    public TilesState() {
    }

    public void setCurrentTile(String currentTile, TileType type) {
        if (type == TileType.FOREGROUND) {
            this.currentTile = foregroundTiles.get(currentTile);
        } else if (type == TileType.NPC) {
            this.currentTile = npcTile.get(currentTile);
        } else {
            this.currentTile = backgroundTiles.get(currentTile);
        }
        if (EditorState.getInstance().mapState.selectionIn != null) {
            EditorState.getInstance().mapState.mouseClick();
        }
        setChanged();
        notifyObservers("Change current tile");
    }

    public void addDefaultTiles() {
        addTile(Paths.get(Tools.getPathFromRessources("eraser.png")), TileType.BACKGROUND);
        addTile(Paths.get(Tools.getPathFromRessources("eraser.png")), TileType.FOREGROUND);
        addTile(Paths.get(Tools.getPathFromRessources("eraser.png")), TileType.NPC);
        addDirectoryTiles(Tools.getPathFromRessources("background"), TileType.BACKGROUND);
        addDirectoryTiles(Tools.getPathFromRessources("foreground"), TileType.FOREGROUND);
    }

    public void addTile(Path file, TileType type) {
        addTile(file, type, true);
    }

    public void addTile(Path file, TileType type, boolean askUpdate) {
        try {
            BufferedImage img = ImageIO.read(file.toFile());
            if (img == null)
                return;
            if (type == TileType.FOREGROUND) {
                ImportedTile fore = new ImportedTile(file.getFileName().toString(), img);
                foregroundTiles.put(file.getFileName().toString(), fore);
            } else if (type == TileType.NPC) {
                Animation npc = new Animation(file.getFileName().toString(), img);
                npcTile.put(file.getFileName().toString(), npc);
            } else {
                cutAndAddBackTile(img, file.getFileName().toString());
            }
        } catch (IOException e) {
            System.err.println("Can't load this file : " + file.getFileName());
        }
        if (askUpdate)
            askUpdate();
    }

    public void cutAndAddBackTile(BufferedImage back, String name) {
        int height = back.getHeight();
        int width = back.getWidth();
        if (name.equals("eraser.png") || (height == 16 && width == 16)) {
            Tile tile = new Tile(name, back);
            backgroundTiles.put(name, tile);
            return;
        }
        Tile tile = new BigTile(name, back);
        backgroundTiles.put(name, tile);
    }

    public void addDirectoryTiles(String path, TileType type) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(Files::isRegularFile)
                    .forEach((file) -> addTile(file, type, false));
        } catch (IOException e) {
            e.printStackTrace();
        }
        askUpdate();
    }

    private void askUpdate() {
        setChanged();
        notifyObservers("Tiles Update");
    }

    public void autoAddTiles(File fileOrDir) {
        // TODO : Better algo
        if (fileOrDir.isFile())
            addTile(fileOrDir.toPath(), TileType.BACKGROUND);
        else if (fileOrDir.getName().contains("fore"))
            addDirectoryTiles(fileOrDir.toPath().toString(), TileType.FOREGROUND);
        else if (fileOrDir.getName().contains("back"))
            addDirectoryTiles(fileOrDir.toPath().toString(), TileType.BACKGROUND);
        else if (fileOrDir.getName().contains("npc"))
            addDirectoryTiles(fileOrDir.toPath().toString(), TileType.NPC);
        else {
            try (Stream<Path> paths = Files.walk(fileOrDir.toPath())) {
                paths.filter(Files::isDirectory)
                        .forEach((file) -> {
                            if (!file.toFile().getName().equals(fileOrDir.getName()))
                                autoAddTiles(file.toFile());
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
