package Model.Editor;

import Model.World.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ImportedTile {
    private Dimension dimension;
    private Vector<Tile> image;
    //private boolean IsWalkable; Est-ce qu'un foreground est unwalkable par défaut ?

    public ImportedTile(String img_path) throws Exception {
        BufferedImage image_tmp = null;
        try {
            image_tmp = ImageIO.read(new File(img_path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int height = image_tmp.getHeight();
        int width = image_tmp.getWidth();
        if (height % 16 != 0 || width % 16 != 0)
            throw new Exception("Invalid dimension");
        height /= 16;
        width /= 16;
        dimension = new Dimension(height, width);
        image = new Vector<Tile>();
        for (int i  = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile t = new Tile(image_tmp.getSubimage(i * 16,j * 16,16, 16));
                image.add(t);
            }
        }
    }

    public int getHeight() {
        return dimension.height;
    }

    public int getWidth() {
        return dimension.width;
    }

    public Tile getTile(int x, int y) {
        return image.get(x * dimension.width + y);
    }
}
