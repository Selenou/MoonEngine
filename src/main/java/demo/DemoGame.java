package demo;

import core.buffer.VBO;
import core.input.Input;
import core.kernel.Game;
import core.kernel.Transform;
import core.model.Mesh;
import core.model.Model;
import core.model.Shader;
import core.utils.ResourceLoader;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;

public class DemoGame implements Game {

    private Model model;
    private VBO vbo;
    private Shader shader;
    private Transform transform;
    //private Texture texture;

    @Override
    public void init() {

        this.model = ResourceLoader.loadModel("cube.obj");
        Mesh mesh = model.getMesh().get(0);
        this.vbo = new VBO();
        this.vbo.allocate(mesh);

        this.transform = new Transform();

        //this.texture = ResourceLoader.loadTexture("wall.jpg");

        this.shader = new Shader();
        this.shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        this.shader.addFragmentShader(ResourceLoader.loadShader("basicColorFragment.fs"));
        this.shader.compileShader();
        //this.shader.addUniform("customColor");
        this.shader.addUniform("transform");
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

    float tmp = 0.0f;

    @Override
    public void update() {
        tmp += 0.01;
        //this.shader.setUniformf("customColor", (float)Math.abs(Math.sin(tmp)));
        this.transform.setTranslation(new Vector3f((float)(Math.sin(tmp)), 0.0f, -3));
        this.transform.setRotation(new Quaternionf(new AxisAngle4f((float)(Math.sin(tmp) * Math.PI), new Vector3f(0, 1, 0))));
    }

    @Override
    public void render() {
        this.shader.bind();
        this.shader.setUniform("transform", this.transform.getProjectedTransformation());
        //this.texture.bind();
        this.vbo.draw();
    }
}
