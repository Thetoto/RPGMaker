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

    public MainController() {
        editor = new Editor();

        editorState = new EditorState();

        editorState.addObserver(editor);

        toolsController = new ToolsController(editor.toolsPane, editorState.toolsState);
        tilesController = new TilesController(editor.tilesPane, editorState.tilesState, toolsController);

        editor.mapPane.addMouseCompleteListener(new MouseAdapter() {
            Point mouseEnter = null;

            public void mousePressed(MouseEvent e) {
                if (editorState.selectionIn != null) {
                    mouseEnter = null;
                    System.out.println("Deselect");
                    editorState.mouseClicked(null, null);
                    return;
                }
                mouseEnter = new Point(e.getX() / 16, e.getY() / 16);
                System.out.println("In  | X: " + mouseEnter.x + ", Y: " + mouseEnter.y);
                editorState.mouseClicked(mouseEnter, mouseEnter);
            }

            public void mouseReleased(MouseEvent e) {
                mouseEnter = null;
            }

            public void mouseDragged(MouseEvent e) {
                if (mouseEnter == null)
                    return;
                Point mouseOut =  new Point(e.getX() / 16, e.getY() / 16);
                System.out.println("In  | X: " + mouseOut.x + ", Y: " + mouseOut.y);
                editorState.mouseClicked(mouseEnter, mouseOut);
            }
        });

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
    }
}
