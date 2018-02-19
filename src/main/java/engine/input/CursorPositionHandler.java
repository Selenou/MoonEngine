package engine.input;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorPositionHandler extends GLFWCursorPosCallback {

    private Vector2f cursorPosition;

    public CursorPositionHandler() {
        this.cursorPosition = new Vector2f(0, 0);
    }

    @Override
    public void invoke(long window, double xpos, double ypos) {
        this.cursorPosition.set((float)xpos, (float)ypos);
    }
}
