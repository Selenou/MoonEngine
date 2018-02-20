package demo;

import engine.audio.AudioSource;
import engine.buffer.VBO;
import engine.config.Config;
import engine.core.CoreEngine;
import engine.input.Input;
import engine.core.AbstractGame;
import engine.model.Model;
import engine.model.Renderer;
import engine.scene.Camera;
import engine.scene.GameObject;
import engine.shader.DefaultShader;
import engine.utils.AssimpModelLoader;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class DemoGame extends AbstractGame {

    public DemoGame() {
        super();

        // preload sounds
        CoreEngine.getInstance().getAudioEngine().loadSound("zelda.ogg");


        // GFX stuff
        ArrayList<Model> modelList = AssimpModelLoader.loadModel("nanosuit/", "nanosuit.obj");
        GameObject nanosuit = new GameObject();

        for(Model model : modelList){
            VBO vbo = new VBO();
            vbo.allocate(model.getMesh());
            Renderer renderer = new Renderer(vbo, DefaultShader.getInstance());

            GameObject subObj = new GameObject();
            subObj.addComponent("model", model);
            subObj.addComponent("renderer", renderer);

            nanosuit.addChild(subObj);
        }

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
