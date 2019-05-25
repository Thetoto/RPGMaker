package Engine;

import Model.World.World;
import Tools.ThreadLauncher;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class EngineController {
    Engine frame;
    EngineState state;
    Vector<Boolean> keyState = new Vector<>();
    Timer timeCycle;

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
                                         e -> timeCycle.setDelay(state.switchTime() * 1000));
            timeCycle.start();
            timeCycle.setDelay(state.world.timeCycle.getNextDelay() * 1000);
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