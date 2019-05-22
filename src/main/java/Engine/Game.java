package Engine;

import Model.World.Direction;

import java.awt.event.KeyEvent;

public class Game {
    public Game(EngineController controller) {
        long last_time = System.nanoTime();

        while (!controller.keyState.get(KeyEvent.VK_ESCAPE)) {
            long time = System.nanoTime();
            int delta_time = (int)((time - last_time) / 1000000);
            last_time = time;

            boolean hasMoved = false;
            if (controller.keyState.get(KeyEvent.VK_Z))
                hasMoved |= controller.state.movePerso(Direction.UP, delta_time);
            if (controller.keyState.get(KeyEvent.VK_Q))
                hasMoved |= controller.state.movePerso(Direction.LEFT, delta_time);
            if (controller.keyState.get(KeyEvent.VK_S))
                hasMoved |= controller.state.movePerso(Direction.DOWN, delta_time);
            if (controller.keyState.get(KeyEvent.VK_D))
                hasMoved |= controller.state.movePerso(Direction.RIGHT, delta_time);
            if (hasMoved)
                controller.state.redrawPerso();

            try {
                Thread.sleep(10, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
