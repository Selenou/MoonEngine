package engine.utils;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class TextureManager {

    private static TextureManager instance = null;

    private HashMap<String, Integer> textureCache;

    public static TextureManager getInstance() {
        if(instance == null){
            instance = new TextureManager();
        }
        return instance;
    }

    private TextureManager() {
        this.textureCache = new HashMap<>();
    }

    public int loadTexture(String textureDirectory) {
        String path = Utils.getAbsolutePath(textureDirectory);

        if(this.textureCache.containsKey(path)){
            return this.textureCache.get(path);
        }

        // super efficient : stack is automatically popped, ip memory automatically reclaimed
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            // flip the y-axis during image loading
            stbi_set_flip_vertically_on_load(true);

            ByteBuffer imageBuffer = stbi_load(path, w, h, c, 0);

            if (imageBuffer == null) {
                throw new RuntimeException("Failed to load a texture !" + System.lineSeparator() + stbi_failure_reason());
            }

            int width = w.get(0);
            int height = h.get(0);
            int channels = c.get(0); // components per pixel (rgb, rgba, ga, ...)

            System.out.println("Loading texture " + textureDirectory + " | width:" + width + ", height:" + height + ", channels:" + channels);

            int texId = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, texId);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR); // texture minifying function
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); // texture magnification function
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT); // x axis
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT); // y axis

            // we can start generating a texture using the previously loaded image data

            // PNG
            if(channels == 4){
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);
            }
            else { // JPG
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, imageBuffer);
            }

            // once glTexImage2D is called, the currently bound texture object now has the texture image attached to it

            // if you want mipmap
            glTexParameteri(GL_TEXTURE_2D, GL_NEAREST_MIPMAP_LINEAR, GL_NEAREST);
            // stored smaller versions of a texture where the appropriate sized version is chosen based on the distance to the viewer
            glGenerateMipmap(GL_TEXTURE_2D);

            // it is good practice to free the image memory
            stbi_image_free(imageBuffer);

            this.textureCache.put(path, texId);

            return texId;
        }
    }

    public void cleanUp() {
        for(int textureId : textureCache.values()) {
            glDeleteTextures(textureId);
        }
    }
}
