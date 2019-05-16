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
    public ToolsPanel toolsPane;

    public TopBar topBar;

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

        topBar = new TopBar();

        tilesPane = new TilesPanel();
        mapPane = new MapPanel();

        toolsPane = new ToolsPanel();
        toolsPane.setBackground(Color.GREEN);

        mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.add(tilesPane, BorderLayout.WEST);
        mainPane.add(mapPane, BorderLayout.CENTER);
        mainPane.add(toolsPane, BorderLayout.EAST);
        mainPane.add(topBar, BorderLayout.NORTH);

        this.setContentPane(mainPane);

        this.setVisible(true);
    }

    @Override
    public void setVisible(boolean flag) {
        menuBar.setVisible(flag);

        mapPane.setVisible(flag);

        tilesPane.setVisible(flag);
        topBar.setVisible(flag);

        mainPane.setVisible(flag);
        super.setVisible(flag);
    }

    public void update(Observable observable, Object o) {
        if (o instanceof EditorState) {
            EditorState obj = (EditorState)o;
            tmpButton.setText(obj.text);
        }
        if (o instanceof String){
            String arg = (String) o;
            if (arg.equals("mouseClicked")) {
                EditorState coords = (EditorState) observable;
                mapPane.show_selection(coords.selectionIn, coords.selectionOut);
            }
        }
    }
}
