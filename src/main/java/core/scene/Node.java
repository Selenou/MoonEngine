package core.scene;

import core.input.Input;

import java.util.ArrayList;

public class Node {

    private Node parent;
    private ArrayList<Node> children;

    private Transform transform;

    public Node() {
        this.transform = new Transform();
        this.children = new ArrayList<>();
    }

    public void update(){
        for(Node child : this.children)
            child.update();
    }

    public void input(Input input, float delta){
        for(Node child : this.children)
            child.input(input, delta);
    }

    public void render(){
        for(Node child : this.children)
            child.render();
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
