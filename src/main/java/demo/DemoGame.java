package demo;

import core.buffer.VBO;
import core.input.Input;
import core.kernel.Camera;
import core.kernel.AbstractGame;
import core.kernel.Transform;
import core.model.Model;
import core.model.Shader;
import core.utils.ResourceLoader;
import org.joml.Vector3f;

public class DemoGame extends AbstractGame {

    private VBO vbo;
    private Shader shader;
    private Transform transform;

    @Override
    public void init() {

        Model model = ResourceLoader.loadModel("cube.obj");
        this.vbo = new VBO();
        this.vbo.allocate(model.getMesh().get(0));

        this.transform = new Transform();
        this.transform.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));

        this.mainCamera = new Camera(new Vector3f(0,0,5), new Vector3f(0,0,-1), new Vector3f(0,1,0));

        this.shader = new Shader();
        this.shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        this.shader.addFragmentShader(ResourceLoader.loadShader("basicColorFragment.fs"));
        this.shader.compileShader();
        this.shader.addUniform("MVP");
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
