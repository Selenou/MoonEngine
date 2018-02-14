package core.utils;

import core.model.Mesh;
import core.model.Model;
import core.model.Vertex;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;

import java.util.ArrayList;

public class AssimpModelLoader {

    public static Model loadModel(AIScene scene) {
        int meshCount = scene.mNumMeshes();
        PointerBuffer meshesBuffer = scene.mMeshes();

        ArrayList<Mesh> meshes = new ArrayList<>();

        if (meshesBuffer != null) {
            for (int i = 0; i < meshCount; ++i) {
                meshes.add(constructMesh(AIMesh.create(meshesBuffer.get(i))));
            }
        }

        return new Model(meshes);
    }

    private static Mesh constructMesh(AIMesh mesh) {

        ArrayList<Vertex> vertexList = new ArrayList<>();
        ArrayList<Integer> indicesList = new ArrayList<>();

        AIVector3D.Buffer aiVertices = mesh.mVertices();

        while(aiVertices.remaining() > 0) {
            AIVector3D aiVertex = aiVertices.get();
            vertexList.add(new Vertex(new Vector3f(aiVertex.x(), aiVertex.y(), aiVertex.z())));
        }

        AIFace.Buffer aiFaces = mesh.mFaces();

        while(aiFaces.remaining() > 0) {
            AIFace aiface = aiFaces.get();

            if (aiface.mNumIndices() != 3)
                throw new IllegalStateException("AIFace.mNumIndices() != 3");

            for(int j = 0; j < 3; j++) {
                indicesList.add(aiface.mIndices().get(j));
            }
        }

        return new Mesh(AssimpModelLoader.toVertexArray(vertexList), AssimpModelLoader.toIntArray(indicesList));
    }

    private static int[] toIntArray(ArrayList<Integer> arrayList) {
        return arrayList.stream().mapToInt(i->i).toArray();
    }

    private static Vertex[] toVertexArray(ArrayList<Vertex> arrayList) {
        return arrayList.toArray(new Vertex[0]);
    }
}
