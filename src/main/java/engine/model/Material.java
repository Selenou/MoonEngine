package engine.model;

import org.joml.Vector3f;

public class Material {

    private Texture diffuseMap;
    private Vector3f diffuseColor;
    private Vector3f ambientColor;
    private Vector3f specularColor;

    public Material() {
        this.diffuseColor = new Vector3f();
        this.ambientColor = new Vector3f();
        this.specularColor = new Vector3f();
    }

    public Vector3f getDiffuseColor() {
        return this.diffuseColor;
    }

    public Vector3f getAmbientColor() {
        return this.ambientColor;
    }

    public Vector3f getSpecularColor() {
        return this.specularColor;
    }

    public Texture getDiffusemap() {
        return this.diffuseMap;
    }

    public void setDiffuseColor(Vector3f color) {
        this.diffuseColor = color;
    }

    public void setAmbientColor(Vector3f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public void setSpecularColor(Vector3f specularColor) {
        this.specularColor = specularColor;
    }

    public void setDiffusemap(Texture diffusemap) {
        this.diffuseMap = diffusemap;
    }
}
