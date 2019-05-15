package Editor;

import Model.Editor.EditorState;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Editor extends JFrame implements Observer {

    public JMenuBar menuBar;
    public JPanel mainPane;

    public TilesPanel tilesPane;
    public MapPanel mapPane;
    public JPanel toolsPane;

    public JButton tmpButton;

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

        tmpButton = new JButton("click");
        toolsPane.add(tmpButton);

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
        if (o instanceof EditorState) {
            EditorState obj = (EditorState)o;
            tmpButton.setText(obj.text);
        }
    }
}
