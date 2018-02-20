package engine.model;

import engine.buffer.VBO;

import java.util.ArrayList;

public class Model {

    private ArrayList<Mesh> meshes;
    private ArrayList<Material> materials;

    public Model(ArrayList<Mesh> meshes, ArrayList<Material> materials) {
        this.meshes = meshes;
        this.materials = materials;
    }

    public void allocate() {
        for (Mesh mesh : meshes)
            mesh.allocate(new VBO());
    }

    public ArrayList<Mesh> getMeshes() {
        return this.meshes;
    }

    public ArrayList<Material> getMaterials() {
        return this.materials;
    }

    public void cleanup() {
        for (Mesh mesh : meshes)
            mesh.cleanup();

        for (Material material : materials)
            material.cleanup();
    }
}
