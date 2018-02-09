package core.input;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class InputManager {

    private KeyboardHandler keyCallback;

    public InputManager(long window) {
        this.keyCallback = new KeyboardHandler();
        glfwSetKeyCallback(window, this.keyCallback);
    }

    public void update() {
        keyCallback.update();
    }
}
