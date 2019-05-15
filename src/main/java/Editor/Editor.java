package Editor;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Editor extends JFrame implements Observer {

    private JMenuBar menuBar;
    private JPanel mainPane;

    private TilesPanel tilesPane;
    private MapPanel mapPane;
    private JPanel toolsPane;

    public Editor() {
        this.setSize(1080,720);
        this.setTitle("BibleRPG - Premium Edition");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUpEditor();
    }


    private void setUpEditor() {
        menuBar = new JMenuBar();
        menuBar.add(new JMenu("coucou"));
        this.setJMenuBar(menuBar);


        tilesPane = new TilesPanel();
        mapPane = new MapPanel();

        toolsPane = new JPanel();
        toolsPane.setBackground(Color.GREEN);

        mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.add(tilesPane, BorderLayout.WEST);
        mainPane.add(mapPane, BorderLayout.CENTER);
        mainPane.add(toolsPane, BorderLayout.EAST);

        this.setContentPane(mainPane);

        setAllVisible();
    }

    private void setAllVisible() {
        menuBar.setVisible(true);

        mapPane.setVisible(true);
        toolsPane.setVisible(true);
        tilesPane.setVisible(true);

        mainPane.setVisible(true);
        this.setVisible(true);
    }

    public void update(Observable observable, Object o) {

    }
}
