package Controller;

import Editor.ToolsPanel;
import Model.Editor.ToolsEnum;
import Model.Editor.ToolsState;

public class ToolsController {
    ToolsPanel toolsPanel;
    ToolsState toolsState;

    public ToolsController(ToolsPanel toolsPanel, ToolsState toolsState) {
        this.toolsPanel = toolsPanel;
        this.toolsState = toolsState;
        toolsState.addObserver(toolsPanel);
    }

    public void setToolBox() {
        toolsState.setCurrentTools(ToolsEnum.TOOLBOX);
    }
}
