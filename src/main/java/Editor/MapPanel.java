package Editor;

import Model.Editor.ImportedTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MapPanel extends JPanel {

    BufferedImage bi;

    public MapPanel() {
        bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        this.setBackground(Color.BLUE);
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
        this.add(Limg);
        this.repaint();
    }
}
