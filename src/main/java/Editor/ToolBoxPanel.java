package Editor;

import Model.World.TimeCycle;

import javax.swing.*;
import java.awt.*;

public class ToolBoxPanel extends JPanel {
    public JButton setSpawnButton;
    public JButton addTeleporterButton;

    public JCheckBox showWalkable;
    public JButton forceWalkable;
    public JButton forceUnwalkable;

    public JButton activeTimeCycle;
    public JSpinner dayTime;
    public JSpinner nightTime;

    public ToolBoxPanel() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.YELLOW);
        setSpawnButton = new JButton("Set Player Spawn");
        addTeleporterButton = new JButton("Add Teleporter");
        showWalkable = new JCheckBox("Show walkable on map");
        forceWalkable = new JButton("Set selection walkable");
        forceUnwalkable = new JButton("Set selection not walkable");
        activeTimeCycle = new JButton("Set time cycle");
        dayTime = new JSpinner();
        nightTime = new JSpinner();

        dayTime.setModel(new SpinnerNumberModel(10, 10, 800, 1));
        nightTime.setModel(new SpinnerNumberModel(10, 10, 800, 1));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        this.add(setSpawnButton, c);
        c.gridy += 1;
        this.add(addTeleporterButton, c);
        c.gridy += 1;
        this.add(showWalkable, c);
        c.gridy += 1;
        this.add(forceWalkable, c);
        c.gridy += 1;
        this.add(forceUnwalkable, c);
        c.gridy += 1;
        this.add(activeTimeCycle, c);
        c.gridy += 1;
        this.add(dayTime, c);
        c.gridy += 1;
        this.add(nightTime, c);

        dayTime.setVisible(false);
        nightTime.setVisible(false);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        setSpawnButton.setVisible(aFlag);
        addTeleporterButton.setVisible(aFlag);
        showWalkable.setVisible(aFlag);
        activeTimeCycle.setVisible(aFlag);
    }

    public void showCycleSetting(TimeCycle timeCycle) {
        dayTime.setVisible(timeCycle.isActive());
        nightTime.setVisible(timeCycle.isActive());
        if (timeCycle.isActive()) {
            dayTime.getModel().setValue(timeCycle.getDayDuration());
            nightTime.getModel().setValue(timeCycle.getNightDuration());
        }
    }
}
