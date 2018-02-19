package engine.scene;

import engine.input.Input;
import org.joml.Vector3f;

public class Scene extends Node {

    private Node rootNode;
    private Camera mainCamera;

    public Scene() {
        this.rootNode = new Node();
        this.rootNode.setParent(this);
        this.mainCamera = new Camera(new Vector3f(0,0,5), new Vector3f(0,0,-1), new Vector3f(0,1,0), Camera.CameraMode.PERSPECTIVE);
    }

    public void update() {
        this.rootNode.update();
    }

    public void input(Input input, float delta) {
        this.mainCamera.input(input, delta);
        this.rootNode.input(input, delta);
    }

    public void render() {
        this.rootNode.render();
    }

    public Camera getMainCamera() {
        return this.mainCamera;
    }

    public Node getRootNode() {
        return this.rootNode;
    }
}
