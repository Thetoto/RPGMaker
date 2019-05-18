package Editor;

import javax.swing.*;
import java.awt.*;

public class ToolBoxPanel extends JPanel {
    public JButton setSpawn;

    public ToolBoxPanel() {
        this.setBackground(Color.BLACK);
        setSpawn = new JButton("Set Player Spawn");
        this.add(setSpawn);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        setSpawn.setVisible(aFlag);
    }
}
