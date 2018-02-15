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

        Model model = ResourceLoader.loadModel("sphere.obj");
        VBO vbo = new VBO();
        vbo.allocate(model.getMesh().get(0)); //todo gerer multi mesh
        Renderer renderer = new Renderer(vbo, DefaultShader.getInstance());
        GameObject cube = new GameObject();
        cube.getTransform().setScale(new Vector3f(1)); //todo a fix
        cube.addComponent("model", model);
        cube.addComponent("renderer", renderer);
        this.scene.getRootNode().addChild(cube);


        Model model2 = ResourceLoader.loadModel("sphere.obj");
        VBO vbo2 = new VBO();
        vbo2.allocate(model2.getMesh().get(0));
        Renderer renderer2 = new Renderer(vbo2, DefaultShader.getInstance());
        GameObject cube2 = new GameObject();
        cube2.getTransform().setScale(new Vector3f(2f));
        cube2.addComponent("model", model2);
        cube2.addComponent("renderer", renderer2);
        this.scene.getRootNode().addChild(cube2);

        Model model3 = ResourceLoader.loadModel("sphere.obj");
        VBO vbo3 = new VBO();
        vbo3.allocate(model3.getMesh().get(0));
        Renderer renderer3 = new Renderer(vbo3, DefaultShader.getInstance());
        GameObject cube3 = new GameObject();
        cube3.getTransform().setScale(new Vector3f(3));
        cube3.addComponent("model", model3);
        cube3.addComponent("renderer", renderer3);
        this.scene.getRootNode().addChild(cube3);
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
