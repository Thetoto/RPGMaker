package Editor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TilesSelectPanel extends JPanel {
    ArrayList<JButton> buttons = new ArrayList<>();

    public TilesSelectPanel() {
    }

    @Override
    public Component add(Component component) {
        if (component instanceof JButton) {
            buttons.add((JButton)component);
        }
        return super.add(component);
    }
}
