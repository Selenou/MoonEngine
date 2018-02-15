package demo;

import core.buffer.VBO;
import core.input.Input;
import core.kernel.Camera;
import core.kernel.Game;
import core.kernel.Transform;
import core.model.Model;
import core.model.Shader;
import core.utils.ResourceLoader;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;

public class DemoGame implements Game {

    private VBO vbo;
    private Shader shader;
    private Transform transform;
    private Camera camera;

    @Override
    public void init() {

        Model model = ResourceLoader.loadModel("cube.obj");
        this.vbo = new VBO();
        this.vbo.allocate(model.getMesh().get(0));

        this.transform = new Transform();
        this.transform.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));

        this.camera = new Camera(new Vector3f(0,0,5), new Vector3f(0,0,-1), new Vector3f(0,1,0));

        this.shader = new Shader();
        this.shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        this.shader.addFragmentShader(ResourceLoader.loadShader("basicColorFragment.fs"));
        this.shader.compileShader();
        this.shader.addUniform("MVP");
    }

    @Override
    public void input(Input input) {
        if(input.isKeyHeld(GLFW_KEY_SPACE)){
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }
        else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }
    }

    @Override
    public void update() {
        this.camera.update();
    }

    @Override
    public void render() {
        this.shader.bind();
        this.shader.setUniform("MVP", this.camera.getViewProjectionMatrix().mul(this.transform.getModelMatrix()));
        this.vbo.draw();
    }
}
