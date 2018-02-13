package core.model;

import org.joml.Vector3f;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

import java.util.ArrayList;

public class Mesh {

    private Vertex[] vertices;
    private int[] indices;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    /**
     * //TODO a revoir
     * Parser for Assimp AIMesh
     * @param mesh
     */
    public Mesh(AIMesh mesh) {

        // vertex

        ArrayList<Vertex> vertexList = new ArrayList<>();
        AIVector3D.Buffer vertexBuffer = mesh.mVertices();

        for(int i = 0; i < vertexBuffer.capacity(); i++) {
            Vertex vertex = new Vertex(new Vector3f(vertexBuffer.get(i).x(), vertexBuffer.get(i).y(), vertexBuffer.get(i).z()));
            vertexList.add(vertex);
        }

        this.vertices = vertexList.toArray(new Vertex[0]);

        // indices

        ArrayList<Integer> indicesList = new ArrayList<>();
        AIFace.Buffer facesBuffer = mesh.mFaces();

        for(int i = 0; i < mesh.mNumFaces(); i++) {
            AIFace face = facesBuffer.get(i);

            if (face.mNumIndices() != 3)
                throw new IllegalStateException("AIFace.mNumIndices() != 3");

            for(int j = 0; j < 3; j++) {
                indicesList.add(face.mIndices().get(j));
            }
        }

        this.indices = indicesList.stream().mapToInt(i->i).toArray();
    }

    public Vertex[] getVertices() {
        return this.vertices;
    }

    public int[] getIndices() {
        return this.indices;
    }
}
