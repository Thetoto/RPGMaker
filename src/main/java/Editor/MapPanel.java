package Editor;

import Model.Editor.EditorState;
import Model.Editor.ToolsEnum;
import Model.World.*;
import Model.Editor.MapState;
import Tools.Draw;

import javax.swing.*;
import javax.swing.text.IconView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MapPanel extends JLayeredPane implements Observer {
    static Integer BACK_LAYER = 0;
    static Integer MAP_LAYER = 1;
    static Integer MID_LAYER = 2;
    static Integer SELECT_LAYER = 3;

    JLabel backLayer;
    JLabel mapLayer;
    JLabel midLayer;
    JLabel selectLayer;

    static int multiply;
    public double currentZoom = 1.;

    BufferedImage bi = Tile.getPlaceholder().get();
    BufferedImage selection_layout = Tile.getPlaceholder().get();

    public MapPanel() {
        backLayer = new JLabel();
        this.add(backLayer, BACK_LAYER);

        mapLayer = new JLabel();
        this.add(mapLayer, MAP_LAYER);

        midLayer = new JLabel();
        this.add(midLayer, MID_LAYER);

        selectLayer = new JLabel();
        this.add(selectLayer, SELECT_LAYER);
    }

    public synchronized void drawMap(Map map) {
        bi = new BufferedImage(map.getDim().width * multiply, map.getDim().height * multiply, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bi.createGraphics();
        Draw.drawBackTiles(g, map, multiply);
        Draw.drawForeTiles(g, map, multiply);
        Draw.drawNPC(g, map, multiply);
        g.dispose();

        mapLayer.setIcon(new ImageIcon(bi));
        mapLayer.setBounds(0, 0, bi.getWidth(), bi.getHeight());

        this.setSizeMap();
        this.repaint();
    }



    public synchronized void show_walkable(MapState mapState) {
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

        midLayer.setVisible(true);
        midLayer.setIcon(new ImageIcon(tmp));
        midLayer.setBounds(0, 0, tmp.getWidth(), tmp.getHeight());

        this.repaint();
    }

    private void drawBack(MapState mapState) {
        BufferedImage tmp = new BufferedImage(mapState.currentMap.getDim().width * multiply, mapState.currentMap.getDim().height * multiply, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = tmp.createGraphics();
        Draw.drawBackground(g, mapState.currentMap.getBackgroundTile(), mapState.currentMap.getDim(), multiply);
        g.dispose();

        backLayer.setIcon(new ImageIcon(tmp));
        backLayer.setBounds(0, 0, tmp.getWidth(), tmp.getHeight());
        this.repaint();
    }

    public synchronized void show_selection(MapState mapState) {
        Point in = mapState.selectionIn;
        Point out = mapState.selectionOut;
        Tile cur = null;

        if (EditorState.getInstance().toolsState.currentTools == ToolsEnum.TILES) {
            cur = EditorState.getInstance().tilesState.currentTile;
            if (cur instanceof BigTile) {
                BigTile bt = (BigTile)cur;
                if (bt.cur != -1)
                    cur = bt.getTile(bt.cur);
            }
            if (in == null && mapState.mousePos != null) {
                in = mapState.mousePos;
                out = mapState.mousePos;
            }
        }

        selection_layout = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = selection_layout.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        if (in != null && out != null) {
            for (int x = in.x; x <= out.x; x++) {
                for (int y = in.y; y <= out.y; y++) {
                    if (cur != null && ((cur.get().getWidth() == 16 && cur.get().getHeight() == 16) || in.equals(out))) {
                        g.drawImage(cur.get(), x * multiply, y * multiply, null);
                    } else {
                        g.setColor(new Color(200, 0, 0, 100));
                        g.drawRect(x * multiply, y * multiply, 16, 16);
                        g.fillRect(x * multiply, y * multiply, 16, 16);
                    }
                }
            }
        }
        g.dispose();
        selectLayer.setIcon(new ImageIcon(selection_layout));
        selectLayer.setBounds(0, 0, bi.getWidth(), bi.getHeight());

        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        at.scale(currentZoom, currentZoom);
        g2.transform(at);
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
                drawBack(mapState);
                drawMap(mapState.currentMap);
            }
            if (arg.equals("Update Background")) {
                drawBack(mapState);
            }
            if (arg.equals("Zoom Update")) {
                currentZoom = mapState.zoomPercent;
                setSizeMap();
                this.repaint();
            }
        }
    }

    private void setSizeMap() {
        this.setPreferredSize(new Dimension((int)(bi.getWidth() * currentZoom), (int)(bi.getHeight() * currentZoom)));
        this.setSize(new Dimension((int)(bi.getWidth() * currentZoom), (int)(bi.getHeight() * currentZoom)));

        Editor.validateAll(this);
    }
}
