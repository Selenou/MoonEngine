package core.shader;

import core.kernel.CoreEngine;
import core.scene.GameObject;
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

    public void updateUniforms(GameObject object) {
        //todo erreur qql part si 2 objects dans scene et si on modifie la transform du premier
        this.setUniform("MVP", CoreEngine.getInstance().getGame().getScene().getMainCamera().getViewProjectionMatrix().mul(object.getTransform().getModelMatrix()));
    }

    public static DefaultShader getInstance() {
        if(instance == null) {
            instance = new DefaultShader();
        }
        return instance;
    }
}
