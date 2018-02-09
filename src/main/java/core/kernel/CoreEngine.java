package core.kernel;

import core.input.Input;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

import static org.lwjgl.glfw.GLFW.*;

public class CoreEngine {

    private Window window;
    private Input input;
    private boolean isRunning;

    private int frameRate = 60;
    private float frameTime = 1.0f / this.frameRate;

    public CoreEngine(int width, int height) {
        this.initGLFW();

        this.window = new Window();
        this.window.create(width, height);

        this.input = new Input(this.window.getWindow());

        this.isRunning = false;

        this.getGLProperties();
    }

    private void initGLFW() {
        // Setup an error callback. The default implementation will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
    }

    public void start() {
        if(this.isRunning)
            return;

        this.run();
    }

    private void stop() {
        if(!this.isRunning)
            return;

        this.isRunning = false;
    }

    private void run() {
        this.isRunning = true;

        int frames = 0;
        long frameCounter = 0;

        long lastTime = Time.getTime();
        double unprocessedTime = 0;

        // Rendering Loop
        while(this.isRunning) {
            boolean render = false;

            long startTime = Time.getTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) Time.SECOND;
            frameCounter += passedTime;

            while(unprocessedTime > frameTime) {
                render = true;

                unprocessedTime -= frameTime;

                if(this.window.isCloseRequested()){
                    this.stop();
                }

                this.update();

                if(frameCounter >= Time.SECOND) {
                    System.out.println("FPS : " + frames);
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if(render) {
                this.render();
                frames++;
            }
            else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        this.cleanUp();
    }

    private void update() {
        this.input.update();
    }

    private void render() {
        this.window.render();
    }

    private void cleanUp() {
        this.window.dispose();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void getGLProperties(){
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION) + " bytes");
        System.out.println("Max Geometry Uniform Blocks: " + GL31.GL_MAX_GEOMETRY_UNIFORM_BLOCKS+ " bytes");
        System.out.println("Max Geometry Shader Invocations: " + GL40.GL_MAX_GEOMETRY_SHADER_INVOCATIONS + " bytes");
        System.out.println("Max Uniform Buffer Bindings: " + GL31.GL_MAX_UNIFORM_BUFFER_BINDINGS + " bytes");
        System.out.println("Max Uniform Block Size: " + GL31.GL_MAX_UNIFORM_BLOCK_SIZE + " bytes");
        System.out.println("Max SSBO Block Size: " + GL43.GL_MAX_SHADER_STORAGE_BLOCK_SIZE + " bytes");
    }
}