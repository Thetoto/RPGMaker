package Editor;

import javax.swing.*;
import java.awt.*;

public class ToolBoxPanel extends JPanel {
    public JButton setSpawnButton;
    public JButton addTeleporterButton;
    public JCheckBox showWalkable;

    public ToolBoxPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.YELLOW);
        setSpawnButton = new JButton("Set Player Spawn");
        addTeleporterButton = new JButton("Add Teleporter");
        showWalkable = new JCheckBox("Show walkable on map");

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        this.add(setSpawnButton, c);
        c.gridy += 1;
        this.add(addTeleporterButton, c);
        c.gridy += 1;
        this.add(showWalkable, c);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        setSpawnButton.setVisible(aFlag);
        addTeleporterButton.setVisible(aFlag);
        showWalkable.setVisible(aFlag);
    }
}
