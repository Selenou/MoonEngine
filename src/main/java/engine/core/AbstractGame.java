package engine.core;

import engine.input.Input;
import engine.scene.Scene;

public abstract class AbstractGame {

    protected Scene scene;

    public AbstractGame() {
        this.scene = new Scene();
    }

    public void input(Input input, float delta) {
        this.scene.input(input, delta);
    }

    public void update() {
        this.scene.update();
        this.scene.getMainCamera().update();
    }

    public void render() {
        this.scene.render();
    }

    public Scene getScene() {
        return this.scene;
    }

    public void cleanup() {
        this.scene.cleanup();
    }
}