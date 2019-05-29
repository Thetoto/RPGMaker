package Model.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.BatchUpdateException;
import java.util.Vector;

public class Animation extends Tile {
    transient public Vector<Tile> tiles;
    transient Direction direction;
    transient int state;
    transient int dir_size;

    public Animation(String name, BufferedImage img) {
        super(name, img);
        segmentation(img);
        if (tiles == null)
            System.err.println("Invalid Animation File Dimension");
        direction = Direction.DOWN;
        state = 0;
        dir_size = tiles.size() / 4;
    }

    @Override
    public BufferedImage get() {
        BufferedImage res = tiles.get(0).get();
        if (tiles.size() >= 2) {
            res = tiles.get(1).get();
        }
        return res;
    }

    public BufferedImage getFull() {
        BufferedImage ref = tiles.get(0).get();
        BufferedImage res = new BufferedImage(ref.getWidth() * tiles.size(), ref.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = res.getGraphics();
        for (int i = 0; i < tiles.size(); i++) {
            g.drawImage(tiles.get(i).get(), i * ref.getWidth(), 0, null);
        }
        g.dispose();
        return res;
    }

    public BufferedImage get(Direction dir) {
        BufferedImage res = tiles.get(0).get();
        if (direction != dir) {
            state = 0;
            direction = dir;
        }
        if (tiles.size() >= 5) {
            switch (dir) {
                case DOWN:
                    res = tiles.get(state).get();
                    break;
                case RIGHT:
                    res = tiles.get(dir_size + state).get();
                    break;
                case UP:
                    res = tiles.get(dir_size * 2 + state).get();
                    break;
                case LEFT:
                    res = tiles.get(dir_size * 3 + state).get();
                    break;
            }
            state = (state + 1) % dir_size;
        }
        return res;
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

    public int getSize() {
        return tiles.get(0).get().getWidth() / 16;
    }

    public ImportedTile toImportedTile() {
        return new ImportedTile("tmp", get());
    }

    public ImportedTile toImportedTile(Direction dir) {
        return new ImportedTile("tmp", get(dir));
    }
}
