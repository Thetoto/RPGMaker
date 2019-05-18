package Controller;

import Editor.TilesPanel;
import Model.Editor.TilesState;
import Model.Editor.ToolsEnum;

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
                tilesState.setCurrentTile(entry.getKey(), true);
                setupCheckbox();
            });
        }
        for(Map.Entry<String, JButton> entry : tilesPanel.backgroundTab.buttons.entrySet()) {
            entry.getValue().addActionListener((e) -> {
                if (toolsController.getCurrentTool() != ToolsEnum.TILES)
                    toolsController.setTool(ToolsEnum.TILES);
                tilesState.setCurrentTile(entry.getKey(), false);
                setupCheckbox();
            });
        }
    }

    public void setupCheckbox() {
        toolsController.toolsPanel.toolTilePanel.walkCheckbox.addItemListener((e) -> {
            tilesState.currentTile.defaultWalkable = e.getStateChange() == ItemEvent.SELECTED;
        });
    }
}
