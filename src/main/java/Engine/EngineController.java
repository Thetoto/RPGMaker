package Engine;

import Model.World.Direction;
import Model.World.World;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EngineController {
    Engine frame;
    EngineState state;

    public EngineController(World world) {
        frame = new Engine();
        state = new EngineState(world);
        state.addObserver(frame.mapPanel);
        state.init();

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                System.out.println("Key pressed " + keyEvent.getKeyCode());
                Direction dir = null;
                if (keyEvent.getKeyCode() == KeyEvent.VK_Z)
                    dir = Direction.UP;
                else if (keyEvent.getKeyCode() == KeyEvent.VK_Q)
                    dir = Direction.LEFT;
                else if (keyEvent.getKeyCode() == KeyEvent.VK_S)
                    dir = Direction.DOWN;
                else if (keyEvent.getKeyCode() == KeyEvent.VK_D)
                    dir = Direction.RIGHT;
                if (dir != null)
                    state.redrawPerso(dir);
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
    }
}
