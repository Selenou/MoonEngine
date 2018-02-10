package core.model;

public class Mesh {

    private Vertex[] vertices;
    private int[] indices;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public Vertex[] getVertices() {
        return this.vertices;
    }

    public int[] getIndices() {
        return this.indices;
    }
}
