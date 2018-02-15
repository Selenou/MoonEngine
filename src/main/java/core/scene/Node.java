package core.scene;

import java.util.ArrayList;

public class Node {

    private Node parent;
    private ArrayList<Node> children;

    private Transform transform;

    public Node() {
        this.transform = new Transform();
        this.children = new ArrayList<>();
    }

    public void addChild(Node childNode) {
        childNode.setParent(this);
        this.children.add(childNode);
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Transform getTransform() {
        return this.transform;
    }
}
