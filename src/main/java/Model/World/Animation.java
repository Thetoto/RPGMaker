package Model.World;

import Tools.FileManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Animation extends Tile {
    transient public Vector<Tile> tiles;
    public Direction direction;

    public Animation(String name, BufferedImage img) {
        super(name, img);
        segmentation(img);
        if (tiles == null)
            System.err.println("Invalid Animation File Dimension");
        setDirection(Direction.UP);
    }

    @Override
    public BufferedImage get() {
        // TODO : Get by direction
        if (tiles.size() >= 2)
            return tiles.get(1).get();
        else
            return tiles.get(0).get();
    }

    private void segmentation(BufferedImage bi) {
        tiles = new Vector<>();
        int biW = bi.getWidth();
        int biH = bi.getHeight();
        if (biW % biH != 0 && biW % 4 != 0) {
            tiles = null;
            return;
        }
        for (int i = 0; i < biW; i += biH) {
            tiles.add(new Tile("animation", bi.getSubimage(i, 0, biH, biH)));
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public ImportedTile toImportedTile() {
        return new ImportedTile("tmp", get());
    }
}
