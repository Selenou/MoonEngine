package core.model;

import core.scene.Component;
import org.joml.Vector3f;

public class Material extends Component {

    private Texture diffusemap;
    private Vector3f color;

    public Material() {

    }

    public Vector3f getColor() {
        return this.color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Texture getDiffusemap() {
        return this.diffusemap;
    }

    public void setDiffusemap(Texture diffusemap) {
        this.diffusemap = diffusemap;
    }
}
