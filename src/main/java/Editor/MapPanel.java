package Editor;

import Model.Editor.EditorState;
import Model.Editor.ToolsEnum;
import Model.World.ImportedTile;
import Model.Editor.MapState;
import Model.World.Map;
import Model.World.Tile;

import javax.annotation.processing.SupportedSourceVersion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MapPanel extends JLayeredPane implements Observer {

    BufferedImage bi;
    BufferedImage selection_layout;

    public MapPanel() {
        bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        JLabel Mimg = new JLabel(new ImageIcon(bi));
        Mimg.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.addImpl(Mimg, JLayeredPane.TOP_ALIGNMENT, 1);

        selection_layout = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        JLabel Limg = new JLabel(new ImageIcon(selection_layout));
        Limg.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.addImpl(Limg, JLayeredPane.TOP_ALIGNMENT, 0);
    }

    public void drawMap(Map map) {
        boolean showGrid = EditorState.getInstance().showGrid;
        int multiply = showGrid ? 17 : 16;
        this.remove(1);
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

        JLabel Mimg = new JLabel(new ImageIcon(bi));

        Mimg.setBounds(0, 0, bi.getWidth(), bi.getHeight());
        this.addImpl(Mimg, JLayeredPane.TOP_ALIGNMENT, 1);

        this.setSizeMap();
        this.repaint();
    }

    public void set_image(Graphics2D g, ImportedTile img, Point top) {
        boolean showGrid = EditorState.getInstance().showGrid;
        int multiply = showGrid ? 17 : 16;
        int decalage_x = top.x;
        int decalage_y = top.y;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                g.drawImage(img.getTile(i, j).get(), (i + decalage_x) * multiply, (j + decalage_y) * multiply , null);
            }
        }
    }

    public void show_selection(MapState mapState) {
        Point in = mapState.selectionIn;
        Point out = mapState.selectionOut;
        Tile cur = null;
        if (EditorState.getInstance().toolsState.currentTools == ToolsEnum.TILES) {
            cur = EditorState.getInstance().tilesState.currentTile;
            if (in == null && mapState.mousePos != null) {
                in = mapState.mousePos;
                out = mapState.mousePos;
            }
        }

        boolean showGrid = EditorState.getInstance().showGrid;
        int multiply = showGrid ? 17 : 16;
        this.remove(0);
        selection_layout = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = selection_layout.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        if (in != null && out != null) {
            for (int x = in.x; x <= out.x; x++) {
                for (int y = in.y; y <= out.y; y++) {
                    if (cur != null) {
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
        JLabel Limg = new JLabel(new ImageIcon(selection_layout));
        Limg.setBounds(0, 0, bi.getWidth(), bi.getHeight());

        this.addImpl(Limg, JLayeredPane.TOP_ALIGNMENT, 0);
        this.repaint();
    }

    public void addMouseCompleteListener(MouseAdapter listener) {
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof String && observable instanceof MapState){
            MapState mapState = (MapState) observable;
            String arg = (String) o;
            if (arg.equals("mouseOver")) {
                this.show_selection(mapState);
            }
            if (arg.equals("mousePreview")) {
                this.show_selection(mapState);
            }
            if (arg.equals("Load Me")) {
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
