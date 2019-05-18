package Editor;

import Model.Editor.EditorState;
import Model.Editor.ToolsEnum;
import Model.World.ImportedTile;
import Model.Editor.MapState;
import Model.World.Map;
import Model.World.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MapPanel extends JLayeredPane implements Observer {
    static Integer MAP_LAYER = 1;
    static Integer MID_LAYER = 2;
    static Integer SELECT_LAYER = 3;

    JLabel mapLayer;
    JLabel midLayer;
    JLabel selectLayer;

    static int multiply;

    BufferedImage bi = Tile.getPlaceholder().get();
    BufferedImage selection_layout = Tile.getPlaceholder().get();

    public MapPanel() {
        mapLayer = new JLabel();
        this.add(mapLayer, MAP_LAYER);

        midLayer = new JLabel();
        this.add(midLayer, MID_LAYER);

        selectLayer = new JLabel();
        this.add(selectLayer, SELECT_LAYER);
    }

    public synchronized void drawMap(Map map) {
        this.remove(mapLayer);
        bi = new BufferedImage(map.getDim().width * multiply, map.getDim().height * multiply, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bi.createGraphics();
        for (int x = 0; x < map.getDim().width; x++) {
            for (int y = 0; y < map.getDim().height; y++) {
                BufferedImage tile = map.getTile(x, y).get();
                g.drawImage(tile, x * multiply, y * multiply, null);
            }
        }
        HashMap<Point, Tile> foreSet = map.getForegroundSet();
        for (Point pt : foreSet.keySet()) {
            ImportedTile tile = (ImportedTile)foreSet.get(pt);
            set_image(g, tile, pt);
        }
        g.dispose();

        mapLayer = new JLabel(new ImageIcon(bi));

        mapLayer.setBounds(0, 0, bi.getWidth(), bi.getHeight());
        this.add(mapLayer, MAP_LAYER);

        this.setSizeMap();
        this.repaint();
    }

    public synchronized void set_image(Graphics2D g, ImportedTile img, Point top) {
        int decalage_x = top.x;
        int decalage_y = top.y;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                g.drawImage(img.getTile(i, j).get(), (i + decalage_x) * multiply, (j + decalage_y) * multiply , null);
            }
        }
    }

    public synchronized void show_walkable(MapState mapState) {
        this.remove(midLayer);
        if (mapState == null) {
            midLayer = new JLabel();
            this.add(midLayer, MID_LAYER);
            this.repaint();
            return;
        }
        BufferedImage tmp = new BufferedImage(mapState.currentMap.getDim().width * multiply, mapState.currentMap.getDim().height * multiply, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = tmp.createGraphics();
        for (int x = 0; x < mapState.currentMap.getDim().width; x++) {
            for (int y = 0; y < mapState.currentMap.getDim().height; y++) {
                if (mapState.currentMap.getWalkable(x, y))
                    g.setColor(new Color(0, 200, 0, 100));
                else
                    g.setColor(new Color(200, 0, 0, 100));

                g.drawRect(x * multiply, y * multiply, 16, 16);
            }
        }

        g.dispose();

        midLayer = new JLabel(new ImageIcon(tmp));

        midLayer.setBounds(0, 0, tmp.getWidth(), tmp.getHeight());
        this.add(midLayer, MID_LAYER);

        this.repaint();
    }

    public synchronized void show_selection(MapState mapState) {
        Point in = mapState.selectionIn;
        Point out = mapState.selectionOut;
        BufferedImage cur = null;
        if (EditorState.getInstance().toolsState.currentTools == ToolsEnum.TILES) {
            cur = EditorState.getInstance().tilesState.currentTile.get();
            if (in == null && mapState.mousePos != null) {
                in = mapState.mousePos;
                out = mapState.mousePos;
            }
        }

        this.remove(selectLayer);
        selection_layout = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = selection_layout.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        if (in != null && out != null) {
            for (int x = in.x; x <= out.x; x++) {
                for (int y = in.y; y <= out.y; y++) {
                    if (cur != null && ((cur.getWidth() == 16 && cur.getHeight() == 16) || in.equals(out))) {
                        g.drawImage(cur, x * multiply, y * multiply, null);
                    } else {
                        g.setColor(new Color(200, 0, 0, 100));
                        g.drawRect(x * multiply, y * multiply, 16, 16);
                        g.fillRect(x * multiply, y * multiply, 16, 16);
                    }
                }
            }
        }
        g.dispose();
        selectLayer = new JLabel(new ImageIcon(selection_layout));
        selectLayer.setBounds(0, 0, bi.getWidth(), bi.getHeight());

        this.add(selectLayer, SELECT_LAYER);
        this.repaint();
    }

    public void addMouseCompleteListener(MouseAdapter listener) {
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }

    @Override
    public synchronized void update(Observable observable, Object o) {
        boolean showGrid = EditorState.getInstance().showGrid;
        boolean showWalk = EditorState.getInstance().showWalk;
        multiply = showGrid ? 17 : 16;

        if (o instanceof String && observable instanceof MapState){
            MapState mapState = (MapState) observable;
            String arg = (String) o;
            if (arg.equals("mouseOver")) {
                this.show_selection(mapState);
            }
            if (arg.equals("mousePreview")) {
                this.show_selection(mapState);
            }
            if (arg.equals("Show Walk")) {
                this.midLayer.setOpaque(true);
                this.show_walkable(mapState);
            }
            if (arg.equals("Hide Walk")) {
                this.show_walkable(null);
            }
            if (arg.equals("Load Me")) {
                if (showWalk)
                    show_walkable(mapState);
                System.out.println("Try to display");
                drawMap(mapState.currentMap);
            }
        }
    }

    private void setSizeMap() {
        this.setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
        this.setSize(new Dimension(bi.getWidth(), bi.getHeight()));

        Editor.validateAll(this);
    }
}
