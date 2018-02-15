package core.scene;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {

    private Vector3f translation;
    private Quaternionf rotation;
    private Vector3f scale;

    public Transform() {
        this.translation = new Vector3f(0, 0, 0);
        this.rotation = new Quaternionf(0, 0, 0, 1);
        this.scale = new Vector3f(1, 1, 1);
    }

    public Matrix4f getModelMatrix() {
        return new Matrix4f().translate(this.translation).rotate(this.rotation).scale(this.scale); // Remember : inverted !
    }

    public Vector3f getTranslation() {
        return this.translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public Quaternionf getRotation() {
        return this.rotation;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return this.scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
