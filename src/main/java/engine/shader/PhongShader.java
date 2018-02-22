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

        this.addUniform("viewProjectionMatrix");
        this.addUniform("modelMatrix");
        this.addUniform("normalMatrix");
        this.addUniform("cameraPosition");
    }

    public void updateUniforms(GameObject object, Model model, int materialIndex) {
        this.setUniform("viewProjectionMatrix", CoreEngine.getInstance().getGame().getScene().getMainCamera().getViewProjectionMatrix());
        this.setUniform("modelMatrix", object.getTransform().getModelMatrix());
        this.setUniform("normalMatrix", object.getTransform().getNormalMatrix());
        this.setUniform("cameraPosition", CoreEngine.getInstance().getGame().getScene().getMainCamera().getPosition());

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
