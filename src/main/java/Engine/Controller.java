package Engine;

public class Controller {
    Engine frame;
    State state;

    public Controller() {
        frame = new Engine();
        state = new State();
    }
}
