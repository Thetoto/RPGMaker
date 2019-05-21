package Editor;

import Model.World.Map;
import Model.World.NPC;
import Model.World.Player;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;

public class ToolNPCPanel extends JPanel {
    JLabel Name = new JLabel();
    JLabel Xcoord = new JLabel();
    JLabel Ycoord = new JLabel();

    public ToolNPCPanel() {
        this.setLayout(new GridLayout(3,1));

        this.add(Name);
        this.add(Xcoord);
        this.add(Ycoord);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        Name.setVisible(aFlag);
        Xcoord.setVisible(aFlag);
        Ycoord.setVisible(aFlag);
    }

    public void updateInfo(NPC npc) {
        Point coord = npc.getPoint();
        Name.setText("Name :" + npc.getName());
        Xcoord.setText("Coord X: " + coord.x);
        Ycoord.setText("Coord Y: " + coord.y);
    }
}
