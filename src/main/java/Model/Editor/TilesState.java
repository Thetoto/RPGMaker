package Model.Editor;

import Editor.TilesPanel;
import Model.World.Tile;
import Tools.Tools;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    public boolean currentIsForeground;
    public String currentTile;

    public Map<String, Tile> backgroundTiles = new HashMap<>();
    public Map<String, ImportedTile> foregroundTiles = new HashMap<>();

    public TilesState() {
    }

    public void setCurrentTile(String currentTile, boolean isForeground) {
        this.currentTile = currentTile;
        this.currentIsForeground = isForeground;
    }

    public void addDefaultTiles() {
        addDirectoryTiles(Tools.getPathFromRessources("background"), false);
        addDirectoryTiles(Tools.getPathFromRessources("foreground"), true);
    }

    public void addTile(Path file, boolean isForeground) {
        addTile(file, isForeground, true);
    }

    public void addTile(Path file, boolean isForeground, boolean askUpdate) {
        try {
            BufferedImage img = ImageIO.read(file.toFile());
            if (isForeground) {
                ImportedTile fore = new ImportedTile(img);
                foregroundTiles.put(file.getFileName().toString(), fore);
            } else {
                Tile back = new Tile(img);
                backgroundTiles.put(file.getFileName().toString(), back);
            }
        } catch (IOException e) {
            System.err.println("Can't load this file : " + file.getFileName());
        }
        if (askUpdate)
            askUpdate();
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
        notifyObservers();
    }
}
