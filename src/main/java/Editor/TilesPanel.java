package Editor;

import Model.World.ImportedTile;
import Model.Editor.TilesState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class TilesPanel extends JPanel implements Observer {
    public JTabbedPane tilesTypePanel;

    public TilesSelectPanel foregroundTab;
    public TilesSelectPanel backgroundTab;

    public TreePanel treePanel;

    public TilesPanel() {
        this.setBackground(Color.RED);
        this.setLayout(new GridLayout(2, 1));

        foregroundTab = new TilesSelectPanel();
        foregroundTab.setBackground(Color.CYAN);
        backgroundTab = new TilesSelectPanel();
        backgroundTab.setBackground(Color.MAGENTA);

        tilesTypePanel = new JTabbedPane();
        tilesTypePanel.add("Foreground", foregroundTab);
        tilesTypePanel.add("Background", backgroundTab);

        treePanel = new TreePanel();

        this.add(tilesTypePanel);
        this.add(treePanel);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);

        tilesTypePanel.setVisible(aFlag);

        foregroundTab.setVisible(aFlag);
        backgroundTab.setVisible(aFlag);

        treePanel.setVisible(aFlag);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof TilesState) {
            backgroundTab.clean();
            foregroundTab.clean();
            TilesState obj = (TilesState)observable;
            for (String k : obj.backgroundTiles.keySet()) {
                BufferedImage icon = obj.backgroundTiles.get(k).get();

                JButton button = new JButton();
                button.setIcon(new ImageIcon(icon));
                button.setPreferredSize(new Dimension(icon.getWidth(), icon.getHeight()));

                backgroundTab.addTile(k, button);
            }
            for (String k : obj.foregroundTiles.keySet()) {
                ImportedTile fore = obj.foregroundTiles.get(k);
                BufferedImage icon = fore.get();

                JButton button = new JButton();
                button.setIcon(new ImageIcon(icon));
                button.setPreferredSize(new Dimension(icon.getWidth(), icon.getHeight()));

                foregroundTab.addTile(k, button);
            }
            tilesTypePanel.setSelectedIndex(1);
            foregroundTab.validate();
            backgroundTab.validate();
            tilesTypePanel.validate();
            this.validate();
            this.getParent().validate();
        }
    }
}
