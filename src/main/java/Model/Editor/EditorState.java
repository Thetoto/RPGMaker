package Model.Editor;

import Editor.MapPanel;
import Model.World.Map;

import java.awt.*;
import java.util.Observable;

public class EditorState extends Observable {
    public ToolsState toolsState;
    public String text;
    public Map map;

    public Point selectionIn;
    public Point selectionOut;

    public EditorState() {
        toolsState = new ToolsState();
        text = "Button";
        map = new Map(new Dimension(20, 20));
        selectionIn = null;
        selectionOut = null;
    }

    public void tmpAction(String new_text) {
        text = new_text;
        setChanged();
        notifyObservers(this);
    }

    public void mouseClicked(Point in, Point out) {
        this.selectionIn = in;
        this.selectionOut = out;

        setChanged();
        notifyObservers("mouseClicked");
    }
}
