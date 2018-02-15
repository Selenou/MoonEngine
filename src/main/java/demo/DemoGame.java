package demo;

import core.buffer.VBO;
import core.config.Config;
import core.input.Input;
import core.kernel.AbstractGame;
import core.model.Model;
import core.shader.DefaultShader;
import core.shader.Shader;
import core.utils.ResourceLoader;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;

public class DemoGame extends AbstractGame {

    private VBO vbo;
    private Shader shader;

    public DemoGame() {
        super();

        Model model = ResourceLoader.loadModel("cube.obj");
        this.vbo = new VBO();
        this.vbo.allocate(model.getMesh().get(0));

        this.shader = DefaultShader.getInstance();
    }

    @Override
    public void input(Input input, float delta) {

        if(Config.DEBUG) {
            if(input.isKeyHeld(GLFW_KEY_SPACE))
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            else
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

        super.input(input, delta);
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

        super.render();
    }
}
