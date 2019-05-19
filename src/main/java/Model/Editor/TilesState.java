package Model.Editor;

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

    public TilesState() {
    }

    public void setCurrentTile(String currentTile, boolean isForeground) {
        if (isForeground) {
            this.currentTile = foregroundTiles.get(currentTile);
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
        addTile(Paths.get(Tools.getPathFromRessources("eraser.png")), false);
        addTile(Paths.get(Tools.getPathFromRessources("eraser.png")), true);
        addDirectoryTiles(Tools.getPathFromRessources("background"), false);
        addDirectoryTiles(Tools.getPathFromRessources("foreground"), true);
    }

    public void addTile(Path file, boolean isForeground) {
        addTile(file, isForeground, true);
    }

    public void addTile(Path file, boolean isForeground, boolean askUpdate) {
        try {
            BufferedImage img = ImageIO.read(file.toFile());
            if (img == null)
                return;
            if (isForeground) {
                ImportedTile fore = new ImportedTile(file.getFileName().toString(), img);
                foregroundTiles.put(file.getFileName().toString(), fore);
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

    public void addDirectoryTiles(String path, boolean isForeground) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(Files::isRegularFile)
                    .forEach((file) -> addTile(file, isForeground, false));
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
            addTile(fileOrDir.toPath(), false);
        else if (fileOrDir.getName().contains("fore"))
            addDirectoryTiles(fileOrDir.toPath().toString(), true);
        else if (fileOrDir.getName().contains("back"))
            addDirectoryTiles(fileOrDir.toPath().toString(), false);
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
