package core.kernel;

import core.buffer.VBO;
import core.input.Input;
import core.model.Mesh;
import core.model.Shader;
import core.model.Vertex;
import core.utils.ResourceLoader;
import org.joml.Math;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.opengl.GL11.*;

public class Game {

    private Mesh mesh;
    private VBO meshBuffer;
    private Shader shader;

    public void init() {

        ResourceLoader.loadImage("wall.jpg");

        Vertex[] vertices = new Vertex[]{
                new Vertex(new Vector3f(0.5f, 0.5f, 0)), //top right
                new Vertex(new Vector3f(0.5f, -0.5f, 0)), // bottom right
                new Vertex(new Vector3f(-0.5f, 0.5f, 0)), // top left
                new Vertex(new Vector3f(-0.5f, -0.5f, 0)) // bottom left
        };

        int[] indices = {
            0,1,3,
            2,0,3
        };

        this.mesh = new Mesh(vertices, indices);
        this.meshBuffer = new VBO();
        this.meshBuffer.allocate(this.mesh);

        this.shader = new Shader();
        this.shader.addVertexShader(ResourceLoader.loadShader("basicVertex.vs"));
        this.shader.addFragmentShader(ResourceLoader.loadShader("basicFragment.fs"));
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
        this.meshBuffer.draw();
    }
}
