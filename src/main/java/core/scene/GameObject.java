package core.scene;

import core.input.Input;

import java.util.HashMap;

public class GameObject extends Node {

    public HashMap<String, Component> components;

    public GameObject() {
        super();
        this.components = new HashMap<>();
    }

    public void addComponent(String name, Component component) {
        component.setParent(this);
        this.components.put(name, component);
    }

    public Component getComponent(String name) {
        return this.components.get(name);
    }

    public void update(){
        for(String name : this.components.keySet())
            this.components.get(name).update();

        super.update();
    }

    public void input(Input input, float delta){
        for(String name : this.components.keySet())
            this.components.get(name).input(input, delta);

        super.input(input, delta);
    }

    public void render(){
        for(String name : this.components.keySet())
            this.components.get(name).render();

        super.render();
    }
}
