package engine.model;

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

    public Vertex() {
        this.position = new Vector3f();
        this.texCoord = new Vector2f();
    }

    public Vertex(Vector3f position) {
        this(position, new Vector2f());
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector2f getTexCoord() {
        return texCoord;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setTexCoord(Vector2f texCoord) {
        this.texCoord = texCoord;
    }
}
