package core.shader;

import core.kernel.CoreEngine;
import core.model.Material;
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
        this.addUniform("COLOR");
    }

    public void updateUniforms(GameObject object) {
        this.setUniform("MVP", CoreEngine.getInstance().getGame().getScene().getMainCamera().getViewProjectionMatrix().mul(object.getTransform().getModelMatrix()));
        this.setUniform("COLOR", ((Material)object.getComponent("material")).getColor());
        if(((Material) object.getComponent("material")).getDiffusemap() != null)
            ((Material) object.getComponent("material")).getDiffusemap().bind();
    }

    public static DefaultShader getInstance() {
        if(instance == null) {
            instance = new DefaultShader();
        }
        return instance;
    }
}
