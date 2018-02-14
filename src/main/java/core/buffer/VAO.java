package core.buffer;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

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
}
