package Editor;

import Model.World.Map;
import Model.World.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Observable;

public class ToolPlayerPanel extends JPanel {
    JLabel mapName = new JLabel();
    JLabel Xcoord = new JLabel();
    JLabel Ycoord = new JLabel();
    public JButton setAnim = new JButton("Set animation");

    public ToolPlayerPanel() {
        this.setLayout(new GridLayout(4,1));

        this.add(mapName);
        this.add(Xcoord);
        this.add(Ycoord);
        this.add(setAnim);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        mapName.setVisible(aFlag);
        Xcoord.setVisible(aFlag);
        Ycoord.setVisible(aFlag);
        setAnim.setVisible(aFlag);
    }

    public void updateInfo(Player p) {
        Point coord = p.getPosition();
        Map map = p.getMap();
        if (map == null)
            mapName.setText("Map : null");
        else
            mapName.setText("Map :" + map.toString());
        Xcoord.setText("Coord X: " + coord.x);
        Ycoord.setText("Coord Y: " + coord.y);
    }
}
