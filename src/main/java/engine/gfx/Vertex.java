package engine.gfx;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex {

    public static final int SIZE = 8;

    private Vector3f position;
    private Vector2f texCoord;
    private Vector3f normal;

    public Vertex(Vector3f position, Vector2f texCoord, Vector3f normal) {
        this.position = position;
        this.texCoord = texCoord;
        this.normal = normal;
    }

    public Vertex(Vector3f position, Vector2f texCoord) {
        this(position, texCoord, new Vector3f());
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

    public Vector3f getNormal() {
        return this.normal;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setTexCoord(Vector2f texCoord) {
        this.texCoord = texCoord;
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }
}
