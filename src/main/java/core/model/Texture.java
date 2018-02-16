package core.model;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Texture {

    private int id;

    public Texture() {

    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, this.id);
    }

    public void setId(int id) {
        this.id = id;
    }
}
