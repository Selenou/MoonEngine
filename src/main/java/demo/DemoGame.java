package demo;

import core.buffer.VBO;
import core.input.Input;
import core.kernel.AbstractGame;
import core.kernel.Camera;
import core.kernel.Transform;
import core.model.Model;
import core.shader.DefaultShader;
import core.shader.Shader;
import core.utils.ResourceLoader;
import org.joml.Vector3f;

public class DemoGame extends AbstractGame {

    private VBO vbo;
    private Shader shader;
    private Transform transform;

    @Override
    public void init() {
        this.mainCamera = new Camera(new Vector3f(0,0,5), new Vector3f(0,0,-1), new Vector3f(0,1,0));

        Model model = ResourceLoader.loadModel("cube.obj");
        this.vbo = new VBO();
        this.vbo.allocate(model.getMesh().get(0));

        //todo inside node
        this.transform = new Transform();

        this.shader = DefaultShader.getInstance();
    }

    @Override
    public void input(Input input, float delta) {
        super.input(input, delta);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render() {
        this.shader.bind();
        this.shader.setUniform("MVP", this.mainCamera.getViewProjectionMatrix().mul(this.transform.getModelMatrix()));
        this.vbo.draw();
    }
}
