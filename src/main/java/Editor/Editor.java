package Editor;

import Model.Editor.EditorState;
import Tools.Tools;

import javax.imageio.ImageIO;
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
        this.setTitle("BibleRPG - Premium ULTIMATE ++ AllInclusive Remake Edition Deluxe");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(mapPane);
        JScrollPane scrollFrame = new JScrollPane(panel);
        scrollFrame.getVerticalScrollBar().setUnitIncrement(16);
        scrollFrame.getHorizontalScrollBar().setUnitIncrement(16);

        mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.add(tilesPane, BorderLayout.WEST);
        mainPane.add(scrollFrame, BorderLayout.CENTER);
        mainPane.add(toolsPane, BorderLayout.EAST);
        mainPane.add(topBar, BorderLayout.NORTH);

        this.setContentPane(mainPane);

        this.setLocationRelativeTo(null);
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
        if (o instanceof String && observable instanceof EditorState){
            String arg = (String) o;
            EditorState obs = (EditorState) observable;
            if (arg.equals("New World") || arg.equals("New Map")) {
                tilesPane.treePanel.show_world(obs.world);
            }
        }
    }

    public static JButton initIconButton(String path, JPanel panel) {
        JButton button =  new JButton();
        try {
            Image img = ImageIO.read(Tools.getFileFromRessources(path));

            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        panel.add(button);
        button.setPreferredSize(new Dimension(32,32));
        return button;
    }
}
