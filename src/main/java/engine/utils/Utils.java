package engine.utils;

import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Utils {

    public static String getAbsolutePath(String fileName) {
        File file = null;

        try {
            file = new File(Utils.class.getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (file == null)
            throw new RuntimeException("Failed to retrieve the file : " + fileName);

        return file.getAbsolutePath();
    }

    public static ByteBuffer loadImage(String fileName){
        ByteBuffer imageBuffer;

        // super efficient : stack is automatically popped, ip memory automatically reclaimed
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            String path = Utils.getAbsolutePath("/images/" + fileName);

            imageBuffer = stbi_load(path, w, h, c, 0);

            if (imageBuffer == null) {
                throw new RuntimeException("Failed to load an image !" + System.lineSeparator() + stbi_failure_reason());
            }
        }

        return imageBuffer;
    }
}
