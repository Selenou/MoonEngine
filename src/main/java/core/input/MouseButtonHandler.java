package core.input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseButtonHandler extends GLFWMouseButtonCallback {

    private ArrayList<Integer> pushedButtons = new ArrayList<>();
    private ArrayList<Integer> heldButtons = new ArrayList<>();
    private ArrayList<Integer> releasedButtons = new ArrayList<>();

    @Override
    public void invoke(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS){
            if (!pushedButtons.contains(button)){
                pushedButtons.add(button);
                heldButtons.add(button);
                //System.out.println("pressed " + button);
            }
        }

        if (action == GLFW_RELEASE){
            releasedButtons.add(button);
            heldButtons.remove(new Integer(button)); // removes the first occurrence of the specified element from this list
            //System.out.println("released " + button);
        }
    }

    public void update() {
        pushedButtons.clear();
        releasedButtons.clear();
    }
}
