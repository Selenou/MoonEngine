package engine.component;

import engine.input.Input;
import engine.scene.GameObject;

public abstract class Component {

    private GameObject parent;

    public Component() {

    }

    public void update(){

    }

    public void input(Input input, float delta){

    }

    public void render(){

    }

    public GameObject getParent() {
        return this.parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public void cleanup() {

    }
}
