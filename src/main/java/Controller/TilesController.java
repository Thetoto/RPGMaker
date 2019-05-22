package Controller;

import Editor.Editor;
import Editor.TilesPanel;
import Model.Editor.EditorState;
import Model.Editor.TileType;
import Model.Editor.TilesState;
import Model.Editor.ToolsEnum;
import Model.World.BigTile;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;

public class TilesController {
    TilesPanel tilesPanel;
    TilesState tilesState;
    ToolsController toolsController;

    public TilesController(TilesPanel tilesPanel, TilesState tilesState, ToolsController toolsController) {
        this.tilesPanel = tilesPanel;
        this.tilesState = tilesState;
        this.toolsController = toolsController;
        tilesState.addObserver(toolsController.toolsPanel.toolTilePanel);
        tilesState.addObserver(tilesPanel);
        tilesState.addDefaultTiles();
        setupListener();
    }

    public void setupListener() {
        for(Map.Entry<String, JButton> entry : tilesPanel.foregroundTab.buttons.entrySet()) {
            entry.getValue().addActionListener((e) -> {
                if (toolsController.getCurrentTool() != ToolsEnum.TILES)
                    toolsController.setTool(ToolsEnum.TILES);
                tilesState.setCurrentTile(entry.getKey(), TileType.FOREGROUND);
                setupCheckboxAndBigTile();
            });
        }
        for(Map.Entry<String, JButton> entry : tilesPanel.backgroundTab.buttons.entrySet()) {
            entry.getValue().addActionListener((e) -> {
                if (toolsController.getCurrentTool() != ToolsEnum.TILES)
                    toolsController.setTool(ToolsEnum.TILES);
                tilesState.setCurrentTile(entry.getKey(), TileType.BACKGROUND);
                setupCheckboxAndBigTile();
            });
        }
        for(Map.Entry<String, JButton> entry : tilesPanel.NPCTab.buttons.entrySet()) {
            entry.getValue().addActionListener((e) -> {
                if (toolsController.getCurrentTool() != ToolsEnum.TILES)
                    toolsController.setTool(ToolsEnum.TILES);
                tilesState.setCurrentTile(entry.getKey(), TileType.NPC);
                setupCheckboxAndBigTile();
            });
        }
    }

    public void setupCheckboxAndBigTile() {
        toolsController.toolsPanel.toolTilePanel.walkCheckbox.addItemListener((e) -> {
            tilesState.currentTile.setDefaultWalkable(e.getStateChange() == ItemEvent.SELECTED);
        });
        toolsController.toolsPanel.toolTilePanel.bigTileAll.addActionListener((e) -> {
            ((BigTile)tilesState.currentTile).setCur(-1);
        });
        toolsController.toolsPanel.toolTilePanel.setAsBackground.addActionListener(e -> {
            EditorState.getInstance().mapState.setBackgroundCurrentTile();
        });
        toolsController.toolsPanel.toolTilePanel.setPlayerAnim.addActionListener(e -> {
            EditorState.getInstance().world.getPlayer().setAnim();
        });
        for (int i = 0;  i < toolsController.toolsPanel.toolTilePanel.bigTileSeclector.size(); i++) {
            final Integer j = i;
            toolsController.toolsPanel.toolTilePanel.bigTileSeclector.get(i).addActionListener((e) -> {
                ((BigTile)tilesState.currentTile).setCur(j);
            });
        }
    }
}
