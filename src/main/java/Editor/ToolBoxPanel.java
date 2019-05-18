package Editor;

import javax.swing.*;
import java.awt.*;

public class ToolBoxPanel extends JPanel {
    public JButton setSpawnButton;
    public JButton addTeleporterButton;
    public JCheckBox showWalkable;

    public ToolBoxPanel() {
        this.setBackground(Color.BLACK);
        setSpawnButton = new JButton("Set Player Spawn");
        addTeleporterButton = new JButton("Add Teleporter");
        showWalkable = new JCheckBox("Show walkable on map");
        this.add(setSpawnButton);
        this.add(addTeleporterButton);
        this.add(showWalkable);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        setSpawnButton.setVisible(aFlag);
        addTeleporterButton.setVisible(aFlag);
        showWalkable.setVisible(aFlag);
    }
}
