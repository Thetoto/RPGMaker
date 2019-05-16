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

        editor.mapPane.addMouseCompleteListener(new MouseAdapter() {
            Point mouseEnter = null;

            public void mousePressed(MouseEvent e) {
                if (editorState.selectionIn != null) {
                    mouseEnter = null;
                    System.out.println("Deselect");
                    editorState.mouseClicked(null, null);
                    return;
                }
                mouseEnter = new Point(e.getX() / 16, e.getY() / 16);
                System.out.println("In  | X: " + mouseEnter.x + ", Y: " + mouseEnter.y);
                editorState.mouseClicked(mouseEnter, mouseEnter);
            }

            public void mouseReleased(MouseEvent e) {
                mouseEnter = null;
            }

            public void mouseDragged(MouseEvent e) {
                if (mouseEnter == null)
                    return;
                Point mouseOut =  new Point(e.getX() / 16, e.getY() / 16);
                System.out.println("In  | X: " + mouseOut.x + ", Y: " + mouseOut.y);
                editorState.mouseClicked(mouseEnter, mouseOut);
            }
        });

        editor.topBar.loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editorState.getWorld();
            }
        });

        editor.topBar.toolsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                toolsController.setToolBox();
            }
        });
    }
}
