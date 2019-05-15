package Editor;

import javax.swing.*;
import java.awt.*;

public class ToolBoxPanel extends JPanel {
    JButton test;
    public ToolBoxPanel() {
        this.setBackground(Color.BLACK);
        test = new JButton("Test");
        this.add(test);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        test.setVisible(aFlag);
    }
}
