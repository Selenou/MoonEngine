package core.buffer;

import core.model.Mesh;
import core.model.Vertex;
import core.utils.BufferHelper;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VBO {

    private int vbo;
    private int ibo;
    private int vao;
    private int size;

    public VBO() {
        this.vbo = glGenBuffers();
        this.ibo = glGenBuffers();
        this.vao = glGenVertexArrays();
        this.size = 0;
    }

    public void allocate(Mesh mesh) {
        this.size = mesh.getIndices().length;

        // bind VAO (apple bug ?)
        glBindVertexArray(this.vao);

        // copy our vertices array in a VBO for OpenGL to use
        glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferHelper.createFlippedBuffer(mesh.getVertices()), GL_STATIC_DRAW);

        // GL_ELEMENT_ARRAY_BUFFER is used to indicate the buffer you're presenting contains the indices of each element in the "other" (GL_ARRAY_BUFFER) buffer
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferHelper.createFlippedBuffer(mesh.getIndices()), GL_STATIC_DRAW);

        // then set our vertex attributes pointers
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
    }

    public void draw() {
        glBindVertexArray(this.vao);
        glDrawElements(GL_TRIANGLES, this.size, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    /*
    public void delete() {
        glDisableVertexAttribArray(0);
        glBindVertexArray(this.vao);
        glDeleteBuffers(this.vbo);
        glDeleteBuffers(this.ibo);
        glDeleteVertexArrays(this.vao);
        glBindVertexArray(0);
    }
    */
}
