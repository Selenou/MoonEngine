package engine.light;

import org.joml.Vector3f;

public class Light {

    private Vector3f position;
    private Vector3f color;
    private float intensity;

    public Light(Vector3f position, Vector3f color, float intensity) {
        this.position = position;
        this.color = color;
        this.intensity = intensity;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector3f getColor() {
        return this.color;
    }

    public float getIntensity() {
        return this.intensity;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }
}
