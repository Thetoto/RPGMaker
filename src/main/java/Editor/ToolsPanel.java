package Editor;

import Model.Editor.EditorState;
import Model.Editor.ToolsEnum;
import Model.Editor.ToolsState;
import Model.World.Map;
import Model.World.Player;
import Model.World.Teleporter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ToolsPanel extends JPanel implements Observer {
    public ToolsEnum currentPanel;
    public ToolBoxPanel toolBoxPanel;
    public ToolTilePanel toolTilePanel;
    public ToolPlayerPanel toolPlayerPanel;
    public ToolTeleporterPanel toolTeleporterPanel;

    public ToolsPanel() {
        toolBoxPanel = new ToolBoxPanel();
        toolTilePanel = new ToolTilePanel();
        toolPlayerPanel = new ToolPlayerPanel();
        toolTeleporterPanel = new ToolTeleporterPanel();
        this.add(toolBoxPanel);
        this.add(toolTilePanel);
        this.add(toolPlayerPanel);
        this.add(toolTeleporterPanel);

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
        toolPlayerPanel.setVisible(false);
        toolTeleporterPanel.setVisible(false);

        switch (currentPanel) {
            case TOOLBOX:
                toolBoxPanel.setVisible(true);
                break;
            case TILES:
                toolTilePanel.setVisible(true);
                break;
            case PLAYER:
                toolPlayerPanel.updateInfo(EditorState.getInstance().world.getPlayer());
                toolPlayerPanel.setVisible(true);
                break;
            case TELEPORTER:
                toolTeleporterPanel.update(EditorState.getInstance().mapState.getCurrentTeleporter());
                toolTeleporterPanel.setVisible(true);
                break;
            case NONE:
                setVisible(false);
                break;
        }
    }
}
