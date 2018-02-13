package core.utils;

import core.model.Texture;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class ResourceLoader {

    /**
     * Get a shader's source code from its file
     * @param fileName name of the file
     * @return the shader's source code as string
     */
    public static String loadShader(String fileName) {

        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader;
        try {
            shaderReader = new BufferedReader(new InputStreamReader(ResourceLoader.class.getResourceAsStream("/shaders/" + fileName)));
            String line;

            while((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }

            shaderReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return shaderSource.toString();
    }

    public static Texture loadTexture(String fileName) {

        try (MemoryStack stack = MemoryStack.stackPush()) { // super efficient : stack is automatically popped, ip memory automatically reclaimed
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            String path = ResourceLoader.class.getResource("/textures/" + fileName).getPath();

            // flip the y-axis during image loading
            stbi_set_flip_vertically_on_load(true);

            ByteBuffer imageBuffer = stbi_load(path, w, h, c, 0);

            if (imageBuffer == null) {
                throw new RuntimeException("Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());
            }

            int width = w.get(0);
            int height = h.get(0);
            int channels = c.get(0); // components per pixel (rgb, rgba, ga, ...)

            System.out.println("Loading texture " + fileName + " | width : " + width + ", height : " + height + ", channels : " + channels);

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
            //glTexParameteri(GL_TEXTURE_2D, GL_NEAREST_MIPMAP_LINEAR, GL_NEAREST);
            // stored smaller versions of a texture where the appropriate sized version is chosen based on the distance to the viewer
            //glGenerateMipmap(GL_TEXTURE_2D);

            // it is good practice to free the image memory
            stbi_image_free(imageBuffer);

            return new Texture(texId);
        }
    }

    public static ByteBuffer loadImage(String fileName) {
        try (MemoryStack stack = MemoryStack.stackPush()) { // super efficient : stack is automatically popped, ip memory automatically reclaimed
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            String path = ResourceLoader.class.getResource("/images/" + fileName).getPath();

            ByteBuffer imageBuffer = stbi_load(path, w, h, c, 0);

            if (imageBuffer == null) {
                throw new RuntimeException("Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());
            }

            return imageBuffer;
        }
    }
}
