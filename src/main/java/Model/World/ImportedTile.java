package Model.World;

import Model.World.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

public class ImportedTile extends Tile {
    private Dimension dimension;

    private Vector<Tile> image;
    //private boolean IsWalkable; Est-ce qu'un foreground est unwalkable par défaut ?

    public ImportedTile(String name, BufferedImage fullImage) {
        super(name, fullImage);

        int height = fullImage.getHeight();
        int width = fullImage.getWidth();
        if (height % 16 != 0 || width % 16 != 0)
            System.err.println("Invalid dimension");
        height /= 16;
        width /= 16;
        dimension = new Dimension(height, width);
        image = new Vector<Tile>();
        for (int i  = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile t = new Tile(fullImage.getSubimage(j * 16,i * 16,16, 16));
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
