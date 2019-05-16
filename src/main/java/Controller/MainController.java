package Controller;

import Editor.Editor;
import Model.Editor.EditorState;

import java.awt.*;
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
            Point mouseEnter = null;

            public void mousePressed(MouseEvent e) {
                mouseEnter =  new Point(e.getX() / 16, e.getY() / 16);
                System.out.println("In  | X: " + mouseEnter.x + ", Y: " + mouseEnter.y);
            }

            public void mouseReleased​(MouseEvent e) {
                if (mouseEnter == null)
                    return;
                Point mouseOut =  new Point(e.getX() / 16, e.getY() / 16);
                System.out.println("Out | X: " + mouseOut.x + ", Y: " + mouseOut.y);
                editorState.mouseClicked(mouseEnter, mouseOut);
            }
        });

        editor.topBar.toolsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                toolsController.setToolBox();
            }
        });
    }
}
