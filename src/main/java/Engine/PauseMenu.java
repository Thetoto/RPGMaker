package Engine;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PauseMenu extends JPanel {
    public JButton resume;
    public JButton exit;

    public PauseMenu() {
        resume = new JButton("Resume");
        resume.setVisible(true);
        resume.setMaximumSize(new Dimension(78, 27));

        exit = new JButton("Exit");
        exit.setVisible(true);
        exit.setMaximumSize(new Dimension(78, 27));

        JLabel label = new JLabel("Game paused");

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        resume.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new CompoundBorder(BorderFactory.createEtchedBorder(), new EmptyBorder(6, 6, 6, 6))));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(label);
        this.add(resume);
        this.add(exit);
    }
}
