package demo;

import engine.audio.AudioSource;

import engine.component.Renderer;
import engine.config.Config;
import engine.core.CoreEngine;
import engine.input.Input;
import engine.core.AbstractGame;
import engine.model.*;
import engine.scene.Camera;
import engine.scene.GameObject;
import engine.shader.DefaultShader;
import engine.utils.AssimpModelLoader;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class DemoGame extends AbstractGame {

    public DemoGame() {
        super();

        // preload sounds
        CoreEngine.getInstance().getAudioEngine().loadSound("zelda.ogg");

        // GFX stuff
        Model model = AssimpModelLoader.loadModel("nanosuit/", "nanosuit.obj");
        model.allocate();
        GameObject nanosuit = new GameObject();
        nanosuit.addComponent("renderer", new Renderer(model, DefaultShader.getInstance()));
        nanosuit.getTransform().setScale(new Vector3f(0.2f));
        nanosuit.getTransform().setTranslation(new Vector3f(0,-1.5f,0));
        this.scene.getRootNode().addChild(nanosuit);

        // bgm
        AudioSource bgmSource = new AudioSource();
        bgmSource.play(CoreEngine.getInstance().getAudioEngine().getSoundBuffer("zelda.ogg"));
        bgmSource.setLooping(true);
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
