package core.shader;

import core.kernel.CoreEngine;
import core.model.Model;
import core.scene.GameObject;

public class DefaultShader extends Shader {

    private static DefaultShader instance = null;

    private DefaultShader() {
        super();

        this.addVertexShader(this.loadShader("defaultVertex.vs"));
        this.addFragmentShader(this.loadShader("defaultFragment.fs"));

        this.compileShader();

        this.addUniform("MVP");
        this.addUniform("COLOR");
    }

    public void updateUniforms(GameObject object) {
        this.setUniform("MVP", CoreEngine.getInstance().getGame().getScene().getMainCamera().getViewProjectionMatrix().mul(object.getTransform().getModelMatrix()));

        Model model = ((Model)object.getComponent("model"));

        this.setUniform("COLOR", model.getMaterial().getColor());
        if(model.getMaterial().getDiffusemap() != null)
            model.getMaterial().getDiffusemap().bind();
    }

    public static DefaultShader getInstance() {
        if(instance == null) {
            instance = new DefaultShader();
        }
        return instance;
    }
}
