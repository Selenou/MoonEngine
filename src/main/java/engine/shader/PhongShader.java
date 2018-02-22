package engine.shader;

import engine.core.CoreEngine;
import engine.core.Time;
import engine.gfx.Model;
import engine.light.Light;
import engine.scene.GameObject;
import org.joml.Vector3f;

public class PhongShader extends Shader {

    private static PhongShader instance = null;

    private Light light;

    private PhongShader() {
        super();

        this.addVertexShader(this.loadShader("phongVertex.vs"));
        this.addFragmentShader(this.loadShader("phongFragment.fs"));

        this.compileShader();

        this.addUniform("viewProjectionMatrix");
        this.addUniform("modelMatrix");
        this.addUniform("normalMatrix");
        this.addUniform("cameraPosition");

        this.addUniform("material.ambient");
        this.addUniform("material.diffuse");
        this.addUniform("material.specular");
        this.addUniform("material.shininess");

        this.light = new Light(new Vector3f(1,0,1), new Vector3f(1,1,1), 3.0f);

        this.addUniform("light.position");
        this.addUniform("light.color");
        this.addUniform("light.intensity");
    }

    public void updateUniforms(GameObject object, Model model, int materialIndex) {
        this.setUniform("viewProjectionMatrix", CoreEngine.getInstance().getGame().getScene().getMainCamera().getViewProjectionMatrix());
        this.setUniform("modelMatrix", object.getTransform().getModelMatrix());
        this.setUniform("normalMatrix", object.getTransform().getNormalMatrix());
        this.setUniform("cameraPosition", CoreEngine.getInstance().getGame().getScene().getMainCamera().getPosition());


        this.setUniform("material.ambient", model.getMaterials().get(materialIndex).getAmbientColor());
        this.setUniform("material.diffuse", model.getMaterials().get(materialIndex).getDiffuseColor());
        this.setUniform("material.specular", model.getMaterials().get(materialIndex).getSpecularColor());
        this.setUniformf("material.shininess", 32.0f);

        this.light.setPosition(new Vector3f((float)Math.sin((double)Time.getTime()/Time.SECOND), this.light.getPosition().y(), (float)Math.cos((double)Time.getTime()/Time.SECOND)));

        this.setUniform("light.position", this.light.getPosition());
        this.setUniform("light.color", this.light.getColor());
        this.setUniformf("light.intensity", this.light.getIntensity());

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
