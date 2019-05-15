package Editor;

import Model.Editor.ToolsEnum;
import Model.Editor.ToolsState;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ToolsPanel extends JPanel implements Observer {
    ToolsEnum currentPanel;
    ToolBoxPanel toolBoxPanel;

    public ToolsPanel() {
        toolBoxPanel = new ToolBoxPanel();
        this.add(toolBoxPanel);

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

        switch (currentPanel) {
            case TOOLBOX:
                toolBoxPanel.setVisible(true);
                break;
            case NONE:
                break;
        }
    }
}
