package engine.core;

import engine.audio.AudioEngine;
import engine.audio.AudioSource;
import engine.config.Config;
import engine.input.Input;
import engine.utils.TextureManager;
import engine.utils.Utils;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

public class CoreEngine {

    private static CoreEngine instance = null;

    private AbstractGame game;
    private Window window;
    private Input input;
    private RenderingEngine renderingEngine;
    private AudioEngine audioEngine;
    private boolean isRunning;

    private int frameRate = Config.FPS;
    private float frameTime = 1.0f / this.frameRate;

    public static CoreEngine getInstance(){
        if(instance == null){
            instance = new CoreEngine();
        }
        return instance;
    }

    private CoreEngine() {
        this.initGLFW();

        this.window = new Window();
        this.window.create(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, Config.WINDOW_TITLE);

        this.input = new Input(this.window.getWindow());

        this.renderingEngine = new RenderingEngine(this.window);
        this.audioEngine = new AudioEngine();

        this.isRunning = false;
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

                this.update(frameTime);

                if(frameCounter >= Time.SECOND) {
                    //System.out.println("FPS : " + frames);
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

    private void update(float deltaTime) {
        this.input.update();
        this.game.input(this.input, deltaTime);
        this.game.update();
    }

    private void render() {
        this.renderingEngine.render(this.game);
    }

    private void cleanUp() {
        this.window.dispose();
        this.audioEngine.cleanUp();

        TextureManager.getInstance().cleanUp();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void setGame(AbstractGame game) {
        this.game = game;
    }

    public AbstractGame getGame() {
        return this.game;
    }

    public Window getWindow() {
        return this.window;
    }

    public AudioEngine getAudioEngine() {
        return this.audioEngine;
    }
}