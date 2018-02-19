package engine.model;

import engine.scene.Component;

public class Model extends Component {

    private Mesh mesh;
    private Material material;

    public Model(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    public Mesh getMesh() {
        return this.mesh;
    }

    public Material getMaterial() {
        return this.material;
    }
}
