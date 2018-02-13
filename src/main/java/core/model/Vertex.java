package core.model;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex {

    public static final int SIZE = 5;

    private Vector3f position;
    private Vector2f texCoord;

    public Vertex(Vector3f position, Vector2f texCoord) {
        this.position = position;
        this.texCoord = texCoord;
    }

    public Vertex(Vector3f position) {
        this(position, new Vector2f(0,0));
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector2f getTexCoord() {
        return texCoord;
    }
}
