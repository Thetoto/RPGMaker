package Tools;

import Model.World.Map;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class ActionManager {
    static Stack<Map> undo = new Stack<Map>();
    static Stack<Map> redo = new Stack<Map>();

    public static void saveAction(Map map) {
        undo.add(map);
        redo.clear();
    }

    public static Map undo(Map cur_map) {
        Map map = undo.pop();
        if (map != null)
            redo.add(cur_map);
        return map;
    }

    public static Map redo(Map cur_map) {
        Map map = redo.pop();
        if (map != null) {
            undo.add(cur_map);
        }
        return map;
    }
}
