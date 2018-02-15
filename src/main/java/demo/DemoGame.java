package demo;

import core.buffer.VBO;
import core.input.Input;
import core.kernel.AbstractGame;
import core.model.Model;
import core.shader.DefaultShader;
import core.shader.Shader;
import core.utils.ResourceLoader;

public class DemoGame extends AbstractGame {

    private VBO vbo;
    private Shader shader;

    public DemoGame() {
        super();
    }

    @Override
    public void init() {
        Model model = ResourceLoader.loadModel("cube.obj");
        this.vbo = new VBO();
        this.vbo.allocate(model.getMesh().get(0));

        this.shader = DefaultShader.getInstance();
    }

    @Override
    public void input(Input input, float delta) {
        super.input(input, delta);
        this.scene.getMainCamera().input(input, delta);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render() {
        this.shader.bind();
        this.shader.setUniform("MVP", this.scene.getMainCamera().getViewProjectionMatrix().mul(this.scene.getTransform().getModelMatrix()));
        this.vbo.draw();
    }
}
