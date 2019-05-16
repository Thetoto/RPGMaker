package Editor;

import Model.Editor.ImportedTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapPanel extends JLayeredPane {

    BufferedImage bi;
    BufferedImage selection_layout;

    public MapPanel() {
        bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        selection_layout = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

        JLabel Limg = new JLabel(new ImageIcon(selection_layout));
        Limg.setBounds(0, -90, this.getWidth(), this.getHeight());
        this.addImpl(Limg, JLayeredPane.TOP_ALIGNMENT,JLayeredPane.DEFAULT_LAYER);
    }

    public void set_image(ImportedTile img) {
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
        this.addImpl(Limg, JLayeredPane.TOP_ALIGNMENT, 1);
        this.repaint();
    }

    public void show_selection(Point in, Point out) {
        this.remove(JLayeredPane.DEFAULT_LAYER);
        selection_layout = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = in.x; x < out.x; x++) {
            for (int y = in.y; y < out.y; y++) {
                Graphics2D g = selection_layout.createGraphics();
                g.setColor(new Color(200, 0, 0, 50));
                g.drawRect(x * 16, y * 16, 16, 16);
                g.fillRect(x * 16, y * 16, 16, 16);
                g.dispose();
            }
        }

        JLabel Limg = new JLabel(new ImageIcon(selection_layout));
        Limg.setBounds(0, 0, this.getWidth(), this.getHeight());

        this.addImpl(Limg, JLayeredPane.TOP_ALIGNMENT,JLayeredPane.DEFAULT_LAYER);
        this.repaint();
    }
}
