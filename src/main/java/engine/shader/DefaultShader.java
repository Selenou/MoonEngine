package engine.shader;

import engine.core.CoreEngine;
import engine.model.Model;
import engine.scene.GameObject;

public class DefaultShader extends Shader {

    private static DefaultShader instance = null;

    private DefaultShader() {
        super();

        this.addVertexShader(this.loadShader("defaultVertex.vs"));
        this.addFragmentShader(this.loadShader("defaultFragment.fs"));

        this.compileShader();

        this.addUniform("MVP");
        this.addUniform("diffuseColor");
    }

    public void updateUniforms(GameObject object, Model model, int materialIndex) {
        this.setUniform("MVP", CoreEngine.getInstance().getGame().getScene().getMainCamera().getViewProjectionMatrix().mul(object.getTransform().getModelMatrix()));
        this.setUniform("diffuseColor", model.getMaterials().get(materialIndex).getDiffuseColor());

        if(model.getMaterials().get(materialIndex).getDiffusemap() != null)
            model.getMaterials().get(materialIndex).getDiffusemap().bind();
    }

    public static DefaultShader getInstance() {
        if(instance == null) {
            instance = new DefaultShader();
        }
        return instance;
    }
}
