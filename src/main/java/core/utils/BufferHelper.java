package core.utils;

import core.model.Vertex;
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
        }

        // flip the buffer !!! this needs to be done before it can be read by GL
        // reset the relative position to zero
        buffer.flip();

        return buffer;
    }
}
