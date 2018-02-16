package core.utils;

import core.model.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;
import java.util.ArrayList;

public class AssimpModelLoader {

    public static ArrayList<Model> loadModel(AIScene scene, String fileDir) {

        ArrayList<Model> models = new ArrayList<>();
        ArrayList<Material> materials = new ArrayList<>();

        int meshCount = scene.mNumMeshes();
        PointerBuffer meshesBuffer = scene.mMeshes();

        int materialCount = scene.mNumMaterials();
        PointerBuffer materialsBuffer = scene.mMaterials();

        if (materialsBuffer != null){
            for (int i = 0; i < materialCount; i++) {
                AIMaterial aiMaterial = AIMaterial.create(materialsBuffer.get(i));
                Material material = generateMaterial(aiMaterial, fileDir);
                materials.add(material);
            }
        }

        if (meshesBuffer != null) {
            for (int i = 0; i < meshCount; ++i) {
                AIMesh aiMesh = AIMesh.create(meshesBuffer.get(i));
                Mesh mesh = generateMesh(aiMesh);
                Model model = new Model(mesh, materials.get(aiMesh.mMaterialIndex()));
                models.add(model);
            }
        }

        return models;
    }

    private static Mesh generateMesh(AIMesh mesh) {

        ArrayList<Vertex> vertexList = new ArrayList<>();
        ArrayList<Integer> indicesList = new ArrayList<>();

        ArrayList<Vector3f> vertices = new ArrayList<>();
        ArrayList<Vector2f> texCoords = new ArrayList<>();

        AIVector3D.Buffer aiVertices = mesh.mVertices();

        while(aiVertices.remaining() > 0) {
            AIVector3D aiVertex = aiVertices.get();
            vertices.add(new Vector3f(aiVertex.x(), aiVertex.y(), aiVertex.z()));
        }

        AIVector3D.Buffer aiTexCoords = mesh.mTextureCoords(0);
        if (aiTexCoords != null){
            while (aiTexCoords.remaining() > 0) {
                AIVector3D aiTexCoord = aiTexCoords.get();
                texCoords.add(new Vector2f(aiTexCoord.x(),aiTexCoord.y()));
            }
        }

        for(int i = 0; i < vertices.size(); i++){
            Vertex vertex = new Vertex();
            vertex.setPosition(vertices.get(i));

            if (!texCoords.isEmpty())
                vertex.setTexCoord(texCoords.get(i));

            vertexList.add(vertex);
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

    private static Material generateMaterial(AIMaterial aiMaterial, String fileDir) {

        AIString path = AIString.calloc();
        Assimp.aiGetMaterialTexture(aiMaterial, Assimp.aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null);
        String textPath = path.dataString();

        Texture diffuseTexture = null;

        if (textPath.length() > 0) {
            diffuseTexture = new Texture();
            diffuseTexture.setId(ResourceLoader.loadTexture("/models/" + fileDir + textPath));
        }

        AIColor4D color = AIColor4D.create();
        Vector3f diffuseColor = null;
        int result = Assimp.aiGetMaterialColor(aiMaterial, Assimp.AI_MATKEY_COLOR_AMBIENT, Assimp.aiTextureType_NONE, 0, color);

        if (result == 0) {
            diffuseColor = new Vector3f(color.r(), color.g(), color.b());
        }

        Material material = new Material();
        material.setColor(diffuseColor);
        material.setDiffusemap(diffuseTexture);

        return material;
    }

    private static int[] toIntArray(ArrayList<Integer> arrayList) {
        return arrayList.stream().mapToInt(i->i).toArray();
    }

    private static Vertex[] toVertexArray(ArrayList<Vertex> arrayList) {
        return arrayList.toArray(new Vertex[0]);
    }
}
