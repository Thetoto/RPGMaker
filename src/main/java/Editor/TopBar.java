package Editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class TopBar extends JPanel {
    public JButton loadButton;
    public JButton saveButton;
    public JButton undoButton;
    public JButton redoButton;
    public JButton createButton;
    public JButton toolsButton;
    public JButton showGridButton;

    public TopBar() {
        this.setBackground(Color.LIGHT_GRAY);
        loadButton = initButton("load.png");
        saveButton = initButton("save.png");
        undoButton = initButton("undo.png");
        redoButton = initButton("redo.png");
        createButton = initButton("create.png");
        toolsButton = initButton("tools.png");
        showGridButton = initButton("grid.png");
    }

    private JButton initButton(String path) {
        JButton button =  new JButton();
        try {
            Image img = ImageIO.read(ClassLoader.getSystemClassLoader().getResource(path));

            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        this.add(button);
        button.setPreferredSize(new Dimension(32,32));
        return button;
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
    }
}
