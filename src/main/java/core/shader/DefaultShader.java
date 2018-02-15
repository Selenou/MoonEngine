package core.shader;

import core.utils.ResourceLoader;

public class DefaultShader extends Shader {

    private static DefaultShader instance = null;

    private DefaultShader() {
        super();

        this.addVertexShader(ResourceLoader.loadShader("defaultVertex.vs"));
        this.addFragmentShader(ResourceLoader.loadShader("defaultFragment.fs"));

        this.compileShader();

        this.addUniform("MVP");
    }

    /*
    public void updateUniforms(GameObject object) {

    }*/

    public static DefaultShader getInstance() {
        if(instance == null) {
            return new DefaultShader();
        }
        return instance;
    }
}
