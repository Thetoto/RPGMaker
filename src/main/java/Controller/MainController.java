package Controller;

import Editor.Editor;
import Model.Editor.EditorState;
import Model.Editor.ToolsEnum;
import Model.World.Map;
import Model.World.World;
import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainController {
    Editor editor;
    EditorState editorState;

    ToolsController toolsController;
    TilesController tilesController;
    MapController mapController;

    public MainController() {
        editor = new Editor();
        editorState = new EditorState();

        editorState.addObserver(editor);

        toolsController = new ToolsController(editor.toolsPane, editorState.toolsState);
        tilesController = new TilesController(editor.tilesPane, editorState.tilesState, toolsController);
        mapController = new MapController(editor.mapPane, editorState.mapState);

        editor.topBar.loadButton.addActionListener(e -> editorState.getWorld());

        editor.topBar.toolsButton.addActionListener(actionEvent -> toolsController.setTool(ToolsEnum.TOOLBOX));

        editor.topBar.createButton.addActionListener(e -> {
            AskNewMap();
        });

        editor.topBar.saveButton.addActionListener(e -> editorState.saveWorld());

        editor.topBar.showGridButton.addActionListener(e -> editorState.invertGrid());
        editor.tilesPane.treePanel.addTreeSelectionListener(e -> {
            var tp = e.getNewLeadSelectionPath();
            if (tp == null)
                return;
            DefaultMutableTreeNode o = (DefaultMutableTreeNode) tp.getLastPathComponent();
            Object obj = o.getUserObject();
            if (obj instanceof Map) {
                Map map = (Map) obj;
                editorState.mapState.updateMap(map);
            }
        });
    }

    private void AskNewMap() {
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(4,2));
        frame.setLocationRelativeTo(null);
        frame.setTitle("Choose the configuration");

        JLabel name_panel = new JLabel("Name");
        JLabel width_panel = new JLabel("Width");
        JLabel height_panel = new JLabel("Height");

        JButton validate = new JButton( "Validate");
        JTextField name = new JTextField();
        JSpinner width = new JSpinner();
        JSpinner height = new JSpinner();

        name.setSize(50, 1);
        name.setText("New map");
        name.setVisible(true);
        width.setModel(new SpinnerNumberModel(20, 1, 800, 1));
        width.setVisible(true);
        height.setModel(new SpinnerNumberModel(20, 1, 800, 1));
        height.setVisible(true);
        validate.addActionListener(e -> {
            int Mwidth = (int) width.getModel().getValue();
            int Mheight = (int) height.getModel().getValue();
            String Mname = name.getText();
            frame.dispose();
            Map map = new Map(new Dimension(Mwidth, Mheight), Mname);
            if (editorState.world == null) {
                editorState.defaultWorld(map);
            } else {
                editorState.addMap(map);
            }
        });


        frame.add(name_panel);
        frame.add(name);
        frame.add(width_panel);
        frame.add(width);
        frame.add(height_panel);
        frame.add(height);
        frame.add(validate);

        frame.validate();
        frame.setVisible(true);
        frame.pack();
        frame.setSize(250, 150);
    }
}
