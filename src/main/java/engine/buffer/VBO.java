package engine.buffer;

import engine.gfx.Mesh;
import engine.gfx.Vertex;
import engine.utils.BufferHelper;

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
            glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * Float.BYTES, 5 * Float.BYTES ); // normal

            // unbind vbo
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        this.vao.unbind();
    }

    public void draw() {
        this.vao.bind();
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);
            glDrawElements(GL_TRIANGLES, this.size, GL_UNSIGNED_INT, 0);
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);
        this.vao.unbind();
    }

    public void cleanup() {
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(this.vbo);
        glDeleteBuffers(this.ebo);

        this.vao.cleanup();
    }
}
