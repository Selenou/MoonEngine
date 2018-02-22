package engine.shader;

import engine.gfx.Model;
import engine.scene.GameObject;
import engine.utils.BufferHelper;
import engine.utils.Utils;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public abstract class Shader {

    private int program;
    private HashMap<String, Integer> uniforms;

    public Shader() {
        // create an object to which shader objects can be attached and returns the ID reference
        this.program = glCreateProgram();

        // create a map to stock uniforms
        this.uniforms = new HashMap<>();

        // returns 0 if an error occurs creating the program object
        if(this.program == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location in constructor");
            System.exit(1);
        }
    }

    public void addVertexShader(String text) {
        this.addProgram(text, GL_VERTEX_SHADER);
    }

    public void addGeometryShader(String text) {
        this.addProgram(text, GL_GEOMETRY_SHADER);
    }

    public void addFragmentShader(String text) {
        this.addProgram(text, GL_FRAGMENT_SHADER);
    }

    private void addProgram(String text, int type) {
        // creates an empty shader object referenced by an ID
        int shader = glCreateShader(type);

        // returns 0 if an error occurs during the shader creation
        if(shader == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
            System.exit(1);
        }

        // sets the source code in shader
        glShaderSource(shader, text);

        //compiles the source code that have been stored in the shader object
        glCompileShader(shader);

        // returns 0 if an error occurs during the shader compilation
        if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        // attaches the shader object to the program object. This indicates that shader will be included in link operations that will be performed on program.
        glAttachShader(this.program, shader);
    }

    public void updateUniforms(GameObject object, Model model, int materialIndex) {

    }

    public void addUniform(String uniform) {
        int uniformLocation = glGetUniformLocation(this.program, uniform);

        if (uniformLocation == 0xFFFFFFFF) {
            System.err.println(this.getClass().getName() + " Error: Could not find uniform: " + uniform);
            new Exception().printStackTrace();
            System.exit(1);
        }

        this.uniforms.put(uniform, uniformLocation);
    }

    public void compileShader() {
        // links the program object
        glLinkProgram(this.program);

        // returns 0 if an error occurs during linking
        if(glGetProgrami(this.program, GL_LINK_STATUS) == 0) {
            System.err.println(glGetProgramInfoLog(this.program, 1024));
            System.exit(1);
        }

        // checks to see whether the executables contained in program can execute given the current OpenGL state
        glValidateProgram(this.program);

        // returns 0 if an error occurs validating the program object
        if(glGetProgrami(this.program, GL_VALIDATE_STATUS) == 0) {
            System.err.println(glGetProgramInfoLog(this.program, 1024));
        }
    }

    public void bind() {
        // install a program object as part of current rendering state
        // every shader and rendering call after glUseProgram will now use this program object
        glUseProgram(this.program);
    }

    public void setUniformi(String uniformName, int value) {
        glUniform1i(this.uniforms.get(uniformName), value);
    }

    public void setUniformf(String uniformName, float value) {
        glUniform1f(this.uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.x(), value.y(), value.z());
    }

    public void setUniform(String uniformName, Matrix4f value) {
        glUniformMatrix4fv(uniforms.get(uniformName), false, BufferHelper.createFlippedBuffer(value));
    }

    /**
     * Get a shader's source code from its file
     * @param fileName name of the file
     * @return the shader's source code as string
     */
    protected String loadShader(String fileName) {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader;
        try {
            shaderReader = new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream("/shaders/" + fileName)));
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

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        this.unbind();
        if (this.program != 0) {
            glDeleteProgram(this.program);
        }
    }
}
