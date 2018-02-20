package engine.buffer;

import static org.lwjgl.opengl.GL30.*;

public class VAO {

    private int id;

    public VAO() {
        this.id = glGenVertexArrays();
    }

    public void bind() {
        glBindVertexArray(this.id);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public void cleanUp() {
        this.unbind();
        glDeleteVertexArrays(this.id);
    }
}
