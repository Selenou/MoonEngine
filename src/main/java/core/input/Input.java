package core.input;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private KeyboardHandler keyCallback;
    private MouseButtonHandler mouseButtonCallback;

    public Input(long window) {
        this.keyCallback = new KeyboardHandler();
        this.mouseButtonCallback = new MouseButtonHandler();

        glfwSetKeyCallback(window, this.keyCallback);
        glfwSetMouseButtonCallback(window, this.mouseButtonCallback);
    }

    public void update() {
        this.keyCallback.update();
        this.mouseButtonCallback.update();

        // Poll for window events. The key callback above will only be invoked during this call.
        glfwPollEvents();
    }
}
