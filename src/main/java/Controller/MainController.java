package Controller;

import Editor.Editor;
import Engine.EngineController;
import Model.Editor.EditorState;
import Model.Editor.ToolsEnum;
import Model.World.*;
import Tools.FileManager;
import Tools.PopUpManager;
import Tools.ThreadLauncher;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

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
        editorState.mapState.addObserver(editor);

        toolsController = new ToolsController(editor.toolsPane, editorState.toolsState);
        tilesController = new TilesController(editor.tilesPane, editorState.tilesState, toolsController);
        mapController = new MapController(editor.mapPane, editorState.mapState);

        editor.topBar.loadButton.addActionListener(e -> ThreadLauncher.execute(() -> editorState.getWorld()));
        editor.topBar.toolsButton.addActionListener(actionEvent -> toolsController.setTool(ToolsEnum.TOOLBOX));
        editor.topBar.createButton.addActionListener(e -> ThreadLauncher.execute(() -> PopUpManager.askNewMap(editorState)));
        editor.topBar.saveButton.addActionListener(e -> ThreadLauncher.execute(() -> editorState.saveWorld()));
        editor.topBar.showGridButton.addActionListener(e -> editorState.invertGrid());

        editor.topBar.createJar.addActionListener(e -> editorState.createJar());

        editor.topBar.play.addActionListener(e -> editorState.launchGame());

        editor.topBar.zoomMinus.addActionListener(e -> editorState.mapState.zoomChange(false));
        editor.topBar.zoomPlus.addActionListener(e -> editorState.mapState.zoomChange(true));

        editor.topBar.undoButton.addActionListener(e -> editorState.mapState.undo());
        editor.topBar.redoButton.addActionListener(e -> editorState.mapState.redo());

        editor.topBar.addNewTiles.addActionListener(e -> {
            int sizeBefore = editorState.tilesState.foregroundTiles.size() + editorState.tilesState.backgroundTiles.size();
            File f = FileManager.getFileOrDir();
            if (f == null)
                return;
            editorState.tilesState.autoAddTiles(f);
            tilesController.setupListener();
            int sizeAfter = editorState.tilesState.foregroundTiles.size() + editorState.tilesState.backgroundTiles.size();
            Tools.PopUpManager.Alert((sizeAfter - sizeBefore) + " tiles loaded");
        });

        editor.tilesPane.treePanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                TreePath tp = editor.tilesPane.treePanel.getPathForLocation(e.getX(), e.getY());
                if (tp == null)
                    return;
                DefaultMutableTreeNode o = (DefaultMutableTreeNode) tp.getLastPathComponent();
                Object obj = o.getUserObject();
                if (obj instanceof Map) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        editorState.renameMap();
                        return;
                    }
                    Map map = (Map) obj;
                    editorState.mapState.updateMap(map);
                }
                if (obj instanceof Player) {
                    //Player p = (Player) obj;
                    editorState.toolsState.setCurrentTools(ToolsEnum.PLAYER);
                }
                if (obj instanceof Teleporter) {
                    Teleporter t = (Teleporter) obj;
                    editorState.mapState.setCurrentTeleporter(t);
                    editorState.toolsState.setCurrentTools(ToolsEnum.TELEPORTER);
                }
                if (obj instanceof World) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        editorState.renameWorld();
                        return;
                    }
                }
                if (obj instanceof NPC) {
                    NPC npc = (NPC) obj;
                    editorState.mapState.setCurrentNPC(npc);
                    editorState.toolsState.setCurrentTools(ToolsEnum.PNC);
                    toolsController.setNpcMessageListener();
                }
            }
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_RELEASED) {
                    if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown())
                        editorState.mapState.undo();
                    else if (e.getKeyCode() == KeyEvent.VK_A && e.isControlDown())
                        editorState.mapState.redo();
                    else if (e.getKeyCode() == KeyEvent.VK_F5)
                        editorState.launchGame();
                    return true;
                }
                return false;
            }
        });
    }
}
