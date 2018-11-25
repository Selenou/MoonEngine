package engine.scene;

import engine.config.Config;
import engine.input.Input;
import engine.core.CoreEngine;
import engine.core.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    public enum CameraMode {
        PERSPECTIVE,
        ORTHOGRAPHIC
    }

    private Vector3f position;

    private Vector3f forward;
    private Vector3f up;
    private Vector3f right;

    private CameraMode mode;

    private Matrix4f viewProjectionMatrix;
    private Matrix4f projectionMatrix;

    public Camera(Vector3f position, Vector3f forward, Vector3f up, CameraMode mode) {
        this.position = position;
        this.forward = forward.normalize();
        this.up = up.normalize();
        this.right = new Vector3f(this.forward).cross(this.up).normalize();
        this.mode = mode;

        this.viewProjectionMatrix = new Matrix4f();
        this.projectionMatrix = this.createProjectionMatrix();
    }

    public void input(Input input, float delta) {

        float amount = 10 * delta;

        //todo make a component for this
        if(input.isKeyHeld(GLFW_KEY_W))
            this.move(this.forward, amount);
        if(input.isKeyHeld(GLFW_KEY_S))
            this.move(this.forward, -amount);
        if(input.isKeyHeld(GLFW_KEY_A))
            this.move(this.right, -amount);
        if(input.isKeyHeld(GLFW_KEY_D))
            this.move(this.right, amount);
        if(input.isKeyHeld(GLFW_KEY_Q))
            this.move(this.up, amount);
        if(input.isKeyHeld(GLFW_KEY_E))
            this.move(this.up, -amount);

        //todo rotate
    }

    public void update() {
        this.updateViewProjectionMatrix();
    }

    private void move(Vector3f direction, float amount) {
        this.position.add(new Vector3f(direction).mul(amount));
    }

    private void updateViewProjectionMatrix() {
        if(this.mode == CameraMode.PERSPECTIVE)
            this.projectionMatrix.mulPerspectiveAffine(this.updateViewMatrix(), this.viewProjectionMatrix);
        else
            this.projectionMatrix.mulOrthoAffine(this.updateViewMatrix(), this.viewProjectionMatrix);
    }

    private Matrix4f updateViewMatrix() {
        Vector3f target = new Vector3f(this.position).add(this.forward);
        return new Matrix4f().lookAt(this.position, target, this.up);
    }

    private Matrix4f createProjectionMatrix() {
        Window window = CoreEngine.getInstance().getWindow();
        float aspectRatio = window.getWidth() * 1.0f / window.getHeight();

        if(this.mode == CameraMode.PERSPECTIVE)
            return new Matrix4f().setPerspective((float) Math.toRadians(Config.FOV), aspectRatio, Config.Z_NEAR, Config.Z_FAR);
        else
            return new Matrix4f().setOrtho(-aspectRatio, aspectRatio, -1.0f, 1.0f, Config.Z_NEAR, Config.Z_FAR);
    }

    public void updateProjectionMatrix(int width, int height) {
        float aspectRatio = width * 1.0f / height;

        if(this.mode == CameraMode.PERSPECTIVE)
            this.projectionMatrix = new Matrix4f().setPerspective((float) Math.toRadians(Config.FOV), aspectRatio, Config.Z_NEAR, Config.Z_FAR);
        else
            this.projectionMatrix = new Matrix4f().setOrtho(-aspectRatio, aspectRatio, -1.0f, 1.0f, Config.Z_NEAR, Config.Z_FAR);
    }

    public Matrix4f getViewProjectionMatrix() {
        return new Matrix4f(viewProjectionMatrix);
    }

    public void switchMode(CameraMode mode) {
        if(this.mode != mode) {
            this.mode = mode;
            this.projectionMatrix = this.createProjectionMatrix();
        }
    }

    public Vector3f getPosition() {
        return this.position;
    }
}
