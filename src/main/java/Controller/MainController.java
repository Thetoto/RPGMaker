package Controller;

import Editor.Editor;
import Model.Editor.EditorState;
import Model.Editor.ToolsEnum;
import Model.World.Map;
import Model.World.World;
import com.google.gson.Gson;

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
            if (editorState.world == null) {
                editorState.defaultWorld();
            } else {
                editorState.world.addMap(new Map(new Dimension(100, 100), "Nice"));
            }
        });

        editor.topBar.saveButton.addActionListener(e -> editorState.saveWorld());

        editor.topBar.showGridButton.addActionListener(e -> editorState.invertGrid());
    }
}
