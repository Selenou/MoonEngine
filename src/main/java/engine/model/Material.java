package engine.model;

import org.joml.Vector3f;

public class Material {

    private Texture diffuseMap;
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
        return this.diffuseMap;
    }

    public void setDiffusemap(Texture diffusemap) {
        this.diffuseMap = diffusemap;
    }
}
