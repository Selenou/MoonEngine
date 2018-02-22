package engine.gfx;

import engine.buffer.VBO;

public class Mesh {

    private Vertex[] vertices;
    private int[] indices;
    private int materialIndex;
    private VBO vbo;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public Mesh(Vertex[] vertices, int[] indices, int materialIndex) {
        this.vertices = vertices;
        this.indices = indices;
        this.materialIndex = materialIndex;
    }

    public void allocate(VBO vbo) {
        this.vbo = vbo;
        this.vbo.allocate(this);
    }

    public void draw() {
        this.vbo.draw();
    }

    public Vertex[] getVertices() {
        return this.vertices;
    }

    public int[] getIndices() {
        return this.indices;
    }

    public int getMaterialIndex() {
        return this.materialIndex;
    }

    public void cleanup() {
        this.vbo.cleanup();
    }
}
