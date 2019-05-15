package Controller;

import Editor.Editor;
import Model.Editor.EditorState;

import java.awt.event.*;

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
        editor.mapPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("X: " + e.getX() / 16 + ", Y: " + e.getY() / 16);
            }
        });
    }
}
