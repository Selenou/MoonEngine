package engine.utils;

import engine.gfx.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.assimp.Assimp.*;

public class AssimpModelLoader {

    public static Model loadModel(String fileDir, String fileName){
        String path = Utils.getAbsolutePath("/models/" + fileDir + fileName);

        // the flag aiProcess_RemoveRedundantMaterials is a big tricky. If we do not precise it, there is 1 more material at index 0, which is pretty useless atm
        AIScene scene = Assimp.aiImportFile(path,
                aiProcess_JoinIdenticalVertices |
                aiProcess_Triangulate |
                aiProcess_RemoveRedundantMaterials |
                aiProcess_OptimizeMeshes |
                aiProcess_GenNormals
        );

        if (scene == null) {
            throw new IllegalStateException("Failed to load a gfx !" + System.lineSeparator() + aiGetErrorString());
        }

        // materials
        ArrayList<Material> materials = new ArrayList<>();
        int materialCount = scene.mNumMaterials();
        PointerBuffer materialsBuffer = scene.mMaterials();

        if (materialsBuffer != null){
            for (int i = 0; i < materialCount; i++) {
                AIMaterial aiMaterial = AIMaterial.create(materialsBuffer.get(i));
                Material material = generateMaterial(aiMaterial, fileDir);
                materials.add(material);
            }
        }

        // meshes
        ArrayList<Mesh> meshes = new ArrayList<>();
        int meshCount = scene.mNumMeshes();
        PointerBuffer meshesBuffer = scene.mMeshes();

        if (meshesBuffer != null) {
            for (int i = 0; i < meshCount; ++i) {
                AIMesh aiMesh = AIMesh.create(meshesBuffer.get(i));
                Mesh mesh = generateMesh(aiMesh);
                meshes.add(mesh);
            }
        }

        return new Model(meshes, materials);
    }

    private static Mesh generateMesh(AIMesh mesh) {
        ArrayList<Vertex> vertexList = new ArrayList<>();
        ArrayList<Integer> indicesList = new ArrayList<>();

        ArrayList<Vector3f> vertexPosition = new ArrayList<>();
        ArrayList<Vector2f> textureCoords = new ArrayList<>();
        ArrayList<Vector3f> vertexNormal = new ArrayList<>();

        AIVector3D.Buffer aiVertices = mesh.mVertices();

        // position
        while(aiVertices.remaining() > 0) {
            AIVector3D aiVertex = aiVertices.get();
            vertexPosition.add(new Vector3f(aiVertex.x(), aiVertex.y(), aiVertex.z()));
        }

        // texture coords
        AIVector3D.Buffer aiTexCoords = mesh.mTextureCoords(0);
        if (aiTexCoords != null){
            while (aiTexCoords.remaining() > 0) {
                AIVector3D aiTexCoord = aiTexCoords.get();
                textureCoords.add(new Vector2f(aiTexCoord.x(),aiTexCoord.y()));
            }
        }

        // normal
        AIVector3D.Buffer aiNormals = mesh.mNormals();
        if (aiNormals != null) {
            while (aiNormals.remaining() > 0) {
                AIVector3D aiNormal = aiNormals.get();
                vertexNormal.add(new Vector3f(aiNormal.x(), aiNormal.y(), aiNormal.z()));
            }
        }

        // vertex creation
        for(int i = 0; i < vertexPosition.size(); i++){
            Vertex vertex = new Vertex(vertexPosition.get(i));

            if (!textureCoords.isEmpty())
                vertex.setTexCoord(textureCoords.get(i));

            if (!vertexNormal.isEmpty())
                vertex.setNormal(vertexNormal.get(i));

            vertexList.add(vertex);
        }

        AIFace.Buffer aiFaces = mesh.mFaces();

        // indices
        while(aiFaces.remaining() > 0) {
            AIFace aiface = aiFaces.get();

            if (aiface.mNumIndices() != 3)
                throw new IllegalStateException("AIFace.mNumIndices() != 3");

            for(int j = 0; j < 3; j++) {
                indicesList.add(aiface.mIndices().get(j));
            }
        }

        Vertex[] vertex = vertexList.toArray(new Vertex[0]);
        int[] indices = indicesList.stream().mapToInt(i->i).toArray();

        return new Mesh(vertex, indices, mesh.mMaterialIndex());
    }

    private static Material generateMaterial(AIMaterial aiMaterial, String fileDir) {
        AIString path = AIString.calloc();
        Assimp.aiGetMaterialTexture(aiMaterial, Assimp.aiTextureType_DIFFUSE, 0, path, (IntBuffer) null, null, null, null, null, null);
        String textPath = path.dataString();

        // need this for linux and macOs
        textPath = textPath.replace("\\", "/");

        Texture diffuseMap = null;

        if (textPath.length() > 0) {
            int textureId = TextureLoader.loadTexture("/models/" + fileDir + textPath);
            diffuseMap = new Texture(textureId);
        }

        Assimp.aiGetMaterialTexture(aiMaterial, Assimp.aiTextureType_SPECULAR, 0, path, (IntBuffer) null, null, null, null, null, null);
        textPath = path.dataString();

        // need this for linux and macOs
        textPath = textPath.replace("\\", "/");

        Texture specularMap = null;

        if (textPath.length() > 0) {
            int textureId = TextureLoader.loadTexture("/models/" + fileDir + textPath);
            specularMap = new Texture(textureId);
        }

        AIColor4D color = AIColor4D.create();

        int result = Assimp.aiGetMaterialColor(aiMaterial, Assimp.AI_MATKEY_COLOR_DIFFUSE, Assimp.aiTextureType_NONE, 0, color);

        Vector3f diffuseColor = null;
        if (result == 0) {
            diffuseColor = new Vector3f(color.r(), color.g(), color.b());
        }

        result = Assimp.aiGetMaterialColor(aiMaterial, Assimp.AI_MATKEY_COLOR_AMBIENT, Assimp.aiTextureType_NONE, 0, color);

        Vector3f ambientColor = null;
        if (result == 0) {
            ambientColor = new Vector3f(color.r(), color.g(), color.b());
        }

        result = Assimp.aiGetMaterialColor(aiMaterial, Assimp.AI_MATKEY_COLOR_SPECULAR, Assimp.aiTextureType_NONE, 0, color);

        Vector3f specularColor = null;
        if (result == 0) {
            specularColor = new Vector3f(color.r(), color.g(), color.b());
        }

        Material material = new Material();
        material.setDiffusemap(diffuseMap);
        material.setSpecularMap(specularMap);
        material.setDiffuseColor(diffuseColor);
        material.setAmbientColor(ambientColor);
        material.setSpecularColor(specularColor);

        return material;
    }
}