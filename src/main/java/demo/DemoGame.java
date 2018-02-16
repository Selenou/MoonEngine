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
import core.utils.ResourceLoader;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class DemoGame extends AbstractGame {

    public DemoGame() {
        super();

        Model model = ResourceLoader.loadModel("cube.obj");
        VBO vbo = new VBO();
        vbo.allocate(model.getMesh().get(0));
        Renderer renderer = new Renderer(vbo, DefaultShader.getInstance());
        GameObject cube = new GameObject();
        cube.addComponent("model", model);
        cube.addComponent("renderer", renderer);
        this.scene.getRootNode().addChild(cube);

        Model model2 = ResourceLoader.loadModel("cube.obj");
        VBO vbo2 = new VBO();
        vbo2.allocate(model2.getMesh().get(0));
        Renderer renderer2 = new Renderer(vbo2, DefaultShader.getInstance());
        GameObject cube2 = new GameObject();
        cube2.addComponent("model", model2);
        cube2.addComponent("renderer", renderer2);
        cube.addChild(cube2);
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
