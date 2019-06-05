package Editor;

import Model.World.Foreground;

import javax.swing.*;
import java.awt.*;

public class ToolForegroundPanel extends JPanel {

    public JLabel image;
    public JLabel name;

    public JCheckBox setBreakable;
    public JCheckBox setAsHide;
    public JCheckBox setPickable;

    public ToolForegroundPanel() {
        this.setLayout(new GridBagLayout());
        image = new JLabel();
        name = new JLabel();
        setBreakable = new JCheckBox("Set breakable");
        setAsHide = new JCheckBox("Is Hide");
        setPickable = new JCheckBox("Set Pickable");

        setBreakable.setVisible(true);
        setAsHide.setVisible(true);
        setPickable.setVisible(true);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        this.add(image, c);
        c.gridy += 1;
        this.add(name, c);
        c.gridy += 5;
        this.add(setBreakable, c);
        c.gridy += 1;
        this.add(setAsHide, c);
        c.gridy += 1;
        this.add(setPickable, c);
    }

    public void updateInfo(Foreground fore) {
        image.setIcon(new ImageIcon(fore.get()));
        name.setText(fore.getName());
        setBreakable.setSelected(fore.isBreakable);
        setAsHide.setSelected(fore.isHided);
        setPickable.setSelected(fore.isPickable);
    }
}
