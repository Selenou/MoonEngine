package core.utils;

import core.model.Vertex;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferHelper {

    public static IntBuffer createFlippedBuffer(int... values) {
        IntBuffer buffer = BufferUtils.createIntBuffer(values.length);
        buffer.put(values);

        // flip the buffer !!! this needs to be done before it can be read by GL
        // reset the relative position to zero
        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.SIZE);

        for (Vertex vertice : vertices) {
            buffer.put(vertice.getPosition().x());
            buffer.put(vertice.getPosition().y());
            buffer.put(vertice.getPosition().z());
            buffer.put(vertice.getTexCoord().x());
            buffer.put(vertice.getTexCoord().y());
        }

        // flip the buffer !!! this needs to be done before it can be read by GL
        // reset the relative position to zero
        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Matrix4f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
        return matrix.get(buffer);
    }
}
