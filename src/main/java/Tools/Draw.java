package Tools;

import Model.World.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Draw {

    public static synchronized void drawBackTiles(Graphics2D g, Map map, int multiply) {
        for (int x = 0; x < map.getDim().width; x++) {
            for (int y = 0; y < map.getDim().height; y++) {
                BufferedImage tile = map.getTile(x, y).get();
                g.drawImage(tile, x * multiply, y * multiply, null);
            }
        }
    }

    public static synchronized void drawForeTiles(Graphics2D g, Map map, int multiply) {
        HashMap<Point, Tile> foreSet = map.getForegroundSet();
        for (Point pt : foreSet.keySet()) {
            ImportedTile tile = (ImportedTile)foreSet.get(pt);
            drawImported(g, tile, pt, multiply);
        }
    }

    public static synchronized void drawNPC(Graphics2D g, Map map, int multiply) {
        HashMap<Point, NPC> npcSet = map.getNpcSet();
        for (Point pt : npcSet.keySet()) {
            Animation tile = npcSet.get(pt).getAnimation();
            drawImported(g, tile.toImportedTile(), pt, multiply);
        }
    }

    public static synchronized void drawImported(Graphics2D g, ImportedTile img, Point2D top, int multiply) {
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                g.drawImage(img.getTile(i, j).get(), (int)((i + top.getX()) * multiply), (int)((j + top.getY()) * multiply), null);
            }
        }
    }

    public synchronized static void drawBackground(Graphics2D g, Tile tile, Dimension dimension, int multiply) {
        Dimension d = dimension;
        Point init = new Point(0, 0);
        
        BigTile bt = null;
        if (tile instanceof BigTile) {
            bt = (BigTile)tile;
            if (bt.getHeight() == 3 && bt.getWidth() == 3) {
                tile = bt.getTile(1, 1);
                d = new Dimension(d.width - 1, d.height - 1);
                init = new Point(1, 1);
            }
        }
        
        for (int x = init.x; x < d.width; x++) {
            for (int y = init.y; y < d.height; y++) {
                if (tile instanceof BigTile && (bt.getWidth() != 3 || bt.getHeight() != 3)) {
                    Tile t = bt.getTile(x % bt.getWidth(), y % bt.getHeight());
                    g.drawImage(t.get(), x * multiply, y * multiply, null);
                } else {
                    BufferedImage b = tile.get();
                    g.drawImage(b, x * multiply, y * multiply, null);
                }
            }
        }
        
        if (bt != null && bt.getWidth() == 3 && bt.getHeight() == 3) {
            d = dimension;
            for (int x = 1; x < d.width - 1; x++) {
                g.drawImage(bt.getTile(1,0).get(), x * multiply, 0, null);
                g.drawImage(bt.getTile(1,2).get(), x * multiply, (d.height - 1)* multiply, null);
            }
            for (int y = 1; y < d.height - 1; y++) {
                g.drawImage(bt.getTile(0,1).get(), 0, y * multiply, null);
                g.drawImage(bt.getTile(2,1).get(), (d.width - 1) * multiply, y * multiply, null);
            }
            g.drawImage(bt.getTile(0, 0).get(), 0, 0, null);
            g.drawImage(bt.getTile(0, 2).get(), 0, (d.height - 1) * multiply, null);
            g.drawImage(bt.getTile(2, 0).get(), (d.width - 1) * multiply, 0, null);
            g.drawImage(bt.getTile(2, 2).get(), (d.width - 1) * multiply, (d.height - 1) * multiply, null);
        }
    }
}
