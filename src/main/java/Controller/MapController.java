package Controller;

import Editor.Editor;
import Editor.MapPanel;
import Model.Editor.EditorState;
import Model.Editor.MapState;
import Model.World.Map;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapController {
    MapPanel mapPanel;
    MapState mapState;

    public MapController(MapPanel mapPanel, MapState mapState) {
        this.mapPanel = mapPanel;
        this.mapState = mapState;
        mapState.addObserver(mapPanel);

        mapPanel.addMouseCompleteListener(new MouseAdapter() {
            Point mouseEnter = null;
            Point mouseOut = null;

            public void mouseEntered(MouseEvent e) {
                mouseMoved(e);
            }

            public void mouseExited(MouseEvent e) {
                mapState.mouseOver(null);
            }

            public void mouseMoved(MouseEvent e) {
                Point over = getPointCoords(e.getX(), e.getY());
                mapState.mouseOver(over);
            }

            public void mousePressed(MouseEvent e) {
                if (mapState.currentMap == null)
                    return;
                if (mapState.selectionIn != null) {
                    mouseEnter = null;
                    System.out.println("Deselect");
                    mapState.mousePreview(null, null);
                    return;
                }
                mouseEnter = getPointCoords(e.getX(), e.getY());
                mapState.mousePreview(mouseEnter, mouseEnter);
            }

            public void mouseReleased(MouseEvent e) {
                if (mouseEnter == null)
                    return;
                mapState.mouseClick();
                mouseEnter = null;
                mouseOut = null;
            }

            public void mouseDragged(MouseEvent e) {
                if (mouseEnter == null)
                    return;
                Point mouseOut = getPointCoords(e.getX(), e.getY());
                mapState.mousePreview(mouseEnter, mouseOut);
            }
        });
    }

    public static Point getPointCoords(int x, int y) {
        int divdeBy = EditorState.getInstance().showGrid ? 17 : 16;
        return new Point(x / divdeBy, y / divdeBy);
    }
}
