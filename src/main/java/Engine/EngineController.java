package Engine;

import Model.World.World;

public class EngineController {
    Engine frame;
    EngineState state;

    public EngineController(World world) {
        frame = new Engine();
        state = new EngineState(world);
        state.addObserver(frame.mapPanel);
        state.init();
    }
}
