package Editor;

import javax.swing.*;
import java.awt.*;

public class ToolBoxPanel extends JPanel {
    public JButton setSpawnButton;
    public JButton addTeleporterButton;

    public ToolBoxPanel() {
        this.setBackground(Color.BLACK);
        setSpawnButton = new JButton("Set Player Spawn");
        addTeleporterButton = new JButton("Add Teleporter");
        this.add(setSpawnButton);
        this.add(addTeleporterButton);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        setSpawnButton.setVisible(aFlag);
        addTeleporterButton.setVisible(aFlag);
    }
}
