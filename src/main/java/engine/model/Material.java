package engine.model;

import org.joml.Vector3f;

public class Material {

    private Texture diffuseMap;
    private Vector3f diffuseColor;

    public Material() {

    }

    public Vector3f getDiffuseColor() {
        return this.diffuseColor;
    }

    public void setDiffuseColor(Vector3f color) {
        this.diffuseColor = color;
    }

    public Texture getDiffusemap() {
        return this.diffuseMap;
    }

    public void setDiffusemap(Texture diffusemap) {
        this.diffuseMap = diffusemap;
    }
}
