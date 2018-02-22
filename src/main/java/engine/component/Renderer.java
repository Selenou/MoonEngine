package engine.component;

import engine.gfx.Mesh;
import engine.gfx.Model;
import engine.shader.Shader;

public class Renderer extends Component {

    private Model model;
    private Shader shader;

    public Renderer(Model model, Shader shader) {
        this.model = model;
        this.shader = shader;
    }

    public void render(){
        this.shader.bind();
        for(Mesh mesh : this.model.getMeshes()) {
            this.shader.updateUniforms(this.getParent(), this.model, mesh.getMaterialIndex());
            mesh.draw();
        }
    }

    public void cleanup() {
        super.cleanup();
        this.model.cleanup();
        this.shader.cleanup();
    }
}
