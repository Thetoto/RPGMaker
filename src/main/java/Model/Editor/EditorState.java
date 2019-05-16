package Model.Editor;

import Model.World.Map;
import Model.World.World;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Observable;

public class EditorState extends Observable {
    public ToolsState toolsState;
    public String text;
    public Map map;

    public Point selectionIn;
    public Point selectionOut;
    public World world;

    public EditorState() {
        toolsState = new ToolsState();
        text = "Button";
        map = new Map(new Dimension(20, 20), "Default");
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

    public void getWorld() {
        JFrame frame = new JFrame();
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        frame.setContentPane(fc);
        int res = fc.showOpenDialog(frame);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Gson gson = new Gson();
            System.out.println(file.toString() + " " + gson);
            try {
                world = gson.fromJson(new FileReader(file), World.class);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        frame.setVisible(false);
        frame.dispose();
        setChanged();
        notifyObservers("New World");
    }

    public void defaultWorld() {
        world = new World("New World");
        world.addMap(new Map(new Dimension(100, 100), "Nice"));
        setChanged();
        notifyObservers("New World");
    }
}
