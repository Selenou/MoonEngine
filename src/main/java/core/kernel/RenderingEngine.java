package core.kernel;

import core.config.Config;

import static org.lwjgl.opengl.GL11.*;

public class RenderingEngine {

    private Window window;

    public RenderingEngine(Window window) {
        this.window = window;
        this.init();
    }

    private void init () {
        System.out.println(this.getGLVersion());

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Z buffer
        glEnable(GL_DEPTH_TEST);

        if(Config.CULL_FACE_ENABLED) {
            this.enableFaceCulling();
        }
    }

    private void clearScreen() {
        // clear the framebuffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(AbstractGame game) {
        this.clearScreen();
        game.render();
        this.window.render();
    }

    private void enableFaceCulling() {
        // Face culling : determines whether a polygon of a graphical object is visible
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glFrontFace(GL_CCW);
    }

    private String getGLVersion() {
        return glGetString(GL_VERSION);
    }
}
