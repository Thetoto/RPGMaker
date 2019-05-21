package Engine;

import Model.World.World;

import javax.swing.*;

public class Engine extends JFrame {
    Display mapPanel;
    public Engine() {
        this.setSize(1080,720);
        this.setTitle("BibleEngine");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        mapPanel = new Display();
        setLayeredPane(mapPanel);
    }
}
