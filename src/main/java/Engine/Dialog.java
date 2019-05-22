package Engine;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class Dialog extends JPanel {
    JLabel label;

    public Dialog() {
        label = new JLabel();
        this.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new CompoundBorder(BorderFactory.createEtchedBorder(), new EmptyBorder(6, 6, 6, 6))));
        this.add(label);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        label.setVisible(aFlag);
    }

    public void setText(String s) {
        label.setText(s);
    }
}
