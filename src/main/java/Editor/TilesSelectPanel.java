package Editor;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TilesSelectPanel extends JPanel {
    public Map<String, JButton> buttons = new HashMap<>();

    public TilesSelectPanel() {
        this.setPreferredSize(new Dimension(200,1));
    }

    public void addTile(String name, JButton button) {
        buttons.put(name, button);
        this.add(button);
    }

    public void clean() {
        for (JButton b : buttons.values()) {
            this.remove(b);
        }
        buttons.clear();
    }
}
