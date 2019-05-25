package Engine;

import Model.Engine.Timer;
import Model.World.World;
import Tools.ThreadLauncher;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class EngineController {
    Engine frame;
    EngineState state;
    Vector<Boolean> keyState = new Vector<>();
    Model.Engine.Timer timeCycle;

    public EngineController(World world) {
        for (int i = 0; i < 256; i++) {
            keyState.add(false);
        }
        frame = new Engine();
        state = new EngineState(world);
        state.addObserver(frame.mapPanel);
        state.addObserver(frame);
        state.init();

        if (state.world.timeCycle.isActive()) {
            timeCycle = new Timer(state.world.timeCycle.getDelay() * 1000,
                                         e -> timeCycle.updateDelay(state.switchTime() * 1000));
            timeCycle.start();
            timeCycle.updateDelay(state.world.timeCycle.getNextDelay() * 1000);
        }

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


        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                keyState.set(KeyEvent.VK_ESCAPE, true);
            }
        });
    }

    public void pauseGame() {
        System.out.println("Pause...");
        state.setPause(true);

        if (timeCycle != null)
            timeCycle.pause();

        while (!keyState.get(KeyEvent.VK_P)) {
            try {
                Thread.sleep(10, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (timeCycle != null)
            timeCycle.resume();

        state.setPause(false);
        System.out.println("Resume...");
        keyState.set(KeyEvent.VK_P, false);
    }
}