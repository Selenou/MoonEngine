package core.shader;

import core.kernel.CoreEngine;
import core.scene.GameObject;

public class DebugShader extends Shader {

    private static DebugShader instance = null;

    private DebugShader() {
        super();

        this.addVertexShader(this.loadShader("debugVertex.vs"));
        this.addFragmentShader(this.loadShader("debugFragment.fs"));

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
