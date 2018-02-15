package core.input;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    private KeyboardHandler keyboardHandler;
    private MouseButtonHandler mouseButtonHandler;
    private CursorPositionHandler cursorPositionHandler;

    public Input(long window) {
        this.keyboardHandler = new KeyboardHandler();
        this.mouseButtonHandler = new MouseButtonHandler();
        this.cursorPositionHandler = new CursorPositionHandler();

        glfwSetKeyCallback(window, this.keyboardHandler);
        glfwSetMouseButtonCallback(window, this.mouseButtonHandler);
        glfwSetCursorPosCallback(window, this.cursorPositionHandler);
    }

    public void update() {
        this.keyboardHandler.update();
        this.mouseButtonHandler.update();

        // Poll for window events. The key callback above will only be invoked during this call.
        glfwPollEvents();
    }

    public boolean isKeyPressed(int key) {
        return keyboardHandler.isKeyPressed(key);
    }

    public boolean isKeyHeld(int key) {
        return keyboardHandler.isKeyHeld(key);
    }
}
