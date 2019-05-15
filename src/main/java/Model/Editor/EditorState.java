package Model.Editor;

import Editor.MapPanel;
import Model.World.Map;

import java.awt.*;
import java.util.Observable;

public class EditorState extends Observable {
    public String text;
    public Map map;
    public Point selection;

    public EditorState() {
        text = "Button";
        map = new Map(new Dimension(20, 20));
        selection = null;
    }

    public void tmpAction(String new_text) {
        text = new_text;
        setChanged();
        notifyObservers(this);
    }

    public void mouseClicked(int x, int y, MapPanel mapPanel) {
        selection = new Point(x, y);
        setChanged();
        notifyObservers("mouseClicked");
    }
}
