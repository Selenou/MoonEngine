package core.scene;

import core.input.Input;
import org.joml.Vector3f;

public class Scene extends Node {

    private Camera mainCamera;
    private Node root;

    public Scene() {
        this.root = new Node();
        this.root.setParent(this);

        this.mainCamera = new Camera(new Vector3f(0,0,5), new Vector3f(0,0,-1), new Vector3f(0,1,0));
    }

    public void addNode(Node childNode) {
        this.root.addChild(childNode);
    }

    public void update() {
        this.root.update();
    }

    public void input(Input input, float delta) {
        this.mainCamera.input(input, delta);
        this.root.input(input, delta);
    }

    public void render() {
        this.root.render();
    }

    public Camera getMainCamera() {
        return this.mainCamera;
    }
}
