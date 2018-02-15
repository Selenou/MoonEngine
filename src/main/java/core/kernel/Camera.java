package core.kernel;

import core.config.Config;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    public enum CameraMode {
        PERSPECTIVE,
        ORTHOGRAPHIC
    }

    private Vector3f position;
    private Vector3f forward;
    private Vector3f up;
    private CameraMode mode;

    private Matrix4f viewProjectionMatrix = new Matrix4f();
    private Matrix4f projectionMatrix = new Matrix4f();

    public Camera(Vector3f position, Vector3f forward, Vector3f up, CameraMode mode) {
        this.position = position;
        this.forward = forward.normalize();
        this.up = up.normalize();
        this.mode = mode;

        this.viewProjectionMatrix = new Matrix4f();
        this.projectionMatrix = this.getProjectionMatrix();
    }

    public Camera(Vector3f position, Vector3f forward, Vector3f up) {
        this(position, forward, up, CameraMode.PERSPECTIVE);
    }

    public void update() {
        this.updateViewProjectionMatrix();
    }

    private void updateViewProjectionMatrix() {
        if(this.mode == CameraMode.PERSPECTIVE)
            this.projectionMatrix.mulPerspectiveAffine(this.getViewMatrix(), this.viewProjectionMatrix);
        else
            this.projectionMatrix.mulOrthoAffine(this.getViewMatrix(), this.viewProjectionMatrix);
    }

    private Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(this.position, this.forward, this.up);
    }

    private Matrix4f getProjectionMatrix() {
        float aspectRatio = Config.WINDOW_WIDTH * 1.0f /Config.WINDOW_HEIGHT;

        if(this.mode == CameraMode.PERSPECTIVE)
            return new Matrix4f().setPerspective((float) Math.toRadians(Config.FOV), aspectRatio, Config.Z_NEAR, Config.Z_FAR);
        else
            return new Matrix4f().setOrtho(-aspectRatio, aspectRatio, -1.0f, 1.0f, Config.Z_NEAR, Config.Z_FAR);
    }

    public Matrix4f getViewProjectionMatrix() {
        return this.viewProjectionMatrix;
    }
}
