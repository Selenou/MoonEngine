package core.buffer;

import core.model.Mesh;
import core.model.Vertex;
import core.utils.BufferHelper;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class VBO {

    private VAO vao;
    private int vbo;
    private int ebo;
    private int size;

    public VBO() {
        this.vao = new VAO();
        this.vbo = glGenBuffers();
        this.ebo = glGenBuffers();
        this.size = 0;
    }

    public void allocate(Mesh mesh) {
        this.size = mesh.getIndices().length;

        this.vao.bind();
            // copy our vertices array in a VBO for OpenGL to use
            glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
            glBufferData(GL_ARRAY_BUFFER, BufferHelper.createFlippedBuffer(mesh.getVertices()), GL_STATIC_DRAW);

            // GL_ELEMENT_ARRAY_BUFFER is used to indicate the buffer you're presenting contains the indices of each element in the "other" (GL_ARRAY_BUFFER) buffer
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ebo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferHelper.createFlippedBuffer(mesh.getIndices()), GL_STATIC_DRAW);

            // then set our vertex attributes pointers
            glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * Float.BYTES, 0); // position
            glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * Float.BYTES, 3 * Float.BYTES ); // texture

            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
        this.vao.unbind();
    }

    public void draw() {
        this.vao.bind();
            glDrawElements(GL_TRIANGLES, this.size, GL_UNSIGNED_INT, 0);
        this.vao.unbind();
    }
}
