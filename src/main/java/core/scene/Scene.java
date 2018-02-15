package core.scene;

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

    public Camera getMainCamera() {
        return this.mainCamera;
    }
}
