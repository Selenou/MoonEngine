package engine.shader;

import engine.core.CoreEngine;
import engine.gfx.Model;
import engine.scene.GameObject;

public class PhongShader extends Shader {

    private static PhongShader instance = null;

    private PhongShader() {
        super();

        this.addVertexShader(this.loadShader("phongVertex.vs"));
        this.addFragmentShader(this.loadShader("phongFragment.fs"));

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

    public static PhongShader getInstance() {
        if(instance == null) {
            instance = new PhongShader();
        }
        return instance;
    }
}
