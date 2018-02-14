package core.model;

import java.util.ArrayList;

public class Model {

    private ArrayList<Mesh> meshes;

    public Model(ArrayList<Mesh> meshes) {
        this.meshes = meshes;
    }

    public ArrayList<Mesh> getMesh() {
        return this.meshes;
    }
}
