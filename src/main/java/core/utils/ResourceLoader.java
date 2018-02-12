package core.utils;

import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

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

    public static void loadImage(String fileName) {
        // prepare image buffers
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer c = BufferUtils.createIntBuffer(1);

        String path = ResourceLoader.class.getResource("/textures/" + fileName).getPath();

        ByteBuffer imageBuffer = stbi_load(path, w, h, c, 0);

        if (imageBuffer == null) {
            throw new RuntimeException("Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());
        }

        int width = w.get(0);
        int height = h.get(0);
        int channels = c.get(0); // components per pixel (rgb, rgba, ga, ...)

        System.out.println(width + " " + height + " " + channels);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, imageBuffer);
        //glGenerateMipmap(GL_TEXTURE_2D);

        //stbi_image_free(imageBuffer);
    }
}
