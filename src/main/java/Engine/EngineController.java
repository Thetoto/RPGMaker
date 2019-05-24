package Engine;

import Model.World.Teleporter;
import Model.World.World;
import Tools.ThreadLauncher;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
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

    public void pauseGame() {
        System.out.println("Pause...");
        state.setPause(true);
        while (!keyState.get(KeyEvent.VK_P)) {
            try {
                Thread.sleep(10, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        state.setPause(false);
        System.out.println("Resume...");
        keyState.set(KeyEvent.VK_P, false);
    }
}