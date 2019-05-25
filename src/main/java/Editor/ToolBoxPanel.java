package Editor;

import Model.World.TimeCycle;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.*;

public class ToolBoxPanel extends JPanel {
    public JButton setSpawnButton;
    public JButton addTeleporterButton;

    public JCheckBox showWalkable;
    public JButton forceWalkable;
    public JButton forceUnwalkable;

    public JButton activeTimeCycle;
    public JPanel timeCyclePanel;
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

        timeCyclePanel = new JPanel();
        timeCyclePanel.setLayout(new GridLayout(2,2));
        JLabel dayTimeLabel = new JLabel("Set day time value");
        JLabel nightTimeLabel = new JLabel("Set night time value");
        dayTime = new JSpinner();
        nightTime = new JSpinner();

        timeCyclePanel.add(dayTimeLabel);
        timeCyclePanel.add(dayTime);
        timeCyclePanel.add(nightTimeLabel);
        timeCyclePanel.add(nightTime);

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
        this.add(timeCyclePanel, c);

        timeCyclePanel.setVisible(false);
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
        timeCyclePanel.setVisible(timeCycle.isActive());
        dayTime.setVisible(timeCycle.isActive());
        nightTime.setVisible(timeCycle.isActive());
        if (timeCycle.isActive()) {
            dayTime.getModel().setValue(timeCycle.getDayDuration());
            nightTime.getModel().setValue(timeCycle.getNightDuration());
        }
    }
}
