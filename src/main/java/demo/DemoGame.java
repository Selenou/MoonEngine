package demo;

import core.buffer.VBO;
import core.config.Config;
import core.input.Input;
import core.kernel.AbstractGame;
import core.model.Model;
import core.model.Renderer;
import core.scene.Camera;
import core.scene.GameObject;
import core.shader.DefaultShader;
import core.utils.AssimpModelLoader;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class DemoGame extends AbstractGame {

    public DemoGame() {
        super();

        ArrayList<Model> modelList = AssimpModelLoader.loadModel("mage/", "mage.FBX");

        GameObject cube = new GameObject();

        for(Model model : modelList){
            GameObject cubeChild = new GameObject();
            VBO vbo = new VBO();
            vbo.allocate(model.getMesh());
            Renderer renderer = new Renderer(vbo, DefaultShader.getInstance());
            cubeChild.addComponent("model", model);
            cubeChild.addComponent("renderer", renderer);
            cubeChild.addComponent("material", model.getMaterial());
            //cubeChild.getTransform().setRotation(new Quaternionf(new AxisAngle4f((float)Math.toRadians(-90),1,0,0)));
            cubeChild.getTransform().setScale(new Vector3f(0.2f));
            cubeChild.getTransform().setTranslation(new Vector3f(0,-1.5f,0));
            cube.addChild(cubeChild);
        }

        this.scene.getRootNode().addChild(cube);

        GameObject cube2 = new GameObject();

        for(Model model : modelList){
            GameObject cubeChild = new GameObject();
            VBO vbo = new VBO();
            vbo.allocate(model.getMesh());
            Renderer renderer = new Renderer(vbo, DefaultShader.getInstance());
            cubeChild.addComponent("model", model);
            cubeChild.addComponent("renderer", renderer);
            cubeChild.addComponent("material", model.getMaterial());
            //cubeChild.getTransform().setRotation(new Quaternionf(new AxisAngle4f((float)Math.toRadians(-90),1,0,0)));
            cubeChild.getTransform().setScale(new Vector3f(0.3f));
            cubeChild.getTransform().setTranslation(new Vector3f(0,-1.5f,0));
            cube2.addChild(cubeChild);
        }

        this.scene.getRootNode().addChild(cube2);
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
