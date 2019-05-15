package Controller;

import Editor.Editor;
import Model.Editor.EditorState;

import java.awt.event.*;

public class MainController {
    Editor editor;
    EditorState editorState;

    ToolsController toolsController;
    public MainController() {
        editor = new Editor();
        editorState = new EditorState();

        editorState.addObserver(editor);

        toolsController = new ToolsController(editor.toolsPane, editorState.toolsState);

        editor.mapPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / 16;
                int y = e.getY() / 16;
                System.out.println("X: " + x + ", Y: " + y);
                editorState.mouseClicked(x, y, editor.mapPane);
            }
        });

        editor.topBar.toolsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                toolsController.setToolBox();
            }
        });
    }
}
