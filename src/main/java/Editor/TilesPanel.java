package Editor;

import javax.swing.*;
import java.awt.*;

public class TilesPanel extends JPanel {
    public JTabbedPane tilesTypePanel;
    public JPanel forgroundTab;
    public JPanel backgroundTab;

    public JTree treePanel;

    public TilesPanel() {
        this.setBackground(Color.RED);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        forgroundTab = new JPanel();
        forgroundTab.setBackground(Color.CYAN);
        backgroundTab = new JPanel();
        backgroundTab.setBackground(Color.MAGENTA);

        tilesTypePanel = new JTabbedPane();
        tilesTypePanel.add("Background", backgroundTab);
        tilesTypePanel.add("Forground", forgroundTab);

        treePanel = new JTree();

        this.add("Tiles Tab", tilesTypePanel);
        this.add("Tree", treePanel);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);

        tilesTypePanel.setVisible(aFlag);

        forgroundTab.setVisible(aFlag);
        backgroundTab.setVisible(aFlag);

        treePanel.setVisible(aFlag);
    }
}
