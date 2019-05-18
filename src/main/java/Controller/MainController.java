package Controller;

import Editor.Editor;
import Model.Editor.EditorState;
import Model.Editor.Mode;
import Model.Editor.ToolsEnum;
import Model.World.Map;
import Model.World.Player;
import Tools.PopUpManager;
import Tools.ThreadLauncher;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ItemEvent;

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

        editor.toolsPane.toolBoxPanel.setSpawnButton.addActionListener(e -> editorState.mapState.setMode(Mode.PLAYER));
        editor.toolsPane.toolBoxPanel.addTeleporterButton.addActionListener(e -> editorState.mapState.setMode(Mode.TELEPORTER));
        editor.toolsPane.toolBoxPanel.showWalkable.addItemListener(e -> {
            editorState.mapState.setShowWalk(e.getStateChange() == ItemEvent.SELECTED);
        });

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
        });
    }
}
