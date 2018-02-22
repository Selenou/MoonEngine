package demo;

import engine.component.Renderer;
import engine.config.Config;
import engine.input.Input;
import engine.core.AbstractGame;
import engine.gfx.*;
import engine.light.Light;
import engine.scene.Camera;
import engine.scene.GameObject;
import engine.shader.PhongShader;
import engine.utils.AssimpModelLoader;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class DemoGame extends AbstractGame {

    public DemoGame() {
        super();

        /*
        // Audio stuff
        AudioEngine audioEngine = CoreEngine.getInstance().getAudioEngine();
        audioEngine.addSoundBuffer("zelda", new AudioBuffer("zelda.ogg"));
        audioEngine.addSoundSource("bgm", new AudioSource());
        audioEngine.getSoundSource("bgm").setBuffer(audioEngine.getSoundBuffer("zelda").getBufferId());
        audioEngine.getSoundSource("bgm").setLooping(true);
        audioEngine.getSoundSource("bgm").play();
        */


        // GFX stuff
        Model nanosuitModel = AssimpModelLoader.loadModel("nanosuit/", "nanosuit.obj");
        nanosuitModel.allocate();
        GameObject nanosuit = new GameObject();
        nanosuit.addComponent("renderer", new Renderer(nanosuitModel, PhongShader.getInstance()));
        nanosuit.getTransform().setScale(new Vector3f(0.2f));
        nanosuit.getTransform().setTranslation(new Vector3f(0,-1.5f,0));
        this.scene.getRootNode().addChild(nanosuit);
    }

    @Override
    public void input(Input input, float delta) {
        if(Config.DEBUG) {
            if(input.isKeyPressed(GLFW_KEY_F1))
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            else if(input.isKeyPressed(GLFW_KEY_F2))
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            else if(input.isKeyPressed(GLFW_KEY_F3))
                this.scene.getMainCamera().switchMode(Camera.CameraMode.PERSPECTIVE);
            else if(input.isKeyPressed(GLFW_KEY_F4))
                this.scene.getMainCamera().switchMode(Camera.CameraMode.ORTHOGRAPHIC);
        }

        super.input(input, delta);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render() {
        super.render();
    }
}
