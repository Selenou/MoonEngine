package core.kernel;

import core.input.Input;

public interface Game {

    void init();
    void input(Input input);
    void update();
    void render();
}