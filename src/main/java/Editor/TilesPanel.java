package Editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TilesPanel extends JPanel {
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
        initTiles("background", backgroundTab);
        initTiles("foreground", foregroundTab);

        tilesTypePanel = new JTabbedPane();
        tilesTypePanel.add("Background", backgroundTab);
        tilesTypePanel.add("Foreground", foregroundTab);

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

    public void initTiles(String path, JPanel panel) {
        try (Stream<Path> paths = Files.walk(Paths.get(ClassLoader.getSystemClassLoader().getResource(path).getPath()))) {
            paths.filter(Files::isRegularFile).forEach((file) -> {
                JButton button =  new JButton();

                BufferedImage img = null;
                try {
                    img = ImageIO.read(file.toFile());
                    button.setIcon(new ImageIcon(img));

                    panel.add(button);
                    button.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
                } catch (IOException e) {
                    System.err.println("Can't load this file : " + file.getFileName());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
