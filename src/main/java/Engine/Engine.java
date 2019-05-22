package Engine;

import Model.World.World;

import javax.swing.*;
import java.awt.*;

public class Engine extends JFrame {
    public Display mapPanel;
    public Engine() {
        this.setSize(1080,720);
        this.setTitle("BibleEngine");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        mapPanel = new Display();
        this.setLayeredPane(mapPanel);

        this.setVisible(true);
    }
}
