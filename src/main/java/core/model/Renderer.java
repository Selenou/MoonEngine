package core.model;

import core.buffer.VBO;
import core.scene.Component;
import core.shader.Shader;

public class Renderer extends Component {

    private VBO vbo;
    private Shader shader;

    public Renderer(VBO vbo, Shader shader) {
        this.vbo = vbo;
        this.shader = shader;
    }

    public void render(){
        this.shader.bind();
        this.shader.updateUniforms(this.getParent());
        this.vbo.draw();
    }

    public Shader getShader() {
        return this.shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public VBO getVbo() {
        return this.vbo;
    }

    public void setVbo(VBO vbo) {
        this.vbo = vbo;
    }
}
