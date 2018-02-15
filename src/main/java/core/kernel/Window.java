package core.kernel;

import core.config.Config;
import core.utils.ResourceLoader;
import org.lwjgl.glfw.GLFWImage;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private long window;
    private int width, height;

    public void create(int width, int height, String title) {
        this.width = width;
        this.height = height;

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 5);

        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // for MacOS
        //glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        //glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
        //glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        this.window = glfwCreateWindow(width, height, title, NULL, NULL);

        if (this.window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(this.window);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        createCapabilities();

        // Vsync
        if(Config.VSYNC_ENABLED) {
            glfwSwapInterval(1);
        }

        this.setWindowIcon("logo.png");

        // callback for Framebuffer resizing
        glfwSetFramebufferSizeCallback(this.window, (window, widthCb, heightCb) -> this.updateWindowDimension(widthCb, heightCb));

        // Make the window visible
        glfwShowWindow(this.window);
    }

    public void render() {
        glfwSwapBuffers(this.window); // swap the color buffers
    }

    public void updateWindowDimension(int width, int height) {
        this.width = width;
        this.height = height;
        glViewport(0, 0, this.width, this.height);
        CoreEngine.getInstance().getGame().getScene().getMainCamera().updateProjectionMatrix(this.width, this.height);

    }

    public void dispose() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(this.window);
        glfwDestroyWindow(this.window);
    }

    private void setWindowIcon(String fileName) {
        ByteBuffer imageBuffer = ResourceLoader.loadImage(fileName);
        GLFWImage image = GLFWImage.malloc();
        image.set(48, 48, imageBuffer);
        GLFWImage.Buffer images = GLFWImage.malloc(1);
        images.put(0, image);
        glfwSetWindowIcon(this.window, images);
        images.free();
        image.free();
    }

    public boolean isCloseRequested() {
        return glfwWindowShouldClose(this.window);
    }

    public long getWindow() {
        return this.window;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}

