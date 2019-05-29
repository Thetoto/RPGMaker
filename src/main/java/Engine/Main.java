package Engine;

import Model.Editor.EditorState;
import Model.Editor.TileType;
import Model.World.World;

public class Main {
    public static Boolean standalone = false;
    public static void main(String[] args) {
        System.out.println("LINK START");
        standalone = true;
        EditorState e = new EditorState();
        e.tilesState.addDirectoryTilesResource("background", TileType.BACKGROUND);
        e.tilesState.addDirectoryTilesResource("foreground", TileType.FOREGROUND);
        e.tilesState.addDirectoryTilesResource("npc", TileType.NPC);
        World w = e.getWorldJAR();
        new EngineController(w);
    }
}
