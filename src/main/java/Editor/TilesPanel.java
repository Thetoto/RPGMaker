package Editor;

import javax.swing.*;
import java.awt.*;

public class TilesPanel extends JPanel {
    public JTabbedPane tilesTypePanel;
    public JPanel forgroundTab;
    public JPanel backgroundTab;

    public TreePanel treePanel;

    public TilesPanel() {
        this.setBackground(Color.RED);
        this.setLayout(new GridLayout(2, 1));

        forgroundTab = new JPanel();
        forgroundTab.setBackground(Color.CYAN);
        backgroundTab = new JPanel();
        backgroundTab.setBackground(Color.MAGENTA);

        tilesTypePanel = new JTabbedPane();
        tilesTypePanel.add("Background", backgroundTab);
        tilesTypePanel.add("Forground", forgroundTab);

        treePanel = new TreePanel();

        this.add(tilesTypePanel);
        this.add(treePanel);
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
