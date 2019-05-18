package Editor;

import Model.Editor.TilesState;
import Model.World.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class ToolTilePanel extends JPanel implements Observer {
    public JCheckBox walkCheckbox;

    public ToolTilePanel() {
        this.setBackground(Color.MAGENTA);
        this.setLayout(new GridBagLayout());
    }

    public void setUp(Tile tile) {
        removeAll();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        add(new JLabel("Selected tile :"), c);
        var label = new JLabel();
        label.setIcon(new ImageIcon(tile.get()));
        c.gridx += 1;
        add(label, c);
        label.setVisible(true);

        c.gridx = 0;
        c.gridy += 1;
        walkCheckbox = new JCheckBox("Set walkable");
        walkCheckbox.setSelected(tile.walkable);
        add(walkCheckbox, c);
        walkCheckbox.setVisible(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof TilesState && o instanceof String) {
            String str = (String)o;
            TilesState obj = (TilesState)observable;
            if (str.equals("Change current tile")) {
                setUp(obj.currentTile);
            }
            Editor.validateAll(this);
        }
    }
}
