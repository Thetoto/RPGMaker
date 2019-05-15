package Model.Editor;

import java.util.Observable;

public class EditorState extends Observable {
    public String text;
    public EditorState() {
        text = "Button";
    }

    public void tmpAction(String new_text) {
        text = new_text;

        notifyObservers(this);
    }
}
