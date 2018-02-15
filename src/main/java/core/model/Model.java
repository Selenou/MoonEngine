package core.model;

import core.scene.Component;

import java.util.ArrayList;

public class Model extends Component {

    private ArrayList<Mesh> meshes;

    public Model(ArrayList<Mesh> meshes) {
        this.meshes = meshes;
    }

    public ArrayList<Mesh> getMesh() {
        return this.meshes;
    }
}
