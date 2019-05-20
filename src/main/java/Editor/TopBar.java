package Editor;

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
    public JButton addNewTiles;
    public JButton zoomMinus;
    public JButton zoomPlus;

    public TopBar() {
        this.setBackground(Color.LIGHT_GRAY);
        loadButton = Editor.initIconButton("load.png", this);
        saveButton = Editor.initIconButton("save.png", this);
        undoButton = Editor.initIconButton("undo.png", this);
        redoButton = Editor.initIconButton("redo.png", this);
        createButton = Editor.initIconButton("create.png", this);
        toolsButton = Editor.initIconButton("tools.png", this);
        showGridButton = Editor.initIconButton("grid.png", this);
        zoomMinus = Editor.initIconButton("zoom_minus.png", this);
        zoomPlus = Editor.initIconButton("zoom_plus.png", this);
        addNewTiles = Editor.initIconButton("newtile.png", this);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
    }
}
