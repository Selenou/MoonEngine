package core.kernel;

import core.config.Config;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {

    private Vector3f translation;
    private Quaternionf rotation;
    private Vector3f scale;

    public Transform() {
        this.translation = new Vector3f();
        this.rotation = new Quaternionf();
        this.scale = new Vector3f(1.0f, 1.0f, 1.0f);
    }

    public Matrix4f getTransformation() {
        return new Matrix4f().translate(this.translation).rotate(this.rotation).scale(this.scale);
    }

    public Matrix4f getProjectedTransformation() {
        Matrix4f transformationMatrix = this.getTransformation();
        Matrix4f projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(45.0f), Config.WINDOW_WIDTH * 1.0f /Config.WINDOW_HEIGHT, 0.01f, 100.0f);
        return projectionMatrix.mul(transformationMatrix);
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
