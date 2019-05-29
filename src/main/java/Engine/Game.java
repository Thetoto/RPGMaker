package Engine;

import Model.World.Direction;
import Model.World.Map;
import Model.World.NPC;

import java.awt.event.KeyEvent;

import static Model.World.Direction.*;

public class Game {
    public Game(EngineController controller) {
        long last_time = System.nanoTime();
        boolean does_action = false;

        while (!controller.keyState.get(KeyEvent.VK_ESCAPE)) {
            long time = System.nanoTime();
            int delta_time = (int)((time - last_time) / 1000000);
            last_time = time;

            boolean hasMoved = false;
            if (!does_action && controller.keyState.get(KeyEvent.VK_Z))
                hasMoved |= controller.state.movePerso(Direction.UP, delta_time);
            if (!does_action && controller.keyState.get(KeyEvent.VK_Q))
                hasMoved |= controller.state.movePerso(Direction.LEFT, delta_time);
            if (!does_action && controller.keyState.get(KeyEvent.VK_S))
                hasMoved |= controller.state.movePerso(DOWN, delta_time);
            if (!does_action && controller.keyState.get(KeyEvent.VK_D))
                hasMoved |= controller.state.movePerso(Direction.RIGHT, delta_time);
            if (hasMoved)
                controller.state.redrawPerso();

            moveNPCs(controller.state.currentMap, delta_time);
            controller.state.redrawNPC();

            if (controller.keyState.get(KeyEvent.VK_E)) {
                controller.keyState.set(KeyEvent.VK_E, false);
                if (does_action) {
                    controller.state.shutUp();
                    does_action = false;
                    continue;
                }
                does_action = controller.state.talk();
            }

            if (controller.keyState.get(KeyEvent.VK_P)) {
                controller.keyState.set(KeyEvent.VK_P, false);
                controller.pauseGame();
                last_time = System.nanoTime();
            }

            controller.state.checkTeleporter();

            try {
                Thread.sleep(10, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void moveNPCs(Map map, int deltaTime) {
        for (NPC npc : map.getNpcs()) {
            if (npc.isMoving()) {
                switch ((int)(Math.random() * 4)) {
                    case 0:
                        npc.moveNPC(DOWN, deltaTime, map);
                        break;
                    case 1:
                        npc.moveNPC(LEFT, deltaTime, map);
                        break;
                    case 2:
                        npc.moveNPC(RIGHT, deltaTime, map);
                        break;
                    case 3:
                        npc.moveNPC(UP, deltaTime, map);
                        break;
                }
            }
        }
    }
}
