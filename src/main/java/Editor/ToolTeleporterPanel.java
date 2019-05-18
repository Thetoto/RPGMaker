package Editor;

import Model.World.Map;
import Model.World.Player;
import Model.World.Teleporter;

import javax.swing.*;
import java.awt.*;

public class ToolTeleporterPanel extends JPanel {
    public JLabel mapDest;
    public JLabel pointDestX;
    public JLabel pointDestY;
    public JButton setDestButton;

    public ToolTeleporterPanel() {
        this.setLayout(new GridLayout(4,1));

        this.mapDest = new JLabel("Destination map: null");
        this.pointDestX = new JLabel("X: null");
        this.pointDestY = new JLabel("Y: null");
        this.setDestButton = new JButton("Set destination");

        this.add(mapDest);
        this.add(pointDestX);
        this.add(pointDestY);
        this.add(setDestButton);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        this.setDestButton.setVisible(aFlag);
        this.mapDest.setVisible(aFlag);
        this.pointDestX.setVisible(aFlag);
        this.pointDestY.setVisible(aFlag);
    }

    public void update(Teleporter t) {
        String map = t.getMapDestName();
        if (map != null)
            mapDest.setText("Destination map: " + map);
        else
            mapDest.setText("Destination map: null");

        Point point = t.getPointDest();
        if (point != null) {
            pointDestX.setText("X: " + point.x);
            pointDestY.setText("Y: " + point.y);
        } else {
            pointDestX.setText("X: null");
            pointDestY.setText("Y: null");
        }
    }
}
