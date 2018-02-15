package core.kernel;

import core.config.Config;
import core.input.Input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;

public abstract class AbstractGame {

    protected Camera mainCamera;

    public void init() {

    }

    public void input(Input input, float delta) {
        if(Config.DEBUG) {
            if(input.isKeyHeld(GLFW_KEY_SPACE))
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            else
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

        this.mainCamera.input(input, delta);
    }

    public void update() {
        this.mainCamera.update();
    }

    public void render() {

    }

    public Camera getMainCamera() {
        return this.mainCamera;
    }
}