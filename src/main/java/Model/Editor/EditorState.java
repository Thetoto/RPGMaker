package Model.Editor;

import Model.World.Map;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Observable;

public class EditorState extends Observable {
    public ToolsState toolsState;
    public String text;
    public Map map;

    public Point selectionIn;
    public Point selectionOut;
    public File dir;

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

    public void getDir() {
        JFrame frame = new JFrame();
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        frame.setContentPane(fc);
        int res = fc.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            dir = fc.getSelectedFile();
        }
        frame.setVisible(false);
        frame.dispose();
        setChanged();
        notifyObservers("New Directory Selected");
    }
}
