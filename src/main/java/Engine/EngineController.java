package Engine;

import Model.World.World;
import Tools.ThreadLauncher;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class EngineController {
    Engine frame;
    EngineState state;
    Vector<Boolean> keyState = new Vector<>();

    public EngineController(World world) {
        for (int i = 0; i < 256; i++) {
            keyState.add(false);
        }
        frame = new Engine();
        state = new EngineState(world);
        state.addObserver(frame.mapPanel);
        state.addObserver(frame);
        state.init();

        frame.addKeyListener(new KeyListener() {
            @Override
            public synchronized void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public synchronized void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() < 256)
                    keyState.set(keyEvent.getKeyCode(), true);
            }

            @Override
            public synchronized void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() < 256)
                    keyState.set(keyEvent.getKeyCode(), false);
            }
        });

        ThreadLauncher.execute(() -> {
            new Game(this);
            frame.dispose();
        });
    }
}