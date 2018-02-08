package core.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler extends GLFWKeyCallback {

    private ArrayList<Integer> pushedKeys = new ArrayList<>();
    private ArrayList<Integer> heldKeys = new ArrayList<>();
    private ArrayList<Integer> releasedKeys = new ArrayList<>();

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS){
            if (!pushedKeys.contains(key)){
                pushedKeys.add(key);
                heldKeys.add(key);

                System.out.println("pressed " + key);
            }
        }

        if (action == GLFW_RELEASE){
            heldKeys.remove(new Integer(key));
            releasedKeys.add(key);

            System.out.println("released " + key);
        }
    }

    public void update() {
        pushedKeys.clear();
        releasedKeys.clear();

        // Poll for window events. The key callback above will only be invoked during this call.
        glfwPollEvents();
    }
}
