package core.model;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;

import java.util.ArrayList;

public class Model {

    private ArrayList<Mesh> meshes;
    //private ArrayList<Material> materials;

    public Model(AIScene scene) {
        int meshCount = scene.mNumMeshes();

        this.meshes = new ArrayList<>();

        PointerBuffer meshesBuffer = scene.mMeshes();

        for (int i = 0; i < meshCount; ++i) {
            this.meshes.add(new Mesh(AIMesh.create(meshesBuffer.get(i))));
        }
    }

    public ArrayList<Mesh> getMesh() {
        return this.meshes;
    }
}
