package Controller;

import Editor.Editor;
import Model.Editor.EditorState;
import Model.Editor.Mode;
import Model.Editor.ToolsEnum;
import Model.World.Map;
import Model.World.Player;
import Tools.FileManager;
import Model.World.Teleporter;
import Tools.PopUpManager;
import Tools.ThreadLauncher;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
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

        editor.toolsPane.toolBoxPanel.setSpawnButton.addActionListener(e -> editorState.mapState.setMode(Mode.PLAYER));
        editor.toolsPane.toolBoxPanel.addTeleporterButton.addActionListener(e -> editorState.mapState.setMode(Mode.TELEPORTER));
        editor.toolsPane.toolBoxPanel.showWalkable.addItemListener(e -> {
            editorState.mapState.setShowWalk(e.getStateChange() == ItemEvent.SELECTED);
        });
        editor.toolsPane.toolBoxPanel.forceWalkable.addActionListener(e -> editorState.mapState.forceWalkable(true));
        editor.toolsPane.toolBoxPanel.forceUnwalkable.addActionListener(e -> editorState.mapState.forceWalkable(false));

        editor.toolsPane.toolTeleporterPanel.setDestButton.addActionListener(e -> editorState.mapState.setMode(Mode.TELEPORTERDEST));

        editor.toolsPane.toolPlayerPanel.setAnim.addActionListener(e -> editorState.world.getPlayer().setAnim());

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
            if (obj instanceof Player) {
                Player p = (Player) obj;
                editor.toolsPane.updateToPlayer(p);
            }
            if (obj instanceof Teleporter) {
                Teleporter t = (Teleporter) obj;
                editor.toolsPane.updateToTeleporter(t);
                editorState.mapState.setCurrentTeleporter(t);
            }
        });
    }
}
