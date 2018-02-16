package core.shader;

import core.kernel.CoreEngine;
import core.scene.GameObject;
import core.utils.ResourceLoader;

public class DebugShader extends Shader {

    private static DebugShader instance = null;

    private DebugShader() {
        super();

        this.addVertexShader(ResourceLoader.loadShader("debugVertex.vs"));
        this.addFragmentShader(ResourceLoader.loadShader("debugFragment.fs"));

        this.compileShader();

        this.addUniform("MVP");
    }

    public void updateUniforms(GameObject object) {
        this.setUniform("MVP", CoreEngine.getInstance().getGame().getScene().getMainCamera().getViewProjectionMatrix().mul(object.getTransform().getModelMatrix()));
    }

    public static DebugShader getInstance() {
        if(instance == null) {
            instance = new DebugShader();
        }
        return instance;
    }
}
