package Model.World;

import Tools.FileManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class Animation {
    public Vector<Tile> tiles;
    public Direction direction;

    public Animation() {
        try {
            File f = FileManager.getFile();
            if (f == null)
                return;
            BufferedImage tmp = ImageIO.read(f);
            if (tmp == null)
                return;
            segmentation(tmp);
        } catch (IOException e) {
            e.printStackTrace();
            tiles = null;
            return;
        }
        if (tiles == null)
            System.err.println("Invalid Animation File Dimension");
        setDirection(Direction.UP);
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
}
