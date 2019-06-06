package Controller;

import Editor.Editor;
import Editor.ToolsPanel;
import Model.Editor.EditorState;
import Model.Editor.Mode;
import Model.Editor.ToolsEnum;
import Model.Editor.ToolsState;
import Model.World.Foreground;
import Model.World.NPC;
import Model.World.Tile;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

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
        toolsPanel.toolBoxPanel.activeTimeCycle.addActionListener(e -> EditorState.getInstance().reverseTimeCycleSetting());
        toolsPanel.toolBoxPanel.dayTime.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            EditorState.getInstance().world.timeCycle.setDayDuration((int)spinner.getModel().getValue());
        });
        toolsPanel.toolBoxPanel.nightTime.addChangeListener(e -> {
            JSpinner spinner = (JSpinner) e.getSource();
            EditorState.getInstance().world.timeCycle.setNightDuration((int)spinner.getModel().getValue());
        });

        toolsPanel.toolTeleporterPanel.setDestButton.addActionListener(e -> EditorState.getInstance().mapState.setMode(Mode.TELEPORTERDEST));
        toolsPanel.toolTeleporterPanel.deleteMe.addActionListener(e -> EditorState.getInstance().mapState.deleteTeleport());

        toolsPanel.toolNPCPanel.isMoving.addActionListener(e -> {
            JCheckBox tmp = (JCheckBox) e.getSource();
            EditorState.getInstance().mapState.getCurrentNPC().setMoving(tmp.isSelected());
        });

        toolsPanel.toolForegroundPanel.setBreakable.addItemListener(e -> {
            Foreground f = EditorState.getInstance().mapState.getCurrentForeground();
            f.isBreakable = (e.getStateChange() == ItemEvent.SELECTED);
            toolsPanel.toolForegroundPanel.UpdateBreaker(f);
        });
        toolsPanel.toolForegroundPanel.setAsHide.addItemListener(e -> {
            EditorState.getInstance().mapState.getCurrentForeground().isHided = (e.getStateChange() == ItemEvent.SELECTED);
            toolsPanel.toolForegroundPanel.UpdateHider(e.getStateChange() == ItemEvent.SELECTED);
        });
        toolsPanel.toolForegroundPanel.setPickable.addItemListener(e -> EditorState.getInstance().mapState.getCurrentForeground().isPickable = (e.getStateChange() == ItemEvent.SELECTED));
        toolsPanel.toolForegroundPanel.hider.addItemListener(e -> {
            Object o = e.getItem();
            if (o != null) {
                if (o instanceof NPC) {
                    NPC npc = (NPC)o;
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        System.err.println("Add " + EditorState.getInstance().mapState.getCurrentForeground() + " to hideable of " + npc);
                        npc.getRevealForeground().add(EditorState.getInstance().mapState.getCurrentForeground().getPoint());
                    } else {
                        System.err.println("Remove " + EditorState.getInstance().mapState.getCurrentForeground() + " to hideable of " + npc);
                        npc.getRevealForeground().remove(EditorState.getInstance().mapState.getCurrentForeground().getPoint());
                    }
                }

            }
        });
        toolsPanel.toolForegroundPanel.breaker.addActionListener(e -> {
            JComboBox jcb = (JComboBox) e.getSource();
            String s = (String) jcb.getSelectedItem();
            if (s != null) {
                if (s.equals("------"))
                    s = "";
                EditorState.getInstance().mapState.getCurrentForeground().breaker = s;
            }
        });

        toolsPanel.toolPlayerPanel.setSpawn.addActionListener(e -> EditorState.getInstance().mapState.setMode(Mode.PLAYER));
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
