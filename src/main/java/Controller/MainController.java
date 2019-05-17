package Controller;

import Editor.Editor;
import Model.Editor.EditorState;
import Model.Editor.ToolsEnum;
import Model.World.Map;
import Model.World.World;
import Tools.PopUpManager;
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
            PopUpManager.askNewMap(editorState);
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
}
