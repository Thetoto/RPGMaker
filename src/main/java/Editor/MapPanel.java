package Editor;

import Model.Editor.EditorState;
import Model.Editor.ImportedTile;
import Model.Editor.MapState;
import Model.World.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class MapPanel extends JLayeredPane implements Observer {

    BufferedImage bi;
    BufferedImage selection_layout;

    public MapPanel() {
        bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        JLabel Mimg = new JLabel(new ImageIcon(bi));
        Mimg.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.addImpl(Mimg, JLayeredPane.TOP_ALIGNMENT, 1);

        selection_layout = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        JLabel Limg = new JLabel(new ImageIcon(selection_layout));
        Limg.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.addImpl(Limg, JLayeredPane.TOP_ALIGNMENT, 0);
    }

    public void drawMap(Map map) {
        this.remove(1);
        bi = new BufferedImage(map.getDim().width * 16, map.getDim().height * 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        for (int x = 0; x < map.getDim().width; x++) {
            for (int y = 0; y < map.getDim().height; y++) {
                BufferedImage tile = map.getTile(x , y).get();
                g.drawImage(tile, x * 16, y * 16, null);
            }
        }
        g.dispose();
        JLabel Mimg = new JLabel(new ImageIcon(bi));
        Mimg.setBounds(0, 0, this.getWidth(), this.getHeight());

        this.addImpl(Mimg, JLayeredPane.TOP_ALIGNMENT, 1);
        this.repaint();
    }

    public void set_image(ImportedTile img) {
        this.remove(1);
        this.remove(0);
        int decalage_x = 0;
        int decalage_y = 0;
        Graphics2D g = bi.createGraphics();
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                g.drawImage(img.getTile(i, j).get(), i * 16 + decalage_x, j * 16 + decalage_y, null);
                decalage_y += 1;
            }
            decalage_y = 0;
            decalage_x += 1;
        }
        g.dispose();

        JLabel Limg = new JLabel(new ImageIcon(bi));
        this.addImpl(Limg, JLayeredPane.TOP_ALIGNMENT, 0);
        this.repaint();
    }

    public void show_selection(Point in, Point out) {
        this.remove(0);
        selection_layout = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
        if (in != null && out != null) {
            for (int x = in.x; x <= out.x; x++) {
                for (int y = in.y; y <= out.y; y++) {
                    Graphics2D g = selection_layout.createGraphics();
                    g.setColor(new Color(200, 0, 0, 50));
                    g.drawRect(x * 16, y * 16, 16, 16);
                    g.fillRect(x * 16, y * 16, 16, 16);
                    g.dispose();
                }
            }
        }
        JLabel Limg = new JLabel(new ImageIcon(selection_layout));
        Limg.setBounds(0, 0, this.getWidth(), this.getHeight());

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
                EditorState coords = (EditorState) observable;
                //mapPane.show_selection(coords.selectionIn);
            }
            if (arg.equals("mousePreview")) {
                this.show_selection(mapState.selectionIn, mapState.selectionOut);
            }
            if (arg.equals("Load Me")) {
                System.out.println("Try to display");
                drawMap(mapState.currentMap);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(bi.getWidth(), bi.getHeight());
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}
