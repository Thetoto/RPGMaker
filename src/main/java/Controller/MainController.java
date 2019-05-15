package Controller;

import Editor.Editor;
import Model.Editor.EditorState;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    Editor editor;
    EditorState editorState;
    public MainController() {
        editor = new Editor();
        editorState = new EditorState();

        editorState.addObserver(editor);

        editor.tmpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                editorState.tmpAction("J'ai click");
            }
        });
    }
}
