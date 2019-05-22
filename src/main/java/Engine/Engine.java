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
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(mapPanel);

        panel.setVisible(true);
        this.add(panel);

        this.setVisible(true);
    }

    public static void validateAll(Container j) {
        while (j != null) {
            j.validate();
            j = j.getParent();
        }
    }
}
