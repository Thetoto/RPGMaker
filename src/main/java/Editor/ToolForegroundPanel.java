package Editor;

import Model.Editor.EditorState;
import Model.World.Foreground;
import Model.World.ImportedTile;
import Model.World.Tile;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;
import java.util.function.Function;

public class ToolForegroundPanel extends JPanel {

    public JLabel image;
    public JLabel name;

    public JCheckBox setBreakable;
    public JCheckBox setAsHide;
    public JCheckBox setPickable;
    public JComboBox<Foreground> breaker;

    public ToolForegroundPanel() {
        this.setLayout(new GridBagLayout());
        image = new JLabel();
        name = new JLabel();
        setBreakable = new JCheckBox("Set breakable");
        setAsHide = new JCheckBox("Is Hide");
        setPickable = new JCheckBox("Set Pickable");
        breaker = new JComboBox();

        image.setVisible(true);
        name.setVisible(true);
        setBreakable.setVisible(true);
        setAsHide.setVisible(true);
        setPickable.setVisible(true);
        breaker.setVisible(true);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        this.add(image, c);
        c.gridy += 1;
        this.add(name, c);
        c.gridy += 1;
        this.add(setBreakable, c);
        c.gridy += 1;
        this.add(setAsHide, c);
        c.gridy += 1;
        this.add(setPickable, c);
        c.gridy += 1;
        this.add(breaker, c);
    }

    public void updateInfo(Foreground fore) {
        image.setIcon(new ImageIcon(fore.get()));
        name.setText(fore.getName());
        setBreakable.setSelected(fore.isBreakable);
        setAsHide.setSelected(fore.isHided);
        setPickable.setSelected(fore.isPickable);
        UpdateBreaker(fore);
    }

    public void UpdateBreaker(Foreground f) {
        if (f.isBreakable) {
            Vector<Object> tiles = new Vector(Arrays.asList(EditorState.getInstance().tilesState.foregroundTiles.values().stream()
                        .map((Function<ImportedTile, Object>) importedTile -> importedTile.toString()).toArray()));
            tiles.add(0, "------");
            DefaultComboBoxModel model = new DefaultComboBoxModel(tiles);
            breaker.setModel(model);
            breaker.setSelectedItem(f.breaker == "" ? "------" : f.breaker);
        }
        breaker.setVisible(f.isBreakable);
    }
}
