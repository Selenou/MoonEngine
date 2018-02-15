package core.kernel;

import core.config.Config;
import core.input.Input;
import core.scene.Scene;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;

public abstract class AbstractGame {

    protected Scene scene;

    public AbstractGame() {
        this.scene = new Scene();
    }

    public void init() {

    }

    public void input(Input input, float delta) {
        if(Config.DEBUG) {
            if(input.isKeyHeld(GLFW_KEY_SPACE))
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            else
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }
    }

    public void update() {
        this.scene.getMainCamera().update();
    }

    public void render() {

    }

    public Scene getScene() {
        return this.scene;
    }
}