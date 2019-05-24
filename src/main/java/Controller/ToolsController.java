package Controller;

import Editor.ToolsPanel;
import Model.Editor.EditorState;
import Model.Editor.Mode;
import Model.Editor.ToolsEnum;
import Model.Editor.ToolsState;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ToolsController {
    ToolsPanel toolsPanel;
    ToolsState toolsState;

    public ToolsController(ToolsPanel toolsPanel, ToolsState toolsState) {
        this.toolsPanel = toolsPanel;
        this.toolsState = toolsState;
        toolsState.addObserver(toolsPanel);

        toolsPanel.toolBoxPanel.setSpawnButton.addActionListener(e -> EditorState.getInstance().mapState.setMode(Mode.PLAYER));
        toolsPanel.toolBoxPanel.addTeleporterButton.addActionListener(e -> EditorState.getInstance().mapState.setMode(Mode.TELEPORTER));
        toolsPanel.toolBoxPanel.showWalkable.addItemListener(e -> {
            EditorState.getInstance().mapState.setShowWalk(e.getStateChange() == ItemEvent.SELECTED);
        });
        toolsPanel.toolBoxPanel.forceWalkable.addActionListener(e -> EditorState.getInstance().mapState.forceWalkable(true));
        toolsPanel.toolBoxPanel.forceUnwalkable.addActionListener(e -> EditorState.getInstance().mapState.forceWalkable(false));

        toolsPanel.toolTeleporterPanel.setDestButton.addActionListener(e -> EditorState.getInstance().mapState.setMode(Mode.TELEPORTERDEST));
        toolsPanel.toolTeleporterPanel.deleteMe.addActionListener(e -> EditorState.getInstance().mapState.deleteTeleport());
    }

    public ToolsEnum getCurrentTool() {
        return toolsState.currentTools;
    }

    public void setTool(ToolsEnum e) {
        toolsState.setCurrentTools(e);
    }

    public void setNpcMessageListener() {
        toolsPanel.toolNPCPanel.message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String newMessage = toolsPanel.toolNPCPanel.message.getText();
                EditorState.getInstance().mapState.getCurrentNPC().setMessage(newMessage);
            }
        });
        toolsPanel.toolNPCPanel.deleteMe.addActionListener(e -> EditorState.getInstance().mapState.deleteNPC());
    }
}
