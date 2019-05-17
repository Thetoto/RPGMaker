package Editor;

import Model.Editor.ToolsEnum;
import Model.Editor.ToolsState;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ToolsPanel extends JPanel implements Observer {
    public ToolsEnum currentPanel;
    public ToolBoxPanel toolBoxPanel;
    public ToolTilePanel toolTilePanel;

    public ToolsPanel() {
        toolBoxPanel = new ToolBoxPanel();
        toolTilePanel = new ToolTilePanel();
        this.add(toolBoxPanel);
        this.add(toolTilePanel);

        this.setVisible(false);
    }

    public void update(Observable observable, Object o) {
        if (o instanceof ToolsEnum) {
            currentPanel = (ToolsEnum)o;
            this.setVisible(true);
        }
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);

        if (!aFlag)
            return;

        toolBoxPanel.setVisible(false);
        toolTilePanel.setVisible(false);

        switch (currentPanel) {
            case TOOLBOX:
                toolBoxPanel.setVisible(true);
                break;
            case TILES:
                toolTilePanel.setVisible(true);
                break;
            case NONE:
                setVisible(false);
                break;
        }
    }
}
