package core.model;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

public class Shader {

    private int program;

    public Shader() {
        // create an object to which shader objects can be attached and returns the ID reference
        this.program = glCreateProgram();

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
            System.exit(1);
        }
    }

    public void bind() {
        // install a program object as part of current rendering state
        // every shader and rendering call after glUseProgram will now use this program object
        glUseProgram(this.program);
    }

    //todo : delete/detach/cleanup ?
}
