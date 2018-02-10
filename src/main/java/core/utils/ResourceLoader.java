package core.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
}
