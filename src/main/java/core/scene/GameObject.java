package core.scene;

import java.util.HashMap;

public class GameObject extends Node {

    private HashMap<String, Component> components;

    public GameObject() {
        super();
        this.components = new HashMap<>();
    }
}
