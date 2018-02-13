package core.kernel;

import core.buffer.VBO;
import core.input.Input;
import core.model.*;
import core.utils.ResourceLoader;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;

public class Game {

    private Model model;
    private Mesh mesh;
    private VBO vbo;
    private Shader shader;
    //private Texture texture;

    public void init() {

        this.model = ResourceLoader.loadModel("cube-obj/cube.obj");
        this.mesh = model.getMesh().get(0);
        this.vbo = new VBO();
        this.vbo.allocate(this.mesh);

        //this.texture = ResourceLoader.loadTexture("wall.jpg");

        this.shader = new Shader();
        this.shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        this.shader.addFragmentShader(ResourceLoader.loadShader("basicColorFragment.fs"));
        this.shader.compileShader();
        this.shader.addUniform("customColor");
    }

    public void input(Input input) {
        if(input.isKeyHeld(GLFW_KEY_SPACE)){
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }
        else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }
    }

    float tmp = 0.0f;

    public void update() {
        tmp += 0.01;
        this.shader.setUniformf("customColor", (float)Math.abs(Math.sin(tmp)));
    }

    public void render() {
        this.shader.bind();
        //this.texture.bind();
        this.vbo.draw();
    }
}
