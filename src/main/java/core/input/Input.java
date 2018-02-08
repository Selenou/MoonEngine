package core.input;

import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;

public class Input {

    private KeyboardHandler keyCallback;

    public Input(long window) {
        this.keyCallback = new KeyboardHandler();
        glfwSetKeyCallback(window, this.keyCallback);
    }

    public void update() {
        keyCallback.update();
    }
}
