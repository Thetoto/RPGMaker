package Editor;

import Model.Editor.TilesState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class ToolTilePanel extends JPanel implements Observer {
    public ToolTilePanel() {
        this.setBackground(Color.MAGENTA);
        this.add(new JButton("Test"));
    }

    public void setUp(BufferedImage tile) {
        removeAll();
        add(new JLabel("Selected tile :"));
        var label = new JLabel();
        label.setIcon(new ImageIcon(tile));
        add(label);
        label.setVisible(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof TilesState && o instanceof String) {
            String str = (String)o;
            TilesState obj = (TilesState)observable;
            if (str.equals("Change current tile")) {
                setUp(obj.currentTile.get());
            }
            this.validate();
            this.getParent().validate();
            this.getParent().getParent().validate();
        }
    }
}
