package core.kernel;

import static org.lwjgl.opengl.GL11.*;

public class RenderingEngine {

    private Window window;

    public RenderingEngine(Window window) {
        this.window = window;

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        System.out.println(this.getGLVersion());
    }

    public void clearScreen() {
        // clear the framebuffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render() {
        this.window.render();
    }

    public String getGLVersion() {
        return glGetString(GL_VERSION);
    }
}
